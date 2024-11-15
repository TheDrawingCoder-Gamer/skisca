package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

/**
 * A utility proxy base class for implementing draw/paint filters.
 */
object PaintFilterCanvas {
  @native def _nMake(canvasPtr: Long, unrollDrawable: Boolean): Long
  @native def _nAttachToJava(self: PaintFilterCanvas, canvasPtr: Long): Unit

  Library.staticLoad()
}

abstract class PaintFilterCanvas(@NotNull canvas: Canvas, unrollDrawable: Boolean)

/**
 * @param unrollDrawable if needed to filter nested drawable content using this canvas (for drawables there is no paint to filter)
 */
  extends Canvas(PaintFilterCanvas._nMake(Native.getPtr(canvas), unrollDrawable), true, canvas) {
  Stats.onNativeCall()
  PaintFilterCanvas._nAttachToJava(this, _ptr)
  Stats.onNativeCall()
  ReferenceUtil.reachabilityFence(canvas)

  /**
   * Called with the paint that will be used to draw the specified type.
   * The implementation may modify the paint as they wish.
   *
   * The result boolean is used to determine whether the draw op is to be
   * executed (true) or skipped (false).
   *
   * Note: The base implementation calls onFilter() for top-level/explicit paints only.
   */
  def onFilter(@NotNull paint: Paint): Boolean

  final def onFilter(paintPtr: Long): Boolean = {
    val paint = new Paint(paintPtr, false)
    onFilter(paint)
  }

  

  
}