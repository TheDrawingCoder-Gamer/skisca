package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.shaper.*

class FontRenderingScene extends Scene {
  val _inter: Typeface = Typeface.makeFromFile(Scene.file("fonts/Inter-Regular.otf"))
  var _interHinted: Typeface = Scene.inter
  val _interV: Typeface = Typeface.makeFromFile(Scene.file("fonts/Inter-V.ttf"))
  var _paint: Paint = Paint().setColor(0xFF000000)
  var _dpi = 0
  _variants = Array[String]("Identity", "Scaled")


  def _drawLine(canvas: Canvas, text: String, font: Font): Float = {
    val blob = Shaper.make.shape(text, font)
    if (blob != null) {
      val bounds = blob.getBounds
      canvas.drawTextBlob(blob, 0, 0, _paint)
      canvas.translate(0, bounds.getHeight)
      return bounds.getHeight
    }
    0
  }

  override def draw(canvas: Canvas, windowWidth: Int, windowHeight: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val scale = if ("Identity" == variantTitle) {
      dpi
    } else {
      1f
    }
    canvas.translate(30 * scale, 30 * scale)
    drawModes(canvas, windowWidth, scale)
  }

  override def scale: Boolean = "Scaled" == variantTitle

  def drawModes(canvas: Canvas, windowWidth: Int, scale: Float): Unit = {
    val common = "iiijjjlllppp "
    for (typeface <- Pair.arrayOf("", _inter, "Hinted ", _interHinted, "Variable ", _interV)) {
      canvas.save
      for (edging <- Pair.arrayOf("Grayscale ", FontEdging.ANTI_ALIAS, "LCD ", FontEdging.SUBPIXEL_ANTI_ALIAS)) {
        for (subpixel <- Pair.arrayOf("", false, "Subpixel ", true)) {
          for (linear <- Pair.arrayOf("", false, "LinearMetrics ", true)) {
            for (hinting <- Pair.arrayOf("Hinting=NONE ", FontHinting.NONE, "Hinting=SLIGHT ", FontHinting
              .SLIGHT, "Hinting=NORMAL ", FontHinting.NORMAL, "Hinting=FULL ", FontHinting.FULL)) {
              try {
                val font = new Font(typeface.getSecond, 11 * scale)
                try {
                  font.setEdging(edging.getSecond)
                  font.setSubpixel(subpixel.getSecond)
                  font.setMetricsLinear(linear.getSecond)
                  if (hinting.getSecond != null) font.setHinting(hinting.getSecond)
                  _drawLine(canvas, common + "Inter " + (11 * scale) + " " + edging.getFirst + typeface
                    .getFirst + subpixel.getFirst + linear.getFirst + hinting.getFirst, font)
                } finally {
                  if (font != null) font.close()
                }
              }
            }
            canvas.translate(0, 20)
          }
        }
      }
      canvas.restore
      canvas.translate(((windowWidth - 80) / 3 + 20) * scale, 0)
    }
  }
}