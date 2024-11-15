package gay.menkissing.skisca

import org.jetbrains.annotations.*

object EncodePNGOptions {
  val DEFAULT = new EncodePNGOptions(EncodePNGFilterFlag.ALL._value, 6)
}

class EncodePNGOptions @ApiStatus.Internal(@ApiStatus.Internal val _flags: Int, @ApiStatus.Internal val _zlibLevel: Int) {
  def withFlags(flags: EncodePNGFilterFlag*): EncodePNGOptions = {
    var _flags = 0
    for (flag <- flags) {
      _flags = _flags | flag._value
    }
    new EncodePNGOptions(_flags, _zlibLevel)
  }

  def withZlibLevel(level: Int) = new EncodePNGOptions(_flags, level)
}