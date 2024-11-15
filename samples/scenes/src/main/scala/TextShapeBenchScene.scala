package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*
import scala.util.boundary

import java.util.*
import java.util.stream.*

class TextShapeBenchScene extends Scene {
  final var font: Font = null
  final var metrics: FontMetrics = null
  final var fc: FontCollection = null
  final val redStroke = Paint().setColor(0x80CC3333).setMode(PaintMode.STROKE).setStrokeWidth(1)
  _variants = Array[String]("Tabs Paragraph", "Tabs Paragraph No-Cache", "Tabs TextLine", "Emoji Paragraph", "Emoji Paragraph No-Cache", "Emoji TextLine", "Greek Paragraph", "Greek Paragraph No-Cache", "Greek TextLine", "Greek TextLine No-Approx", "Notdef Paragraph", "Notdef Paragraph No-Cache", "Notdef TextLine", "English Paragraph", "English Paragraph No-Cache", "English TextLine")
  _variantIdx = 9
  font = new Font(Scene.jbMono, fontSize).setSubpixel(true)
  metrics = font.getMetrics
  fc = new FontCollection
  fc.setDefaultFontManager(FontMgr.getDefault)
  val fm = new TypefaceFontProvider
  fm.registerTypeface(Scene.jbMono)
  fc.setAssetFontManager(fm)

  var fontSize = 20
  var padding: Float = fontSize * 2

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val variant = _variants(_variantIdx).split(" ")
    var text = variant(0)
    if ("Tabs" == variant(0)) {
      text = "										"
    } else if ("Emoji" == variant(0)) {
      text = "😀 😃 😄 😁 😆 😅 😂 ☺️ 😇 😍"
    } else if ("Greek" == variant(0)) {
      text = "ἔοικα γοῦν τούτου γε σμικρῷ τινι αὐτῷ τούτῳ σοφώτερος εἶναι, ὅτι ἃ μὴ οἶδα οὐδὲ οἴομαι εἰδέναι"
    } else if ("Notdef" == variant(0)) {
      text = "\u20C0\u20C0\u20C0\u20C0\u20C0\u20C0\u20C0\u20C0\u20C0\u20C0"
    } else if ("English" == variant(0)) text = "In girum imus nocte et consumimur igni"
    if ("Paragraph" == variant(1)) {
      if (variant.length > 2) fc.getParagraphCache.reset() // No-Cache
      var i = 1
      boundary {
        while (true) {
          val y = i * padding
          if (y > height - padding) boundary.break() 
          {
            val ts = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("JetBrains Mono"))
                                    .setFontSize(fontSize)
            val ps = new ParagraphStyle
            val pb = new ParagraphBuilder(ps, fc)
            try {
              pb.pushStyle(ts)
              pb.addText(i + " [" + text + "]")
              {
                val p = pb.build
                try {
                  p.layout(Float.PositiveInfinity)
                  p.paint(canvas, padding, y)
                } finally {
                  if (p != null) p.close()
                }
              }
            } finally {
              if (ts != null) ts.close()
              if (ps != null) ps.close()
              if (pb != null) pb.close()
            }
          }
          i += 1
        }
      }
    }
    else { // TextLine
      {
        val shaper = Shaper.makeShapeDontWrapOrReorder
        var i = 1
        try {
          boundary {
            while (true) {
              val y = i * padding
              if (y > height - padding) boundary.break()
              try {
                val line = if ( {
                  variant
                    .length > 2
                  // No-Approx
                }) {
                  shaper.shapeLine(i + " [" + text + "]", font, ShapingOptions.DEFAULT
                                                                              .copy(approximateSpaces = false, approximatePunctuation = false))
                }
                else {
                  shaper.shapeLine(i + " [" + text + "]", font)
                }
                try {
                  canvas.drawTextLine(line, padding, y - metrics.ascent, Scene.blackFill)
                  canvas.drawRect(Rect.makeXYWH(padding, y, line.getWidth, metrics.getHeight), redStroke)
                  for (x <- TextLine._nGetRunPositions(line._ptr)) {
                    canvas.drawLine(padding + x, y, padding + x, y + metrics.getHeight, redStroke)
                  }
                } finally {
                  if (line != null) line.close()
                }
              }
              i += 1
            }
          }
        }
        finally
        {
          if (shaper != null) shaper.close()
        }
      }
    }
  }
}