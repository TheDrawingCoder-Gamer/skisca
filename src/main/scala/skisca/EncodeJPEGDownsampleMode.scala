package gay.menkissing.skisca

enum EncodeJPEGDownsampleMode {
  case

  /**
   * Reduction by a factor of two in both the horizontal and vertical directions.
   */
  DS_420,

  /**
   * Reduction by a factor of two in the horizontal direction.
   */
  DS_422,

  /**
   * No downsampling.
   */
  DS_444
}