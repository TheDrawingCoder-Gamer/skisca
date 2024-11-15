package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

case class FontRun(end: Int, font: Font) {
  @ApiStatus.Internal def _getFontPtr: Long = {
    try {
      Native.getPtr(font)
    } finally {
      ReferenceUtil.reachabilityFence(font)
    }
  }
}