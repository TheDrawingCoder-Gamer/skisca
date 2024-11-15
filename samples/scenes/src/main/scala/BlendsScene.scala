package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class BlendsScene extends Scene {
  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(20, 20)
    canvas.save
    try {
      val dst = Paint().setColor(0xFFD62828)
      val src = Paint().setColor(0xFF01D6A0)
      try {
        for (blendMode <- BlendMode.values) {
          canvas.drawRect(Rect.makeXYWH(0, 0, 20, 20), dst)
          src.setBlendMode(blendMode)
          canvas.drawRect(Rect.makeXYWH(10, 10, 20, 20), src)
          canvas.translate(40, 0)
        }
      } finally {
        if (dst != null) dst.close()
        if (src != null) src.close()
      }
    }
    canvas.restore
    canvas.translate(0, 40)
  }
}