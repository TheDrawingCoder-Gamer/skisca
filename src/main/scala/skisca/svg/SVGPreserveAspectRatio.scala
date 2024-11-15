package gay.menkissing.skisca.svg

import org.jetbrains.annotations.*

case class SVGPreserveAspectRatio(align: SVGPreserveAspectRatioAlign = SVGPreserveAspectRatioAlign.XMID_YMID,
                                  scale: SVGPreserveAspectRatioScale = SVGPreserveAspectRatioScale.MEET) {
  

  def this(align: Int, scale: Int) = {
    this(SVGPreserveAspectRatioAlign.valueOf(align), SVGPreserveAspectRatioScale.values.apply(scale))
  }
}