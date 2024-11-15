package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * <p>Base class for objects that draw into Canvas.</p>
 *
 * <p>The object has a generation id, which is guaranteed to be unique across all drawables. To
 * allow for clients of the drawable that may want to cache the results, the drawable must
 * change its generation id whenever its internal state changes such that it will draw differently.</p>
 */
object Drawable {
  @ApiStatus.Internal
  @native def _nMake: Long

  @ApiStatus.Internal
  @native def _nInit(self: Drawable, ptr: Long): Unit

  @ApiStatus.Internal
  @native def _nDraw(ptr: Long, canvasPtr: Long, matrix: Array[Float]): Unit

  @ApiStatus.Internal
  @native def _nMakePictureSnapshot(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nGetGenerationId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nNotifyDrawingChanged(ptr: Long): Unit

  Library.staticLoad()
}

abstract class Drawable extends RefCnt(Drawable._nMake, true) {
  Stats.onNativeCall()
  Stats.onNativeCall()
  Drawable._nInit(this, _ptr)
  @ApiStatus.Internal var _bounds: Rect = null

  /**
   * Draws into the specified content. The drawing sequence will be balanced upon return
   * (i.e. the saveLevel() on the canvas will match what it was when draw() was called,
   * and the current matrix and clip settings will not be changed.
   */
  @ApiStatus.NonExtendable def draw(canvas: Canvas): Drawable = {
    draw(canvas, null)
  }

  /**
   * Draws into the specified content. The drawing sequence will be balanced upon return
   * (i.e. the saveLevel() on the canvas will match what it was when draw() was called,
   * and the current matrix and clip settings will not be changed.
   */
  @ApiStatus.NonExtendable def draw(canvas: Canvas, x: Float, y: Float): Drawable = {
    draw(canvas, Matrix33
      .makeTranslate(x, y))
  }

  /**
   * Draws into the specified content. The drawing sequence will be balanced upon return
   * (i.e. the saveLevel() on the canvas will match what it was when draw() was called,
   * and the current matrix and clip settings will not be changed.
   */
  @ApiStatus.NonExtendable def draw(canvas: Canvas, @Nullable matrix: Matrix33): Drawable = {
    try {
      Stats.onNativeCall()
      Drawable._nDraw(_ptr, Native.getPtr(canvas), if (matrix == null) {
        null
      } else {
        matrix.mat
      })
      this
    } finally {
      ReferenceUtil.reachabilityFence(canvas)
    }
  }

  @ApiStatus.NonExtendable def makePictureSnapshot: Picture = {
    try {
      Stats.onNativeCall()
      new Picture(Drawable._nMakePictureSnapshot(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Return a unique value for this instance. If two calls to this return the same value,
   * it is presumed that calling the draw() method will render the same thing as well.</p>
   *
   * <p>Subclasses that change their state should call notifyDrawingChanged() to ensure that
   * a new value will be returned the next time it is called.</p>
   */
  def getGenerationId: Int = {
    try {
      Stats.onNativeCall()
      Drawable._nGetGenerationId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Return the (conservative) bounds of what the drawable will draw. If the drawable can
   * change what it draws (e.g. animation or in response to some external change), then this
   * must return a bounds that is always valid for all possible states.
   */
  @ApiStatus.NonExtendable
  @NotNull def getBounds: Rect = {
    if (_bounds == null) _bounds = onGetBounds
    _bounds
  }

  /**
   * Calling this invalidates the previous generation ID, and causes a new one to be computed
   * the next time getGenerationId() is called. Typically this is called by the object itself,
   * in response to its internal state changing.
   */
  @ApiStatus.NonExtendable def notifyDrawingChanged: Drawable = {
    Stats.onNativeCall()
    Drawable._nNotifyDrawingChanged(_ptr)
    _bounds = null
    this
  }

  @ApiStatus.OverrideOnly def onDraw(canvas: Canvas): Unit

  @ApiStatus.OverrideOnly
  @NotNull def onGetBounds: Rect

  @ApiStatus.Internal def _onDraw(canvasPtr: Long): Unit = {
    onDraw(new Canvas(canvasPtr, false, this))
  }


}