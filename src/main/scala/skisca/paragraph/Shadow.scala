package gay.menkissing.skisca.paragraph

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

case class Shadow(color: Int, offsetX: Float, offsetY: Float, blurSigma: Double) {


  def offset = new Point(offsetX, offsetY)
}