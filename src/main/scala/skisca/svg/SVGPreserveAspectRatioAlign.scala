package gay.menkissing.skisca.svg

import org.jetbrains.annotations.*

enum SVGPreserveAspectRatioAlign(val value: Int) extends Enum[SVGPreserveAspectRatioAlign] {
  // These values are chosen such that bits [0,1] encode X alignment, and
  // bits [2,3] encode Y alignment.
  case XMIN_YMIN extends SVGPreserveAspectRatioAlign(0x00)
  case XMID_YMIN extends SVGPreserveAspectRatioAlign(0x01)
  case XMAX_YMIN extends SVGPreserveAspectRatioAlign(0x02)
  case XMIN_YMID extends SVGPreserveAspectRatioAlign(0x04)
  case XMID_YMID extends SVGPreserveAspectRatioAlign(0x05)
  case XMAX_YMID extends SVGPreserveAspectRatioAlign(0x06)
  case XMIN_YMAX extends SVGPreserveAspectRatioAlign(0x08)
  case XMID_YMAX extends SVGPreserveAspectRatioAlign(0x09)
  case XMAX_YMAX extends SVGPreserveAspectRatioAlign(0x0a)
  case NONE extends SVGPreserveAspectRatioAlign(0x10)


  
}

object SVGPreserveAspectRatioAlign {
  @ApiStatus.Internal def valueOf(value: Int): SVGPreserveAspectRatioAlign = {
    value match {
      case 0x00 => {
        XMIN_YMIN
      }
      case 0x01 => {
        XMID_YMIN
      }
      case 0x02 => {
        XMAX_YMIN
      }
      case 0x04 => {
        XMIN_YMID
      }
      case 0x05 => {
        XMID_YMID
      }
      case 0x06 => {
        XMAX_YMID
      }
      case 0x08 => {
        XMIN_YMAX
      }
      case 0x09 => {
        XMID_YMAX
      }
      case 0x0a => {
        XMAX_YMAX
      }
      case 0x10 => {
        NONE
      }
      case _ => {
        throw new IllegalArgumentException("Unknown SVGPreserveAspectRatioAlign value: " + value)
      }
    }
  }
}