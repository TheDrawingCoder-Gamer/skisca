package gay.menkissing.skisca.svg

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object SVGDOM {
  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long): Long

  @ApiStatus.Internal
  @native def _nGetRoot(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetContainerSize(ptr: Long): Point

  @ApiStatus.Internal
  @native def _nSetContainerSize(ptr: Long, width: Float, height: Float): Unit

  @ApiStatus.Internal
  @native def _nRender(ptr: Long, canvasPtr: Long): Unit

  Library.staticLoad()
}

class SVGDOM @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  def this(@NotNull data: Data) = {
    this(SVGDOM._nMakeFromData(Native.getPtr(data)))
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(data)
  }

  @Nullable def getRoot: SVGSVG = {
    try {
      Stats.onNativeCall()
      val ptr = SVGDOM._nGetRoot(_ptr)
      if (ptr == 0) {
        null
      } else {
        new SVGSVG(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Deprecated. Use getRoot().getIntrinsicSize() instead
   */
  @NotNull
  @deprecated def getContainerSize: Point = {
    try {
      SVGDOM._nGetContainerSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> this") def setContainerSize(width: Float, height: Float): SVGDOM = {
    Stats.onNativeCall()
    SVGDOM._nSetContainerSize(_ptr, width, height)
    this
  }

  @NotNull
  @Contract("-> this") def setContainerSize(size: Point): SVGDOM = {
    Stats.onNativeCall()
    SVGDOM._nSetContainerSize(_ptr, size._x, size._y)
    this
  }

  // sk_sp<SkSVGNode>* findNodeById(const char* id);
  @NotNull
  @Contract("-> this") def render(@NotNull canvas: Canvas): SVGDOM = {
    try {
      Stats.onNativeCall()
      SVGDOM._nRender(_ptr, Native.getPtr(canvas))
      this
    } finally {
      ReferenceUtil.reachabilityFence(canvas)
    }
  }
}