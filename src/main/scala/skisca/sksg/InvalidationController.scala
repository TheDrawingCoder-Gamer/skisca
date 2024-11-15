package gay.menkissing.skisca.sksg

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * <p>Receiver for invalidation events.</p>
 * <p>Tracks dirty regions for repaint.</p>
 */
object InvalidationController {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nMake(): Long

  @native def _nInvalidate(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, matrix: Array[Float]): Unit

  @native def _nGetBounds(ptr: Long): Rect

  @native def _nReset(ptr: Long): Unit

  Library.staticLoad()
}

class InvalidationController @ApiStatus.Internal(ptr: Long) extends Managed(ptr, InvalidationController._FinalizerHolder
                                                                                                       .PTR, true) {
  def this() = {
    this(InvalidationController._nMake())
  }

  @NotNull
  @Contract("-> this") def invalidate(left: Float, top: Float, right: Float, bottom: Float, @Nullable matrix: Matrix33): InvalidationController = {
    Stats.onNativeCall()
    InvalidationController._nInvalidate(_ptr, left, top, right, bottom, if (matrix == null) {
      Matrix33.IDENTITY.mat
    } else {
      matrix.mat
    })
    this
  }

  @NotNull def getBounds: Rect = {
    try {
      Stats.onNativeCall()
      InvalidationController._nGetBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> this") def reset(): InvalidationController = {
    Stats.onNativeCall()
    InvalidationController._nReset(_ptr)
    this
  }
}