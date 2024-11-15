package gay.menkissing.skisca

import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * Contains the state used to create the layer
 */
case class SaveLayerRec(@Nullable bounds: Rect, @Nullable paint: Paint, @Nullable backdrop: ImageFilter, flags: Int) {


  /**
   * Sets bounds, paint, backdrop and flags
   *
   * @param bounds   layer dimensions; may be null
   * @param paint    applied to layer when overlaying prior layer; may be null
   * @param backdrop If not null, this causes the current layer to be filtered by
   *                 backdrop, and then drawn into the new layer
   *                 (respecting the current clip).
   *                 If null, the new layer is initialized with transparent-black.
   * @param flags    options to modify layer
   */
  def this(@Nullable bounds: Rect, @Nullable paint: Paint, @Nullable backdrop: ImageFilter, flags: Array[SaveLayerRecFlag]) = {
    this(bounds, paint, backdrop, flags.foldLeft(0)((l, r) => l | r._flag))
  }
  /**
   * Sets bounds, paint, and backdrop to null. Clears flags.
   */
  def this() = {
    this(null, null, null, new Array[SaveLayerRecFlag](0))
  }

  /**
   * Sets bounds and paint. Sets backdrop to null, clears flags
   *
   * @param bounds layer dimensions; may be null
   * @param paint  applied to layer when overlaying prior layer; may be null
   */
  def this(@Nullable bounds: Rect, @Nullable paint: Paint) = {
    this(bounds, paint, null, new Array[SaveLayerRecFlag](0))
  }

  /**
   * Sets bounds, paint, and flags. Sets backdrop to null
   *
   * @param bounds layer dimensions; may be null
   * @param paint  applied to layer when overlaying prior layer; may be null
   * @param flags  options to modify layer
   */
  def this(@Nullable bounds: Rect, @Nullable paint: Paint, flags: SaveLayerRecFlag*) = {
    this(bounds, paint, null, flags.toArray)
  }

  /**
   * Sets bounds, paint, and backdrop
   *
   * @param bounds   layer dimensions; may be null
   * @param paint    applied to layer when overlaying prior layer; may be null
   * @param backdrop If not null, this causes the current layer to be filtered by
   *                 backdrop, and then drawn into the new layer
   *                 (respecting the current clip).
   *                 If null, the new layer is initialized with transparent-black.
   */
  def this(@Nullable bounds: Rect, @Nullable paint: Paint, @Nullable backdrop: ImageFilter) = {
    this(bounds, paint, backdrop, new Array[SaveLayerRecFlag](0))
  }

  /**
   * Sets bounds, paint, backdrop, and flags
   *
   * @param bounds   layer dimensions; may be null
   * @param paint    applied to layer when overlaying prior layer; may be null
   * @param backdrop If not null, this causes the current layer to be filtered by
   *                 backdrop, and then drawn into the new layer
   *                 (respecting the current clip).
   *                 If null, the new layer is initialized with transparent-black.
   * @param flags  options to modify layer
   */
  def this(@Nullable bounds: Rect, @Nullable paint: Paint, @Nullable backdrop: ImageFilter, flags: SaveLayerRecFlag*) = {
    this(bounds, paint, backdrop, flags.toArray)
  }
}