package gay.menkissing.skisca

import org.jetbrains.annotations.*

case class SurfaceProps(deviceIndependentFonts: Boolean = false, pixelGeometry: PixelGeometry = PixelGeometry.UNKNOWN) {


  def this(deviceIndependentFonts: Boolean, pixelGeometry: Int) = {
    this(deviceIndependentFonts, PixelGeometry.values.apply(pixelGeometry))
  }


  @ApiStatus.Internal def _getFlags: Int = {
    0 | (if (deviceIndependentFonts) {
      1
    } else {
      0
    })
  }

  @ApiStatus.Internal def _getPixelGeometryOrdinal: Int = pixelGeometry.ordinal
}