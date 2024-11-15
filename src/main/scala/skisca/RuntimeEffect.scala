package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object RuntimeEffect {
  def makeForShader(sksl: String): RuntimeEffect = {
    Stats.onNativeCall()
    new RuntimeEffect(_nMakeForShader(sksl))
  }

  @NotNull def makeForColorFilter(sksl: String): RuntimeEffect = {
    Stats.onNativeCall()
    new RuntimeEffect(_nMakeForColorFilter(sksl))
  }

  @ApiStatus.Internal
  @native def _nMakeForShader(sksl: String): Long

  @ApiStatus.Internal
  @native def _nMakeShader(runtimeEffectPtr: Long, uniformPtr: Long, childrenPtrs: Array[Long], localMatrix: Array[Float]): Long

  @ApiStatus.Internal
  @native def _nMakeForColorFilter(sksl: String): Long

  @ApiStatus.Internal
  @native def _nMakeColorFilter(runtimeEffectPtr: Long, uniformPtr: Long, childrenPtrs: Array[Long]): Long

  try Library.staticLoad()
}

class RuntimeEffect @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  @NotNull def makeShader(@Nullable uniforms: Data, @Nullable children: Array[Shader], @Nullable localMatrix: Matrix33): Shader = {
    Stats.onNativeCall()
    val childCount = if (children == null) {
      0
    } else {
      children.length
    }
    val childrenPtrs = new Array[Long](childCount)
    for (i <- 0 until childCount) {
      childrenPtrs(i) = Native.getPtr(children(i))
    }
    val matrix = if (localMatrix == null) {
      null
    } else {
      localMatrix.mat
    }
    new Shader(RuntimeEffect._nMakeShader(_ptr, Native.getPtr(uniforms), childrenPtrs, matrix))
  }

  @NotNull def makeColorFilter(@Nullable uniforms: Data, @Nullable children: Array[ColorFilter]): ColorFilter = {
    Stats.onNativeCall()
    val childCount = if (children == null) {
      0
    } else {
      children.length
    }
    val childrenPtrs = new Array[Long](childCount)
    for (i <- 0 until childCount) {
      childrenPtrs(i) = Native.getPtr(children(i))
    }
    new ColorFilter(RuntimeEffect._nMakeColorFilter(_ptr, Native.getPtr(uniforms), childrenPtrs))
  }
}