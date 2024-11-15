package gay.menkissing.skisca

import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * <p>Matrix holds a 3x3 matrix for transforming coordinates. This allows mapping
 * Point and vectors with translation, scaling, skewing, rotation, and
 * perspective.</p>
 *
 * <p>Matrix includes a hidden variable that classifies the type of matrix to
 * improve performance. Matrix is not thread safe unless getType() is called first.</p>
 *
 * @see <a href="https://fiddle.skia.org/c/@Matrix_063">https://fiddle.skia.org/c/@Matrix_063</a>
 */
object Matrix33 {
  /**
   * An identity Matrix33:
   *
   * <pre><code>
   * | 1 0 0 |
   * | 0 1 0 |
   * | 0 0 1 |
   * </code></pre>
   */
  @NotNull val IDENTITY: Matrix33 = makeTranslate(0, 0)

  def apply(m11: Float, m12: Float, m13: Float, m21: Float, m22: Float, m23: Float, m31: Float, m32: Float, m33: Float): Matrix33 = {
    new Matrix33(Array(m11, m12, m13, m21, m22, m23, m31, m32, m33))
  }
  /**
   * <p>Creates a Matrix33 to translate by (dx, dy). Returned matrix is:</p>
   *
   * <pre><code>
   * | 1 0 dx |
   * | 0 1 dy |
   * | 0 0  1 |
   * </code></pre>
   *
   * @param dx horizontal translation
   * @param dy vertical translation
   * @return Matrix33 with translation
   */
  @NotNull
  @Contract("_, _ -> new") def makeTranslate(dx: Float, dy: Float) = {
    new Matrix33(Array[Float](1, 0, dx, 0, 1, dy, 0, 0, 1))
  }

  /**
   * <p>Creates a Matrix33 to scale by s. Returned matrix is:</p>
   *
   * <pre><code>
   * | s 0 0 |
   * | 0 s 0 |
   * | 0 0 1 |
   * </code></pre>
   *
   * @param s scale factor
   * @return Matrix33 with scale
   */
  @NotNull def makeScale(s: Float): Matrix33 = {
    makeScale(s, s)
  }

  /**
   * <p>Creates a Matrix33 to scale by (sx, sy). Returned matrix is:</p>
   *
   * <pre> <code>
   * | sx  0  0 |
   * |  0 sy  0 |
   * |  0  0  1 |
   * </code></pre>
   *
   * @param sx horizontal scale factor
   * @param sy vertical scale factor
   * @return Matrix33 with scale
   */
  @NotNull def makeScale(sx: Float, sy: Float) = {
    new Matrix33(Array[Float](sx, 0, 0, 0, sy, 0, 0, 0, 1))
  }

  /**
   * Creates a Matrix33 to rotate by |deg| about a pivot point at (0, 0).
   *
   * @param deg rotation angle in degrees (positive rotates clockwise)
   * @return Matrix33 with rotation
   */
  @NotNull def makeRotate(deg: Float): Matrix33 = {
    val rad = Math.toRadians(deg)
    var sin = Math.sin(rad)
    var cos = Math.cos(rad)
    val tolerance = 1f / (1 << 12)
    if (Math.abs(sin) <= tolerance) sin = 0
    if (Math.abs(cos) <= tolerance) cos = 0
    new Matrix33(Array[Float](cos.toFloat, -sin.toFloat, 0, sin.toFloat, cos.toFloat, 0, 0, 0, 1))
  }

  /**
   * Creates a Matrix33 to rotate by |deg| about a pivot point at pivot.
   *
   * @param deg   rotation angle in degrees (positive rotates clockwise)
   * @param pivot pivot point
   * @return Matrix33 with rotation
   */
  @NotNull def makeRotate(deg: Float, pivot: Point): Matrix33 = {
    makeRotate(deg, pivot._x, pivot._y)
  }

  /**
   * Creates a Matrix33 to rotate by |deg| about a pivot point at (pivotx, pivoty).
   *
   * @param deg    rotation angle in degrees (positive rotates clockwise)
   * @param pivotx x-coord of pivot
   * @param pivoty y-coord of pivot
   * @return Matrix33 with rotation
   */
  @NotNull def makeRotate(deg: Float, pivotx: Float, pivoty: Float): Matrix33 = {
    val rad = Math.toRadians(deg)
    var sin = Math.sin(rad)
    var cos = Math.cos(rad)
    val tolerance = 1f / (1 << 12)
    if (Math.abs(sin) <= tolerance) sin = 0
    if (Math.abs(cos) <= tolerance) cos = 0
    new Matrix33(Array[Float](cos.toFloat, -sin.toFloat, (pivotx - pivotx * cos + pivoty * sin).toFloat, sin
      .toFloat, cos.toFloat, (pivoty - pivoty * cos - pivotx * sin).toFloat, 0, 0, 1))
  }

  /**
   * <p>Creates a Matrix33 to skew by (sx, sy). Returned matrix is:</p>
   *
   * <pre> <code>
   * | 1  sx  0 |
   * | sy  1  0 |
   * |  0  0  1 |
   * </code></pre>
   *
   * @param sx horizontal skew factor
   * @param sy vertical skew factor
   * @return Matrix33 with skew
   */
  @NotNull def makeSkew(sx: Float, sy: Float) = {
    new Matrix33(Array[Float](1, sx, 0, sy, 1, 0, 0, 0, 1))
  }
}

class Matrix33(
                      /**
                       * <p>Matrix33 elements are in row-major order.</p>
                       *
                       * <pre><code>
                       * | scaleX   skewX  transX |
                       * |  skewY  scaleY  transY |
                       * | persp0  persp1  persp2 |
                       * </code></pre>
                       */
                      val mat: Array[Float]) {
  assert(mat.length == 9, if ("Expected 9 elements, got " + mat == null) {
    null
  } else {
    mat.length
  })

  @NotNull def makePreScale(sx: Float, sy: Float) = new Matrix33(Array[Float](mat(0) * sx, mat(1) * sy, mat(2), mat(3) * sx, mat(4) * sy, mat(5), mat(6) * sx, mat(7) * sy, mat(8)))

  /**
   * <p>Creates Matrix33 by multiplying this by other. This can be thought of mapping by other before applying Matrix.</p>
   *
   * <p>Given:</p>
   *
   * <pre><code>
   * | A B C |          | J K L |
   * this = | D E F |, other = | M N O |
   * | G H I |          | P Q R |
   * </code></pre>
   *
   * <p>Returns:</p>
   *
   * <pre><code>
   * | A B C |   | J K L |   | AJ+BM+CP AK+BN+CQ AL+BO+CR |
   * this * other = | D E F | * | M N O | = | DJ+EM+FP DK+EN+FQ DL+EO+FR |
   * | G H I |   | P Q R |   | GJ+HM+IP GK+HN+IQ GL+HO+IR |
   * </code></pre>
   *
   * @param other Matrix on right side of multiply expression
   * @return this multiplied by other
   */
  @NotNull def makeConcat(other: Matrix33) = {
    new Matrix33(Array[Float](mat(0) * other.mat(0) + mat(1) * other
      .mat(3) + mat(2) * other.mat(6), mat(0) * other.mat(1) + mat(1) * other.mat(4) + mat(2) * other
      .mat(7), mat(0) * other.mat(2) + mat(1) * other.mat(5) + mat(2) * other.mat(8), mat(3) * other
      .mat(0) + mat(4) * other.mat(3) + mat(5) * other.mat(6), mat(3) * other.mat(1) + mat(4) * other
      .mat(4) + mat(5) * other.mat(7), mat(3) * other.mat(2) + mat(4) * other.mat(5) + mat(5) * other
      .mat(8), mat(6) * other.mat(0) + mat(7) * other.mat(3) + mat(8) * other.mat(6), mat(6) * other
      .mat(1) + mat(7) * other.mat(4) + mat(8) * other.mat(7), mat(6) * other.mat(2) + mat(7) * other
      .mat(5) + mat(8) * other.mat(8)))
  }

  /**
   * <p>When converting from Matrix33 to Matrix44, the third row and
   * column remain as identity:</p>
   *
   * <pre><code>
   * [ a b c ]      [ a b 0 c ]
   * [ d e f ]  -&gt;  [ d e 0 f ]
   * [ g h i ]      [ 0 0 1 0 ]
   * [ g h 0 i ]
   * </code></pre>
   */
  @NotNull def asMatrix44 = {
    Matrix44(mat(0), mat(1), 0, mat(2), mat(3), mat(4), 0, mat(5), 0, 0, 1, 0, mat(6), mat(7), 0, mat(8))
  }
}