package gay.menkissing.skisca.svg

import org.jetbrains.annotations.*

case class SVGLength(value: Float, unit: SVGLengthUnit) {
  def this(value: Float, unit: Int) = {
    this(value, SVGLengthUnit.values.apply(unit))
  }

  def this(value: Float) = {
    this(value, SVGLengthUnit.NUMBER)
  }
}