package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.util.*
import scala.util.Using

object Library {
  @ApiStatus.Internal
  @volatile var _loaded = false
  val LIBRARY_NAME = "skisca"

  def staticLoad(): Unit = {
    if (!_loaded && !("false" == System.getProperty("skija.staticLoad"))) load()
  }

  def readResource(url: URL): String = {
    Using(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) { reader =>

      try {
        val builder = new java.lang.StringBuilder
        var ch = 0
        while ({
          ch = reader.read()
          ch >= 0
        }) {
            builder.append(ch.toChar)


          }
        builder.toString.trim
        } catch {
        case e: IOException =>
          null
      }
    }.recover {
      case e: IOException => null
    }.get
  }

  def load(): Unit = {
    if (_loaded) return
    // If `skija.loadFromLibraryPath` is set to true, use System.loadLibrary to load the native library
    val loadFromLibraryPath = System.getProperty("skija.loadFromLibraryPath")
    if ("true" == loadFromLibraryPath) {
      _loadFromLibraryPath()
      return
    }
    // If `skija.library.path` is set, load the native library from this path
    val libraryPath = System.getProperty("skija.library.path")
    if (libraryPath != null) {
      _loadFromDir(new File(libraryPath))
      return
    }
    var failedLoadFromLibraryPath = false
    // If skija is bundled in JRE, try to load the bundled native library
    // User can disable this behavior by set `skija.loadFromLibraryPath` to false
    if (loadFromLibraryPath == null) {
      val theClassFile = getClass.getResource("Library.class")
      if (theClassFile != null && "jrt" == theClassFile.getProtocol) {
        try {
          _loadFromLibraryPath()
          return
        } catch {
          case e: UnsatisfiedLinkError =>
            failedLoadFromLibraryPath = true
            Log.warn("Please use skija platform jmod when using jlink")
        }
      }
    }
    val osName = OperatingSystem.CURRENT.name.toLowerCase(Locale.ROOT)
    val archName = Architecture.CURRENT.name.toLowerCase(Locale.ROOT)
    val moduleName = "gay.menkissing.skisca." + osName + "." + archName
    val basePath = moduleName.replace('.', '/') + "/"
    Using(new ResourceFinder(moduleName)) { finder =>
      val versionFile = finder.findResource(basePath + "skija.version")
      // The platform library is bundled in the JRE, but the skija shared is not bundled.
      // Platforms without official support can provide native libraries in this way.
      if (loadFromLibraryPath == null && !failedLoadFromLibraryPath && versionFile != null && "jrt" == versionFile
        .getProtocol) {
        try {
          _loadFromLibraryPath()
          return
        } catch {
          case e: UnsatisfiedLinkError =>
            failedLoadFromLibraryPath = true
            Log.warn("Please use skija platform jmod when using jlink")
        }
      }
      // Finally, try to load the bundled native library
      val version = if (versionFile != null) {
        readResource(versionFile)
      } else {
        null
      }
      val isSnapshot = version == null || version.endsWith("SNAPSHOT")
      val tempDir = new File(System.getProperty("java.io.tmpdir"), "skija_" + (if (isSnapshot) {
        String
          .valueOf(System.nanoTime)
      } else {
        version + "_" + archName
      }))
      val libFile = _extract(finder, basePath, System.mapLibraryName(LIBRARY_NAME), tempDir)
      if (OperatingSystem.CURRENT eq OperatingSystem.WINDOWS) _extract(finder, basePath, "icudtl.dat", tempDir)
      if (isSnapshot && tempDir.exists) {
        Runtime.getRuntime.addShutdownHook(new Thread(() => {
          try {
            Files.walk(tempDir.toPath).map(_.toFile).sorted(Comparator.reverseOrder).forEach((f: File) => {
              Log.debug("Deleting " + f)
              f.delete
            })
          } catch {
            case ex: IOException =>
              ex.printStackTrace()
          }
        }))
      }
      _loadFromFile(libFile)

    }.recover {
      case e: IOException => throw new RuntimeException(e)
    }.get
  }

  @ApiStatus.Internal def _loadFromLibraryPath(): Unit = {
    Log.debug("Loading skija native library from library path")
    System.loadLibrary(LIBRARY_NAME)
    _loaded = true
    _nAfterLoad()
  }

  @ApiStatus.Internal def _loadFromDir(dir: File): Unit = {
    val libPath = new File(dir, System.mapLibraryName(LIBRARY_NAME)).getAbsolutePath
    Log.debug("Loading " + libPath)
    System.load(libPath)
    _loaded = true
    _nAfterLoad()
  }

  @ApiStatus.Internal def _loadFromFile(file: File): Unit = {
    val libPath = file.getAbsolutePath
    Log.debug("Loading " + libPath)
    System.load(libPath)
    _loaded = true
    _nAfterLoad()
  }

  @ApiStatus.Internal def _extract(finder: ResourceFinder, resourcePath: String, fileName: String, tempDir: File): File = {
    var file: File = null
    var url: URL = null
    try {
      url = finder.findResource(resourcePath + fileName)
    } catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
    if (url == null) {
      file = new File(fileName)
      if (!file.exists) throw new UnsatisfiedLinkError("Library file " + fileName + " not found in " + resourcePath)
    }
    else if ("file" == url.getProtocol) {
      try {
        file = new File(url.toURI)
      } catch {
        case e: URISyntaxException =>
          throw new RuntimeException(e)
      }
    } else {
      file = new File(tempDir, fileName)
      Using(url.openStream()) { is =>
        if (file.exists && file.length != is.available) file.delete
        if (!file.exists) {
          if (!tempDir.exists) tempDir.mkdirs
          val fileTmp = File.createTempFile(fileName, "tmp", tempDir)
          Log.debug("Extracting " + fileName + " to " + file + " via " + fileTmp)
          Files.copy(is, fileTmp.toPath, StandardCopyOption.REPLACE_EXISTING)
          Files.move(fileTmp.toPath, file.toPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
        }
        ()
      }.recover {
        case e: Exception => throw new RuntimeException(e)
      }.get
    }
    file
  }

  @ApiStatus.Internal
  @native def _nAfterLoad(): Unit
}