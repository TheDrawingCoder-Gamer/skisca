package gay.menkissing
package skisca

case class Color4f(r: Float, g: Float, b: Float, a: Float = 1f) {
  def flatten: Array[Float] = Array(r, g, b, a)
  def toColor: Int = {
    (Math.round(a * 255f) << 24)
    | (Math.round(r * 255f) << 16)
    | (Math.round(g * 255f) << 8)
    | (Math.round(b * 255f))
  }
  def makeLerp(other: Color4f, weight: Float): Color4f = {
    Color4f(
      r + (other.r - r) * weight,
      g + (other.g - g) * weight,
      b + (other.b - b) * weight,
      a + (other.a - a) * weight
    )
  }
}

object Color4f {
  def apply(rgba: Array[Float]): Color4f = new Color4f(rgba(0), rgba(1), rgba(2), rgba(3))
  def apply(c: Int): Color4f = Color4f((c >> 16 & 0xFF) / 255f, (c >> 8 & 0xFF) / 255f, (c & 0xFF) / 255f, (c >> 24 & 0xFF) / 255f)
  
  def flattenArray(colors: Array[Color4f]): Array[Float] = {
    val arr = Array.fill[Float](colors.length * 4)(0f)
    colors.zipWithIndex.foreach { case (color, idx) =>
      arr(idx * 4) = color.r
      arr(idx * 4 + 1) = color.g
      arr(idx * 4 + 2) = color.b
      arr(idx * 4 + 3) = color.a
    }
    arr
  }
}
