package gay.menkissing
package skisca.skottie

enum AnimationBuilderFlag(val flag: Int) extends Enum[AnimationBuilderFlag] {
  /**
   * Normally, all static image frames are resolved at
   * load time via ImageAsset::getFrame(0).  With this flag,
   * frames are only resolved when needed, at seek() time.
   */
  case DEFER_IMAGE_LOADING extends AnimationBuilderFlag(0x01)

  /**
   * Attempt to use the embedded fonts (glyph paths,
   * normally used as fallback) over native Skia typefaces.
   */
  case PREFER_EMBEDDED_FONTS extends AnimationBuilderFlag(0x02)
}
