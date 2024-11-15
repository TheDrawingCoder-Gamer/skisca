package gay.menkissing.skisca.svg

import gay.menkissing.skisca.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

case class SVGLengthContext(width: Float, height: Float, dpi: Float = 90) {
  def this(@NotNull size: Point) = {
    this(size._x, size._y)
  }

  def resolve(@NotNull length: SVGLength, @NotNull `type`: SVGLengthType): Float = length.unit match {
    case SVGLengthUnit.NUMBER => {
      length.value
    }
    case SVGLengthUnit.PX => {
      length.value
    }
    case SVGLengthUnit.PERCENTAGE => {
      `type` match {
        case SVGLengthType.HORIZONTAL => {
          length.value * width / 100f
        }
        case SVGLengthType.VERTICAL => {
          length.value * height / 100f
        }
        case SVGLengthType.OTHER =>
          // https://www.w3.org/TR/SVG11/coords.html#Units_viewport_percentage {
          (length.value * Math.hypot(width, height) / Math.sqrt(2.0) / 100.0).toFloat
        }
      }
    case SVGLengthUnit.CM =>
      length.value * dpi / 2.54f
    case SVGLengthUnit.MM =>
      length.value * dpi / 25.4f
    case SVGLengthUnit.IN =>
      length.value * dpi
    case SVGLengthUnit.PT =>
      length.value * dpi / 72.272f
    case SVGLengthUnit.PC =>
      length.value * dpi * 12f / 72.272f
    case _ =>
      throw new IllegalArgumentException("Unknown SVGLengthUnit: " + length.unit)
  }

  @NotNull def resolveRect(@NotNull x: SVGLength, @NotNull y: SVGLength, @NotNull width: SVGLength, @NotNull height: SVGLength): Rect = Rect
    .makeXYWH(resolve(x, SVGLengthType.HORIZONTAL), resolve(y, SVGLengthType.VERTICAL), resolve(width, SVGLengthType
      .HORIZONTAL), resolve(height, SVGLengthType.VERTICAL))
}