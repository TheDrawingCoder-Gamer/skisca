package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * <p>Specify B and C (each between 0...1) to create a shader that applies the corresponding
 * cubic reconstruction filter to the image.</p>
 *
 * <p>Example values:</p>
 * <dl>
 * <dt>B = 1/3, C = 1/3</dt><dd>"Mitchell" filter</dd>
 * <dt>B = 0,   C = 1/2</dt><dd>"Catmull-Rom" filter</dd>
 * </dl>
 *
 * <p>See</p>
 * <ul>
 * <li>"Reconstruction Filters in Computer Graphics" Don P. Mitchell, Arun N. Netravali, 1988
 * <a href="https://www.cs.utexas.edu/~fussell/courses/cs384g-fall2013/lectures/mitchell/Mitchell.pdf">https://www.cs.utexas.edu/~fussell/courses/cs384g-fall2013/lectures/mitchell/Mitchell.pdf</a></li>
 * <li>Desmos worksheet <a href="https://www.desmos.com/calculator/aghdpicrvr">https://www.desmos.com/calculator/aghdpicrvr</a></li>
 * <li>Nice overview <a href="https://entropymine.com/imageworsener/bicubic/">https://entropymine.com/imageworsener/bicubic/</a></li>
 * </ul>
 */
case class CubicResampler(B: Float, C: Float) extends SamplingMode {


  override def packed: Long = {
    0x8000000000000000L | (0x3FFFFFFFFFFFFFFFL & ((java.lang.Float.floatToIntBits(B)
                                                        .toLong << 32) | java.lang.Float
      .floatToIntBits(C).toLong))
  }
}