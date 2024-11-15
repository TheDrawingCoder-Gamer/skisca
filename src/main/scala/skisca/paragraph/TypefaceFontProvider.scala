package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*

object TypefaceFontProvider {
  @native def _nMake: Long

  @native def _nRegisterTypeface(ptr: Long, typefacePtr: Long, alias: String): Long

  try Library.staticLoad()
}

class TypefaceFontProvider extends FontMgr(TypefaceFontProvider._nMake, true) {
  Stats.onNativeCall()

  def registerTypeface(typeface: Typeface): TypefaceFontProvider = registerTypeface(typeface, null)

  def registerTypeface(typeface: Typeface, alias: String): TypefaceFontProvider = {
    try {
      Stats.onNativeCall()
      TypefaceFontProvider._nRegisterTypeface(_ptr, Native.getPtr(typeface), alias)
      this
    } finally {
      ReferenceUtil.reachabilityFence(typeface)
    }
  }
}