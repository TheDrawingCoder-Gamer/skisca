package gay.menkissing
package skisca.impl

import java.util.Locale

enum OperatingSystem extends Enum[OperatingSystem] {
  case WINDOWS
  case LINUX
  case MACOS
  case UNKNOWN
}

object OperatingSystem {
  val CURRENT: OperatingSystem = {
    val os = System.getProperty("os.name").toLowerCase(Locale.ROOT)
    if (os.contains("mac") || os.contains("darwin")) {
      MACOS
    } else if (os.contains("windows")) {
      WINDOWS
    } else if (os.contains("nux") || os.contains("nix")) {
      LINUX
    } else {
      UNKNOWN
    }
  }
}