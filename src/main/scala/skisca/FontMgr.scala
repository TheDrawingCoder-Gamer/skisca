package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*
import scala.util.boundary

object FontMgr {
  object _DefaultHolder {
    val INSTANCE = new FontMgr(_nDefault, false)
    try Stats.onNativeCall()
  }

  /**
   * Return the default fontmgr.
   */
  def getDefault: FontMgr = {
    _DefaultHolder.INSTANCE
  }

  @native def _nGetFamiliesCount(ptr: Long): Int

  @native def _nGetFamilyName(ptr: Long, index: Int): String

  @native def _nMakeStyleSet(ptr: Long, index: Int): Long

  @native def _nMatchFamily(ptr: Long, familyName: String): Long

  @native def _nMatchFamilyStyle(ptr: Long, familyName: String, fontStyle: Int): Long

  @native def _nMatchFamilyStyleCharacter(ptr: Long, familyName: String, fontStyle: Int, bcp47: Array[String], character: Int): Long

  @native def _nMakeFromData(ptr: Long, dataPtr: Long, ttcIndex: Int): Long

  @native def _nDefault: Long

  try Library.staticLoad()
}

class FontMgr(ptr: Long, allowClose: Boolean = true) extends RefCnt(ptr, allowClose) {
  def getFamiliesCount: Int = {
    try {
      Stats.onNativeCall()
      FontMgr._nGetFamiliesCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getFamilyName(index: Int): String = {
    try {
      Stats.onNativeCall()
      FontMgr._nGetFamilyName(_ptr, index)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def makeStyleSet(index: Int): FontStyleSet = {
    try {
      Stats.onNativeCall()
      val ptr = FontMgr._nMakeStyleSet(_ptr, index)
      if (ptr == 0) {
        null
      } else {
        new FontStyleSet(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * The caller must call {@link # close ( )} on the returned object.
   * Never returns null; will return an empty set if the name is not found.
   *
   * Passing null as the parameter will return the default system family.
   * Note that most systems don't have a default system family, so passing null will often
   * result in the empty set.
   *
   * It is possible that this will return a style set not accessible from
   * {@link # makeStyleSet ( int )} due to hidden or auto-activated fonts.
   */
  def matchFamily(familyName: String): FontStyleSet = {
    try {
      Stats.onNativeCall()
      new FontStyleSet(FontMgr._nMatchFamily(_ptr, familyName))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Find the closest matching typeface to the specified familyName and style
   * and return a ref to it. The caller must call {@link # close ( )} on the returned
   * object. Will return null if no 'good' match is found.
   *
   * Passing null as the parameter for `familyName` will return the
   * default system font.
   *
   * It is possible that this will return a style set not accessible from
   * {@link # makeStyleSet ( int )} or {@link # matchFamily ( String )} due to hidden or
   * auto-activated fonts.
   */
  @Nullable def matchFamilyStyle(familyName: String, style: FontStyle): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontMgr._nMatchFamilyStyle(_ptr, familyName, style.value)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @Nullable def matchFamiliesStyle(families: Array[String], style: FontStyle): Typeface = {
    boundary[Typeface] {
      for (family <- families) {
        val typeface = matchFamilyStyle(family, style)
        if (typeface != null) boundary.break(typeface)
      }
      null
    }
    null
  }

  /**
   * Use the system fallback to find a typeface for the given character.
   * Note that bcp47 is a combination of ISO 639, 15924, and 3166-1 codes,
   * so it is fine to just pass a ISO 639 here.
   *
   * Will return null if no family can be found for the character
   * in the system fallback.
   *
   * Passing `null` as the parameter for `familyName` will return the
   * default system font.
   *
   * bcp47[0] is the least significant fallback, bcp47[bcp47.length-1] is the
   * most significant. If no specified bcp47 codes match, any font with the
   * requested character will be matched.
   */
  def matchFamilyStyleCharacter(@Nullable familyName: String, style: FontStyle, @Nullable bcp47: Array[String], character: Int): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontMgr._nMatchFamilyStyleCharacter(_ptr, familyName, style.value, bcp47, character)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @Nullable def matchFamiliesStyleCharacter(families: Array[String], style: FontStyle, @Nullable bcp47: Array[String], character: Int): Typeface = {
    boundary[Typeface] {
      for (family <- families) {
        val typeface = matchFamilyStyleCharacter(family, style, bcp47, character)
        if (typeface != null) boundary.break(typeface)
      }
      null
    }
  }

  /**
   * Create a typeface for the specified data and TTC index (pass 0 for none)
   * or null if the data is not recognized. The caller must call {@link # close ( )} on
   * the returned object if it is not null.
   */
  def makeFromData(data: Data): Typeface = {
    makeFromData(data, 0)
  }

  def makeFromData(data: Data, ttcIndex: Int): Typeface = {
    try {
      Stats.onNativeCall()
      val ptr = FontMgr._nMakeFromData(_ptr, Native.getPtr(data), ttcIndex)
      if (ptr == 0) {
        null
      } else {
        new Typeface(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(data)
    }
  }
}