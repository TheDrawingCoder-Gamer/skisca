package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object ShadowUtils {
  /**
   * Draw an offset spot shadow and outlining ambient shadow for the given path using a disc
   * light. The shadow may be cached, depending on the path type and canvas matrix. If the
   * matrix is perspective or the path is volatile, it will not be cached.
   *
   * @param canvas              The canvas on which to draw the shadows.
   * @param path                The occluder used to generate the shadows.
   * @param zPlaneParams        Values for the plane function which returns the Z offset of the
   *                            occluder from the canvas based on local x and y values (the current matrix is not applied).
   * @param lightPos            The 3D position of the light relative to the canvas plane. This is
   *                            independent of the canvas's current matrix.
   * @param lightRadius         The radius of the disc light.
   * @param ambientColor        The color of the ambient shadow.
   * @param spotColor           The color of the spot shadow.
   * @param transparentOccluder The occluding object is not opaque. Knowing that the occluder is opaque allows
   *                            us to cull shadow geometry behind it and improve performance.
   * @param geometricOnly       Don't try to use analytic shadows.
   */
    def drawShadow(@NotNull canvas: Canvas, @NotNull path: Path, @NotNull zPlaneParams: Point3, @NotNull lightPos: Point3, lightRadius: Float, ambientColor: Int, spotColor: Int, transparentOccluder: Boolean, geometricOnly: Boolean): Unit = {
      Stats.onNativeCall()
      var flags = 0
      if (transparentOccluder) flags |= 1
      if (geometricOnly) flags |= 2
      _nDrawShadow(Native.getPtr(canvas), Native.getPtr(path), zPlaneParams._x, zPlaneParams._y, zPlaneParams
        ._z, lightPos._x, lightPos._y, lightPos._z, lightRadius, ambientColor, spotColor, flags)
    }

  /**
   * Helper routine to compute ambient color value for one-pass tonal alpha.
   *
   * @param ambientColor Original ambient color
   * @param spotColor    Original spot color
   * @return Modified ambient color
   */
  def computeTonalAmbientColor(ambientColor: Int, spotColor: Int): Int = {
    Stats.onNativeCall()
    _nComputeTonalAmbientColor(ambientColor, spotColor)
  }

  /**
   * Helper routine to compute spot color value for one-pass tonal alpha.
   *
   * @param ambientColor Original ambient color
   * @param spotColor    Original spot color
   * @return Modified spot color
   */
  def computeTonalSpotColor(ambientColor: Int, spotColor: Int): Int = {
    Stats.onNativeCall()
    _nComputeTonalSpotColor(ambientColor, spotColor)
  }

  @ApiStatus.Internal
  @native def _nDrawShadow(canvasPtr: Long, pathPtr: Long, zPlaneX: Float, zPlaneY: Float, zPlaneZ: Float, lightPosX: Float, lightPosY: Float, lightPosZ: Float, lightRadius: Float, ambientColor: Int, spotColor: Int, flags: Int): Unit

  @ApiStatus.Internal
  @native def _nComputeTonalAmbientColor(ambientColor: Int, spotColor: Int): Int

  @ApiStatus.Internal
  @native def _nComputeTonalSpotColor(ambientColor: Int, spotColor: Int): Int

  Library.staticLoad()
}