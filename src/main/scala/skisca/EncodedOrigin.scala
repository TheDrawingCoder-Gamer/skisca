package gay.menkissing
package skisca

import org.jetbrains.annotations.*

enum EncodedOrigin {
  case _UNUSED,

  /**
   * Default
   */
  TOP_LEFT,

  /**
   * Reflected across y-axis
   */
  TOP_RIGHT,

  /**
   * Rotated 180
   */
  BOTTOM_RIGHT,

  /**
   * Reflected across x-axis
   */
  BOTTOM_LEFT,

  /**
   * Reflected across x-axis, Rotated 90 CCW
   */
  LEFT_TOP,

  /**
   * Rotated 90 CW
   */
  RIGHT_TOP,

  /**
   * Reflected across x-axis, Rotated 90 CW
   */
  RIGHT_BOTTOM,

  /**
   * Rotated 90 CCW
   */
  LEFT_BOTTOM

  /**
   * Given an encoded origin and the width and height of the source data, returns a matrix
   * that transforms the source rectangle with upper left corner at [0, 0] and origin to a correctly
   * oriented destination rectangle of [0, 0, w, h].
   */
  def toMatrix(w: Int, h: Int): Matrix33 = {
    this match {
      case TOP_LEFT => {
        Matrix33.IDENTITY
      }
      case TOP_RIGHT => {
        Matrix33.apply(-1, 0, w, 0, 1, 0, 0, 0, 1)
      }
      case BOTTOM_RIGHT => {
        Matrix33.apply(-1, 0, w, 0, -1, h, 0, 0, 1)
      }
      case BOTTOM_LEFT => {
        Matrix33.apply(1, 0, 0, 0, -1, h, 0, 0, 1)
      }
      case LEFT_TOP => {
        Matrix33.apply(0, 1, 0, 1, 0, 0, 0, 0, 1)
      }
      case RIGHT_TOP => {
        Matrix33.apply(0, -1, w, 1, 0, 0, 0, 0, 1)
      }
      case RIGHT_BOTTOM => {
        Matrix33.apply(0, -1, w, -1, 0, h, 0, 0, 1)
      }
      case LEFT_BOTTOM => {
        Matrix33.apply(0, 1, 0, -1, 0, h, 0, 0, 1)
      }
      case _ => {
        throw new IllegalArgumentException("Unsupported origin " + this)
      }
    }
  }

  /**
   * Return true if the encoded origin includes a 90 degree rotation, in which case the width
   * and height of the source data are swapped relative to a correctly oriented destination.
   */
  def swapsWidthHeight: Boolean = {
    this match {
      case LEFT_TOP | RIGHT_TOP | RIGHT_BOTTOM | LEFT_BOTTOM => {
        true
      }
      case _ => {
        false
      }
    }
  }
}