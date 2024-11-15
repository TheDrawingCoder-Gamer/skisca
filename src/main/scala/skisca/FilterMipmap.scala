package gay.menkissing.skisca

import org.jetbrains.annotations.*

case class FilterMipmap(filterMode: FilterMode, mipmapMode: MipmapMode = MipmapMode.NONE) extends SamplingMode {
  override def packed: Long = {
    0x3FFFFFFFFFFFFFFFL & ((filterMode.ordinal.toLong << 32) | mipmapMode
      .ordinal.toLong)
  }
}