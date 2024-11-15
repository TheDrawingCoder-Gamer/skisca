package gay.menkissing.skisca

object EncodeWEBPOptions {
  val DEFAULT = new EncodeWEBPOptions(EncodeWEBPCompressionMode.LOSSY, 100f)
}

case class EncodeWEBPOptions(compressionMode: EncodeWEBPCompressionMode, quality: Float)