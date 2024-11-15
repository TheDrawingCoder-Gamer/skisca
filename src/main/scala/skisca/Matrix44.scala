package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * <p>4x4 matrix used by SkCanvas and other parts of Skia.</p>
 *
 * Skia assumes a right-handed coordinate system:
 * +X goes to the right
 * +Y goes down
 * +Z goes into the screen (away from the viewer)
 */
object Matrix44 {
  val IDENTITY = Matrix44(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1)
  
  def apply(m11: Float, m12: Float, m13: Float, m14: Float, m21: Float, m22: Float, m23: Float, m24: Float,
            m31: Float, m32: Float, m33: Float, m34: Float, m41: Float, m42: Float, m43: Float, m44: Float): Matrix44 =
    new Matrix44(Array(m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44))
}

class Matrix44(
                      /**
                       * Matrix elements are in row-major order.
                       */
                      val mat: Array[Float])
/**
 * The constructor parameters are in row-major order.
 */ {
  assert(mat.length == 16, if ("Expected 16 elements, got " + mat == null) {
    null
  } else {
    mat.length
  })

  /**
   * <p>When converting from Matrix44 to Matrix33, the third row and
   * column is dropped.</p>
   *
   * <pre><code>
   * [ a b _ c ]      [ a b c ]
   * [ d e _ f ]  -&gt;  [ d e f ]
   * [ _ _ _ _ ]      [ g h i ]
   * [ g h _ i ]
   * </code></pre>
   */
  def asMatrix33 = {
    new Matrix33(Array(mat(0), mat(1), mat(3), mat(4), mat(5), mat(7), mat(12), mat(13), mat(15)))
  }
}