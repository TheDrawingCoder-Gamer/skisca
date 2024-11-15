import sbt._
import Keys._
import scala.sys.process._

sealed trait Arch {
  val id: String
}

object Arch {
  case object X64 extends Arch {
    val id = "x64"
  }
  case object Arm64 extends Arch {
    val id = "arm64"
  }
}

sealed trait OS {
  val id: String
}

object OS {
  case object Windows extends OS {
    val id = "windows"
  }
  case object MacOS extends OS {
    val id = "macos"
  }
  case object Linux extends OS {
    val id = "linux"
  }
}

sealed trait SkiaBuildType {
  val id: String
}

object SkiaBuildType {
  case object Release extends SkiaBuildType {
    val id = "Release"
  }
  case object Debug extends SkiaBuildType {
    val id = "Debug"
  }
}

object SkiaDependencyPlugin extends AutoPlugin {
  object autoImport {
    lazy val skiaVersion: SettingKey[String] = settingKey[String]("Skia Version")
    lazy val skiaBuildType: SettingKey[SkiaBuildType] = settingKey[SkiaBuildType]("Build type")

    lazy val downloadLinuxX64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip linux x64 debug")
    lazy val downloadLinuxX64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip linux x64 release")
    lazy val downloadLinuxArm64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip linux arm64 debug")
    lazy val downloadLinuxArm64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip linux arm64 release")

    lazy val downloadWindowsX64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip windows x64 debug")
    lazy val downloadWindowsX64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip windows x64 release")
    lazy val downloadWindowsArm64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip windows arm64 debug")
    lazy val downloadWindowsArm64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip windows arm64 release")

    lazy val downloadMacosX64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip macos x64 debug")
    lazy val downloadMacosX64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip macos x64 release")
    lazy val downloadMacosArm64Debug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip macos arm64 debug")
    lazy val downloadMacosArm64Release: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip macos arm64 release")

    lazy val skiaDownloadCurrentOsDebug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip detected os debug")
    lazy val skiaDownloadCurrentOsRelease: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip detected os release")

    lazy val skiaDownloadCurrentOs: TaskKey[Set[File]] = taskKey[Set[File]]("Down and unzip detected os")

    // lazy val downloadWasmDebug: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip wasm debug")
    // lazy val downloadWasmRelease: TaskKey[Set[File]] = taskKey[Set[File]]("Download and unzip wasm release")

    lazy val skiaOSCacheDir: TaskKey[File] = taskKey[File]("Current Skia cache location")
  }
  import autoImport._

  def downloadAndUnzipTask(config: String, arch: String)(buildType: String): Def.Initialize[Task[Set[File]]] = Def.task {
    val version = skiaVersion.value
    val skiaBaseUrl = s"https://github.com/HumbleUI/SkiaBuild/releases/download/$version"
    val artifactId = s"Skia-$version-$config-$buildType-$arch"
    val cacheDir = dependencyCacheDirectory.value
    val resDir = Path.resolve(cacheDir)(new File(s"skia/$version/$artifactId")).get
    if (!resDir.exists()) {
      IO.createDirectory(resDir)
      IO.unzipURL(new URL(s"$skiaBaseUrl/$artifactId.zip"), resDir)
    } else {
      Path.allSubpaths(resDir).map(_._1).toSet
    }
  }

  def currentOS: OS = {
    val osName = System.getProperty("os.name")
    if (osName == "Mac OS X") {
      OS.MacOS
    } else if (osName.startsWith("Win")) {
      OS.Windows
    } else if (osName == "Linux") {
      OS.Linux
    } else {
      throw new Error(s"unknown os $osName")
    }
  }
  def currentArch: Arch = {
    val archName = System.getProperty("os.arch")
    archName match {
      case "x86_64" | "amd64" => Arch.X64
      case "aarch64" => Arch.Arm64
      case _ => throw new Error(s"Unknown arch $archName")
    }
  }

  def getResDirOS(config: String, arch: String, buildType: String): Def.Initialize[Task[File]] = Def.task {
    val version = skiaVersion.value
    val artifactId = s"Skia-$version-$config-$buildType-$arch"
    val cacheDir = dependencyCacheDirectory.value

    Path.resolve(cacheDir)(new File(s"skia/$version/$artifactId")).get
  }


  def selectTaskFromOSArch(config: String, arch: String, buildType: String): TaskKey[Set[File]] = {
    assert(buildType == "Release" || buildType == "Debug")
    val isRelease = buildType == "Release"
    config match {
      /*
      case "wasm" => {
        if (isRelease)
          downloadWasmRelease
        else
          downloadWasmDebug
      }
      */
      case "windows" => {
        arch match {
          case "x64" => {
            if (isRelease) {
              downloadWindowsX64Release
            } else {
              downloadWindowsX64Debug
            }
          }
          case "arm64" => {
            if (isRelease) {
              downloadWindowsArm64Release
            } else {
              downloadWindowsArm64Debug
            }
          }
        }
      }
      case "macos" => {
        arch match {
          case "x64" => {
            if (isRelease) {
              downloadMacosX64Release
            } else {
              downloadMacosX64Debug
            }
          }
          case "arm64" => {
            if (isRelease) {
              downloadMacosArm64Release
            } else {
              downloadMacosArm64Debug
            }
          }
        }
      }
      case "linux" => {
        arch match {
          case "x64" => {
            if (isRelease) {
              downloadLinuxX64Release
            } else {
              downloadLinuxX64Debug
            }
          }
          case "arm64" => {
            if (isRelease) {
              downloadLinuxArm64Release
            } else {
              downloadLinuxArm64Debug
            }
          }
        }
      }
    }
  }

  def downloadForCurrentOS(buildType: String): Def.Initialize[Task[Set[File]]] = Def.taskDyn {
    val goodOsName = currentOS.id
    val goodArch = currentArch.id


    val daKey = selectTaskFromOSArch(goodOsName, goodArch, buildType)


    daKey.toTask

  }

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    downloadLinuxX64Debug := {
      downloadAndUnzipTask("linux", "x64")("Debug")
    }.value,
    downloadLinuxX64Release := {
      downloadAndUnzipTask("linux", "x64")("Release")
    }.value,
    downloadLinuxArm64Debug := {
      downloadAndUnzipTask("linux", "arm64")("Debug")
    }.value,
    downloadLinuxArm64Release := {
      downloadAndUnzipTask("linux", "arm64")("Release")
    }.value,
    downloadWindowsX64Debug := {
      downloadAndUnzipTask("windows", "x64")("Debug")
    }.value,
    downloadWindowsX64Release := {
      downloadAndUnzipTask("windows", "x64")("Release")
    }.value,
    downloadWindowsArm64Debug := {
      downloadAndUnzipTask("windows", "arm64")("Debug")
    }.value,
    downloadWindowsArm64Release := {
      downloadAndUnzipTask("windows", "arm64")("Release")
    }.value,
    downloadMacosX64Debug := {
      downloadAndUnzipTask("macos", "x64")("Debug")
    }.value,
    downloadMacosX64Release := {
      downloadAndUnzipTask("macos", "x64")("Release")
    }.value,
    downloadMacosArm64Debug := {
      downloadAndUnzipTask("macos", "arm64")("Debug")
    }.value,
    downloadMacosArm64Release := {
      downloadAndUnzipTask("macos", "arm64")("Release")
    }.value,
    /*
    downloadWasmDebug := {
      downloadAndUnzipTask("wasm", "wasm")("Debug")
    }.value,
    downloadWasmRelease := {
      downloadAndUnzipTask("wasm", "wasm")("Release")
    }.value,
    */
    skiaDownloadCurrentOsRelease := {
      downloadForCurrentOS("Release")
    }.value,
    skiaDownloadCurrentOsDebug := {
      downloadForCurrentOS("Debug")
    }.value,
    skiaDownloadCurrentOs := Def.taskDyn {
      val bType = skiaBuildType.value
      downloadForCurrentOS(bType.id)
    }.value,
    skiaOSCacheDir := Def.taskDyn {
      val bType =  skiaBuildType.value
      getResDirOS(currentOS.id, currentArch.id, bType.id)
    }.value,

    skiaVersion := "m116-d2c211228d",
    skiaBuildType := SkiaBuildType.Debug,
  )
}