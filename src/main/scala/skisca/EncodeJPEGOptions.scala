package gay.menkissing.skisca

import org.jetbrains.annotations.*


object EncodeJPEGOptions {
  val DEFAULT = new EncodeJPEGOptions(100, EncodeJPEGDownsampleMode.DS_420, EncodeJPEGAlphaMode.IGNORE)
}

case class EncodeJPEGOptions(quality: Int, downsampleMode: EncodeJPEGDownsampleMode, alphaMode: EncodeJPEGAlphaMode)