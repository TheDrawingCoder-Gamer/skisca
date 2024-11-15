package gay.menkissing.skisca

import org.jetbrains.annotations.ApiStatus

object GradientStyle {
  @ApiStatus.Internal val _INTERPOLATE_PREMUL = 1
  var DEFAULT = new GradientStyle(FilterTileMode.CLAMP, true, null)
}

case class GradientStyle(tileMode: FilterTileMode, premul: Boolean, localMatrix: Matrix33) {
  @ApiStatus.Internal def _getFlags: Int = {
    0 | (if (premul) {
      GradientStyle._INTERPOLATE_PREMUL
    } else {
      0
    })
  }

  @ApiStatus.Internal def _getMatrixArray: Array[Float] = {
    if (localMatrix == null) {
      null
    } else {
      localMatrix.mat
    }
  }
}