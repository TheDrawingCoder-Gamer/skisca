package gay.menkissing.skisca.svg

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object SVGSVG {
  @ApiStatus.Internal
  @native def _nGetX(ptr: Long): SVGLength

  @ApiStatus.Internal
  @native def _nGetY(ptr: Long): SVGLength

  @ApiStatus.Internal
  @native def _nGetWidth(ptr: Long): SVGLength

  @ApiStatus.Internal
  @native def _nGetHeight(ptr: Long): SVGLength

  @ApiStatus.Internal
  @native def _nGetPreserveAspectRatio(ptr: Long): SVGPreserveAspectRatio

  @ApiStatus.Internal
  @native def _nGetViewBox(ptr: Long): Rect

  @ApiStatus.Internal
  @native def _nGetIntrinsicSize(ptr: Long, width: Float, height: Float, dpi: Float): Point

  @ApiStatus.Internal
  @native def _nSetX(ptr: Long, value: Float, unit: Int): Unit

  @ApiStatus.Internal
  @native def _nSetY(ptr: Long, value: Float, unit: Int): Unit

  @ApiStatus.Internal
  @native def _nSetWidth(ptr: Long, value: Float, unit: Int): Unit

  @ApiStatus.Internal
  @native def _nSetHeight(ptr: Long, value: Float, unit: Int): Unit

  @ApiStatus.Internal
  @native def _nSetPreserveAspectRatio(ptr: Long, align: Int, scale: Int): Unit

  @ApiStatus.Internal
  @native def _nSetViewBox(ptr: Long, l: Float, t: Float, r: Float, b: Float): Unit

  Library.staticLoad()
}

class SVGSVG @ApiStatus.Internal(ptr: Long) extends SVGContainer(ptr) {
  @NotNull def getX: SVGLength = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetX(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getY: SVGLength = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetY(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getWidth: SVGLength = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetWidth(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getHeight: SVGLength = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetHeight(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getPreserveAspectRatio: SVGPreserveAspectRatio = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetPreserveAspectRatio(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @Nullable def getViewBox: Rect = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetViewBox(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getIntrinsicSize(@NotNull lc: SVGLengthContext): Point = {
    try {
      Stats.onNativeCall()
      SVGSVG._nGetIntrinsicSize(_ptr, lc.width, lc.height, lc.dpi)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setX(@NotNull length: SVGLength): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetX(_ptr, length.value, length.unit.ordinal)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setY(@NotNull length: SVGLength): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetY(_ptr, length.value, length.unit.ordinal)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setWidth(@NotNull length: SVGLength): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetWidth(_ptr, length.value, length.unit.ordinal)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setHeight(@NotNull length: SVGLength): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetHeight(_ptr, length.value, length.unit.ordinal)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setPreserveAspectRatio(@NotNull ratio: SVGPreserveAspectRatio): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetPreserveAspectRatio(_ptr, ratio.align.value, ratio.scale.ordinal)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("_ -> this") def setViewBox(@NotNull viewBox: Rect): SVGSVG = {
    try {
      Stats.onNativeCall()
      SVGSVG._nSetViewBox(_ptr, viewBox._left, viewBox._top, viewBox._right, viewBox._bottom)
      this
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}