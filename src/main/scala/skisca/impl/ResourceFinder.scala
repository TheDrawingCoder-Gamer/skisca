package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

import java.io.*
import java.lang.module.*
import java.net.*
import java.nio.file.*
import java.util.*

object ResourceFinder {
  @throws[IOException]
  private def _findResource(reader: ModuleReader, path: String) = {
    val uri = reader.find(path).orElse(null)
    if (uri != null) {
      uri.toURL
    } else {
      null
    }
  }
}

final class ResourceFinder(
                            /*
                                 * For multi-release jars, the APIs of the same class in different versions should be the same.
                                 */
                            private val _moduleName: String) extends Closeable {
  private var _moduleReader: Optional[ModuleReader] = null

  @throws[IOException]
  def findResource(path: String): URL = {
    val url = classOf[ResourceFinder].getClassLoader.getResource(path)
    if (url != null) return url
    if (_moduleReader != null) {
      if (_moduleReader.isPresent) {
        return ResourceFinder._findResource(_moduleReader.get, path)
      } else {
        return null
      }
    }
    var moduleReference = ModuleFinder.ofSystem.find(_moduleName)
    if (moduleReference.isPresent) {
      _moduleReader = Optional.of(moduleReference.get.open)
      return ResourceFinder._findResource(_moduleReader.get, path)
    }
    val modulePath = System.getProperty("jdk.module.path")
    if (modulePath != null && !modulePath.isEmpty) {
      val paths = modulePath.split(File.pathSeparator).map(it => Paths.get(it))
      moduleReference = ModuleFinder.of(paths*).find(_moduleName)
      if (moduleReference.isPresent) {
        _moduleReader = Optional.of(moduleReference.get.open)
        return ResourceFinder._findResource(_moduleReader.get, path)
      }
    }
    _moduleReader = Optional.empty
    null
  }

  @throws[IOException]
  override def close(): Unit = {
    if (_moduleReader != null && _moduleReader.isPresent) _moduleReader.get.close()
  }
}