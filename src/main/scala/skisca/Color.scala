package gay.menkissing.skisca

object Color {
  // TODO premultiply, alpha
  def makeLerp(c1: Int, c2: Int, weight: Float): Int = {
    val r = (getR(c1) * weight + getR(c2) * (1 - weight)).toInt
    val g = (getG(c1) * weight + getG(c2) * (1 - weight)).toInt
    val b = (getB(c1) * weight + getB(c2) * (1 - weight)).toInt
    makeRGB(r, g, b)
  }

  def makeARGB(a: Int, r: Int, g: Int, b: Int): Int = {
    assert(0 <= a && a <= 255, "Alpha is out of 0..255 range: " + a)
    assert(0 <= r && r <= 255, "Red is out of 0..255 range: " + r)
    assert(0 <= g && g <= 255, "Green is out of 0..255 range: " + g)
    assert(0 <= b && b <= 255, "Blue is out of 0..255 range: " + b)
    ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF)
  }

  def makeRGB(r: Int, g: Int, b: Int): Int = makeARGB(255, r, g, b)

  def getA(color: Int): Int = (color >> 24) & 0xFF

  def getR(color: Int): Int = (color >> 16) & 0xFF

  def getG(color: Int): Int = (color >> 8) & 0xFF

  def getB(color: Int): Int = color & 0xFF

  def withA(color: Int, a: Int): Int = {
    assert(0 <= a && a <= 255, "Alpha is out of 0..255 range: " + a)
    ((a & 0xFF) << 24) | (color & 0x00FFFFFF)
  }

  def withR(color: Int, r: Int): Int = {
    assert(0 <= r && r <= 255, "Red is out of 0..255 range: " + r)
    ((r & 0xFF) << 16) | (color & 0xFF00FFFF)
  }

  def withG(color: Int, g: Int): Int = {
    assert(0 <= g && g <= 255, "Green is out of 0..255 range: " + g)
    ((g & 0xFF) << 8) | (color & 0xFFFF00FF)
  }

  def withB(color: Int, b: Int): Int = {
    assert(0 <= b && b <= 255, "Blue is out of 0..255 range: " + b)
    (b & 0xFF) | (color & 0xFFFFFF00)
  }
}