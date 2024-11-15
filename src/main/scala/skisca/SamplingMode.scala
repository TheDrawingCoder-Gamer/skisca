package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * @see gay.menkissing.skisca.FilterMipmap
 * @see gay.menkissing.skisca.CubicResampler
 */
object SamplingMode {
  val DEFAULT = new FilterMipmap(FilterMode.NEAREST, MipmapMode.NONE)
  val LINEAR = new FilterMipmap(FilterMode.LINEAR, MipmapMode.NONE)
  val MITCHELL = new CubicResampler(0.33333334f, 0.33333334f)
  val CATMULL_ROM = new CubicResampler(0, 0.5f)
}

trait SamplingMode { 
  // 10 + 30-bit float + 32-bit float: CubicResampler
  // 01 + 30-bit zeros + 32-bit int:   SamplingModeAnisotropic
  // 00 + 30-bit int   + 32-bit int:   FilterMipmap
  def packed: Long
}