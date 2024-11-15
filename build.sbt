import sbt.File
import scala.sys.process._

import java.io.FilenameFilter
import scala.collection.immutable.ListSet

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.2"

// val skiscaJavaSharedLibrary = SharedLibrary("libskisca.so")
// val skiaLibraryDir = taskKey[File]("Skia Libraries Dir")
// val skiaOutputDir = taskKey[File]("Skia Output Dir")
val compileSkisca = taskKey[Seq[File]]("Compile Skisca")

def ccompileSkisca(taskOutputDir: File, baseDir: File, arch: Arch, skiaDir: File, buildType: String): Seq[File] = {
  val platformDir = baseDir / "platform"
  IO.createDirectory(taskOutputDir)
  if ((Process(Seq("cmake", "-S", platformDir.toString, "-B", ".", "-G", "Ninja", s"-DCMAKE_BUILD_TYPE=$buildType", s"-DSKIA_DIR=${skiaDir.toString}", s"-DSKIA_ARCH=${arch.id}"), taskOutputDir) #&& Process("ninja", taskOutputDir)).! != 0) {
    throw new RuntimeException("Build failed")
  }
  val libFile =
    SkiaDependencyPlugin.currentOS match {
      case OS.Windows => taskOutputDir / "skisca.dll"
      case OS.MacOS => taskOutputDir / "libskisca.dylib"
      case OS.Linux => taskOutputDir / "libskisca.so"
    }
  if (!libFile.exists()) {
    throw new RuntimeException("failed to build?")
  }
  if (SkiaDependencyPlugin.currentOS == OS.Windows) {
    Seq(libFile, skiaDir / "out" / s"$buildType-${arch.id}" / "icudtl.dat")
  } else {
    Seq(libFile)
  }
}

def currentOs: String = {
    val osName = System.getProperty("os.name")
    if (osName == "Mac OS X") {
        "macos"
    } else if (osName.startsWith("Win")) {
        "windows"
    } else if (osName == "Linux") {
        "linux"
    } else {
        throw new Error(s"unknown os $osName")
    }
}



lazy val root = (project in file("."))
  .enablePlugins(SkiaDependencyPlugin)
  .settings(
      name := "skisca2",

      idePackagePrefix := Some("gay.menkissing"),
      libraryDependencies += "io.github.humbleui" % "types" % "0.2.0",
      libraryDependencies += "org.jetbrains" % "annotations" % "26.0.1",
    Compile / compileSkisca := {
      val cacheDirectory = streams.value.cacheDirectory
      val outDir = cacheDirectory / s"${skiaBuildType.value.id}-${SkiaDependencyPlugin.currentOS.id}-${SkiaDependencyPlugin.currentArch.id}"
      ccompileSkisca(outDir, baseDirectory.value, SkiaDependencyPlugin.currentArch, skiaOSCacheDir.value, skiaBuildType.value.id)
    },
    Compile / packageBin / mappings ++= {
      val daFiles = (Compile / compileSkisca).value
      daFiles map { it =>
        it -> (s"gay/menkissing/skisca/${SkiaDependencyPlugin.currentOS.id}/${SkiaDependencyPlugin.currentArch.id}/" + it.getName)
      }
    },
    /*
      skiaLibraryDir := {
        val skiaDir = skiaOSCacheDir.value
        val outputName = s"${skiaBuildType.value.id}-${SkiaDependencyPlugin.currentArch.id}"
        skiaDir / "include" / outputName
      },
      skiaOutputDir := {
        val skiaDir = skiaOSCacheDir.value
        val outputName = s"${skiaBuildType.value.id}-${SkiaDependencyPlugin.currentArch.id}"
        skiaDir / "out" / outputName
      },
      (Compile / cxxCompile) := (Compile / cxxCompile).dependsOn(skiaDownloadCurrentOs).evaluated,
      Compile / ccTargets := ListSet(skiscaJavaSharedLibrary),
      Compile / cxxFlagsDynamic := Map(
          skiscaJavaSharedLibrary -> {
              val outputDir = skiaOutputDir.value
              val base = Seq(
                  "-DFT2_BUILD_LIBRARY",
                  "-DFT_CONFIG_MODULES_H=<include/freetype-android/ftmodule.h>",
                  "-DFT_CONFIG_OPTIONS_H=<include/freetype-android/ftoption.h>",
                  "-DPNG_INTEL_SSE",
                  "-DPNG_SET_OPTION_SUPPORTED",
                  "-DSK_GL",
                  "-DSK_GANESH",
                  "-DSK_SHAPER_HARFBUZZ_AVAILABLE",
                  "-DSK_UNICODE_AVAILABLE",
                  "-DU_DISABLE_RENAMING",
                  "-DU_USING_ICU_NAMESPACE=0",
                  "-DU_DISABLE_RENAMING",
                  "-DSK_USING_THIRD_PARTY_ICU",
                  "-DU_COMMON_IMPLEMENTATION",
                  "-DU_STATIC_IMPLEMENTATION",
                  "-DU_ENABLE_DYLOAD=0",
                  "-DU_I18N_IMPLEMENTATION",
                  "-D_XOPEN_SOURCE=0",
                  "-fPIC",


              )
              val platformSpecific = currentOs match {
                  case "windows" => Seq("-DNOMINMAX", "-DWIN32_LEAN_AND_MEAN", "-DSK_DIRECT3D", "-DU_NOEXCEPT=")
                  case "macos" => Seq("-DSK_ENABLE_API_AVAILABLE", "-DSK_METAL", "-DSK_SHAPER_CORETEXT_AVAILABLE")
                  case "linux" => Seq("-DSK_R32_SHIFT=16")
              }
              val debugStuff = if (skiaBuildType.value == SkiaBuildType.Release) Seq("-DNDEBUG") else Seq()
              List.concat(base, platformSpecific, debugStuff)
          }
      ),
    Compile / ldLibraries := Map(
      skiscaJavaSharedLibrary -> {

        val base = Seq(
          "skia",
          "skottie",
          "sksg",
          "skunicode",
          "skshaper",
          "skresources",
          "svg",
          "harfbuzz"
        )
        val freetype = currentOs match {
          case "linux" => Seq("freetype", "fontconfig")
          case _ => Seq("freetype2")
        }
        val platformSpecific = currentOs match {
          case "windows" => Seq("d3d12", "dxgi", "d3dcompiler", "opengl32")
          case "macos" => Seq("GL", "Cocoa", "Metal")
          case "linux" => Seq("GL", "EGL")
        }
        Seq.concat(base, freetype, platformSpecific)
      }
    ),
    Compile / ldLibraryDirectoriesDynamic := Map(
      skiscaJavaSharedLibrary -> {
        val outputDir = skiaOutputDir.value
        Seq(outputDir)
      }
    ),
    Compile / cxxSources := Map(
          skiscaJavaSharedLibrary -> {
              val ccBase = baseDirectory.value / "src/main/cc"
              val ccFilter: FilenameFilter = (file, it) => it.endsWith("cc")
              List.concat(
                  ccBase.listFiles(ccFilter),
                  (ccBase / "impl").listFiles(ccFilter),
                  (ccBase / "paragraph").listFiles(ccFilter),
                  (ccBase / "shaper").listFiles(ccFilter),
                  (ccBase / "svg").listFiles(ccFilter),
                  (ccBase / "resources").listFiles(ccFilter),
                  (ccBase / "skottie").listFiles(ccFilter),
                  (ccBase / "sksg").listFiles(ccFilter)
              )
          }
      ),
      Compile / cxxIncludes := Map(
          skiscaJavaSharedLibrary -> {
              val javaHome = new File(System.getProperty("java.home"))
              val base = javaHome / "include"
              val osPlatform = currentOs match {
                  case "windows" => base / "win32"
                  case "macos" => base / "darwin"
                  case "linux" => base / "linux"
              }
              Seq(base, osPlatform)
          }
      ),
      Compile / cxxIncludeDirectories := { CcPlugin.combineMaps(
        (Compile / cxxIncludes).value,
        Map( skiscaJavaSharedLibrary -> {
          val curSkiaDir = skiaOSCacheDir.value
          Seq(curSkiaDir,
            curSkiaDir / "include",
            curSkiaDir / "include/core",
            curSkiaDir / "include/codec",
            curSkiaDir / "include/effects",
            curSkiaDir / "include/utils",
            curSkiaDir / "include/pathops",
            curSkiaDir / "modules/skunicode/include",
            curSkiaDir / "modules/skshaper/include",
            curSkiaDir / "third_party/externals/freetype/include",
            curSkiaDir / "third_party/externals/harfbuzz/src",
            curSkiaDir / "modules/svg/include",
            curSkiaDir / "modules/skresources/include",
            curSkiaDir / "modules/skottie/include",
            curSkiaDir / "modules/sksg/include",
            curSkiaDir / "modules/skparagraph/include",
            curSkiaDir / "include/svg",
            curSkiaDir / "include/config",
            curSkiaDir / "include/gpu",
            curSkiaDir / "third_party_skcms",
            curSkiaDir / "include/third_party/skcms",
            curSkiaDir / "include/encode",
            curSkiaDir / "third_party/externals/icu/source/common",
            curSkiaDir / "third_party/icu"
          )
        })
      )},

     */
  )

lazy val scenesSample = (project in file("samples/scenes"))
  .dependsOn(root)
  .settings(
    idePackagePrefix := Some("gay.menkissing.skisca.examples.scenes"),
  )
lazy val lwjglSample = (project in file("samples/lwjgl"))
  .dependsOn(root, scenesSample)
  .settings(

    libraryDependencies ++= {
      val libs = Seq(
        "lwjgl",
        "lwjgl-glfw",
        "lwjgl-opengl",
      )
      val nativesClassifier =
        if (SkiaDependencyPlugin.currentArch == Arch.X64) {
          "natives-" + currentOs
        } else {
          "natives-" + currentOs + "-" + SkiaDependencyPlugin.currentArch.id
        }
      Seq.concat(libs.map("org.lwjgl" % _ % "3.3.1"), libs.map("org.lwjgl" % _ % "3.3.1" classifier nativesClassifier))
    }

  )
