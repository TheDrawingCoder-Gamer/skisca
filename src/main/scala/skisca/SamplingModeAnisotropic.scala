package gay.menkissing.skisca

import org.jetbrains.annotations.*

case class SamplingModeAnisotropic(maxAniso: Int) extends SamplingMode {
  assert(maxAniso >= 1, "Expected maxAniso = " + maxAniso + " >= 1")

  override def packed: Long = 0x4000000000000000L | (0x3FFFFFFFFFFFFFFFL & maxAniso)
}