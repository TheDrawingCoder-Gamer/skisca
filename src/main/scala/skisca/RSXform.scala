package gay.menkissing.skisca

import org.jetbrains.annotations.*

/**
 * <p>A compressed form of a rotation+scale matrix.</p>
 *
 * <pre>[ fSCos     -fSSin    fTx ]
 * [ fSSin      fSCos    fTy ]
 * [     0          0      1 ]</pre>
 */
object RSXform {
  /**
   * Initialize a new xform based on the scale, rotation (in radians), final tx,ty location
   * and anchor-point ax,ay within the src quad.
   *
   * Note: the anchor point is not normalized (e.g. 0...1) but is in pixels of the src image.
   */
    def makeFromRadians(scale: Float, radians: Float, tx: Float, ty: Float, ax: Float, ay: Float): RSXform = {
      val s = Math.sin(radians).toFloat * scale
      val c = Math.cos(radians).toFloat * scale
      new RSXform(c, s, tx + -c * ax + s * ay, ty + -s * ax - c * ay)
    }
}

case class RSXform(scos: Float, ssin: Float, tx: Float, ty: Float)