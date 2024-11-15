package gay.menkissing.skisca

object FourByteTag {
  def fromString(name: String): Int = {
    assert(name.length == 4, "Name must be exactly 4 symbols, got: '" + name + "'")
    (name.charAt(0) & 0xFF) << 24 | (name.charAt(1) & 0xFF) << 16 | (name.charAt(2) & 0xFF) << 8 | (name
      .charAt(3) & 0xFF)
  }

  def toString(tag: Int) = {
    new String(Array[Byte]((tag >> 24 & 0xFF).toByte, (tag >> 16 & 0xFF)
      .toByte, (tag >> 8 & 0xFF).toByte, (tag & 0xFF).toByte))
  }
}