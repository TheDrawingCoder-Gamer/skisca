package gay.menkissing.skisca.svg

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object SVGNode {
  @ApiStatus.Internal
  @native def _nGetTag(ptr: Long): Int

  Library.staticLoad()
}

abstract class SVGNode @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  @NotNull def getTag: SVGTag = {
    try {
      Stats.onNativeCall()
      SVGTag.values.apply(SVGNode._nGetTag(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}