package gay.menkissing
package skisca.skottie

enum RenderFlag(val flag: Int) {
  case SKIP_TOP_LEVEL_ISOLATION extends RenderFlag(0x01)
  case DISABLE_TOP_LEVEL_CLIPPING extends RenderFlag(0x02)
}