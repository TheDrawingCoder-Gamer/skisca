package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

import java.nio.file.*

class WallOfTextScene extends Scene {
  private var font: Font = null
  private val words: Array[String] = Files.lines(java.nio.file.Path.of(Scene.file("texts/google-10000-english.txt"))).sorted.limit(1000).toArray(Array.ofDim)
  private var text: String = String.join(" ", words*)
  private val fill = Paint()
  private val boundsStroke = Paint().setColor(0x803333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)
  private val colors: Array[Int] = Array[Int](0xFF000000, 0xFFf94144, 0xFFf3722c, 0xFFf8961e, 0xFFf9844a, 0xFFf9c74f, 0xFF90be6d, 0xFF43aa8b, 0xFF4d908e, 0xFF577590, 0xFF277da1)
  private var fc: FontCollection = null
  
  _variants = Array[String]("TextLine by words", "ShapeThenWrap", "ShapeThenWrap by words", "JVM RunHandler", "JVM RunHandler by words", "ShaperDrivenWrapper", "Primitive", "CoreText", "ShapeDontWrapOrReorder", "Paragraph with cache", "Paragraph no cache")
  _variantIdx = 0

  private[scenes] def drawParagraph(canvas: Canvas, fontSize: Float, padding: Float, textWidth: Float): Unit = {
    if (fc == null) {
      fc = new FontCollection
      fc.setDefaultFontManager(FontMgr.getDefault)
      val fm = new TypefaceFontProvider
      fm.registerTypeface(Scene.inter, "Inter")
      fc.setAssetFontManager(fm)
    }
    if (_variants(_variantIdx).endsWith("no cache")) fc.getParagraphCache.reset()
    {
      val ts = new TextStyle().setFontFamilies(Array[String]("Inter")).setFontSize(fontSize)
                              .setColor(colors(_variantIdx))
      val ps = new ParagraphStyle
      val pb = new ParagraphBuilder(ps, fc)
      try {
        for (i <- 0 until words.length) {
          ts.setColor(colors(i % colors.length))
          pb.pushStyle(ts)
          pb.addText(words(i) + " ")
          pb.popStyle
        }
        {
          val p = pb.build
          try {
            p.layout(textWidth)
            p.paint(canvas, padding, padding)
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
  }

  private[scenes] def makeBlob(text: String, shaper: Shaper, textWidth: Float) = {
    if (_variants(_variantIdx)
      .startsWith("JVM RunHandler")) {
      {
        val handler = new DebugTextBlobHandler
        try {
          shaper.shape(text, font, ShapingOptions.DEFAULT, textWidth, handler)
          handler.makeBlob
        } finally {
          if (handler != null) handler.close()
        }
      }
    } else {
      shaper.shape(text, font, textWidth)
    }
  }

  private[scenes] def drawByWords(shaper: Shaper, canvas: Canvas, padding: Float, textWidth: Float): Unit = {
    var x = padding
    var y = padding
    val space = font.measureTextWidth(" ")
    val lineHeight = font.getMetrics.getHeight
    for (i <- 0 until words.length) {
      {
        val blob = makeBlob(words(i), shaper, Float.PositiveInfinity)
        try {
          val bounds = blob.getTightBounds
          val wordWidth = bounds.getWidth
          if (x + wordWidth > textWidth) {
            x = padding
            y += lineHeight
          }
          fill.setColor(colors(i % colors.length))
          canvas.drawRect(bounds.offset(x, y), boundsStroke)
          canvas.drawTextBlob(blob, x, y, fill)
          x += wordWidth + space
        } finally {
          if (blob != null) blob.close()
        }
      }
    }
  }

  def drawTextLinesByWords(canvas: Canvas, padding: Float, textWidth: Float): Unit = {
    var x = padding
    var y = padding
    val space = font.measureTextWidth(" ")
    val lineHeight = font.getMetrics.getHeight
    for (i <- 0 until words.length) {
      {
        val line = TextLine.make(words(i), font)
        try {
          val wordWidth = line.getWidth
          if (x + wordWidth > textWidth) {
            x = padding
            y += lineHeight
          }
          fill.setColor(colors(i % colors.length))
          canvas.drawRect(Rect.makeXYWH(x, y, line.getWidth, line.getHeight), boundsStroke)
          canvas.drawTextLine(line, x, y - line.getAscent, fill)
          x += wordWidth + space
        } finally {
          if (line != null) line.close()
        }
      }
    }
  }

  def drawTogether(shaper: Shaper, canvas: Canvas, padding: Float, textWidth: Float): Unit = {
    {
      val blob = makeBlob(text, shaper, textWidth)
      try {
        canvas.drawTextBlob(blob, padding, padding, fill)
      } finally {
        if (blob != null) blob.close()
      }
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val padding = 20f * dpi
    val textWidth = width * dpi - padding * 2
    val fontSize = 14.5f * dpi
    val variant = _variants(_variantIdx)
    if (font == null) font = new Font(Scene.inter, fontSize).setSubpixel(true)
    fill.setColor(colors(_variantIdx))
    if (variant.startsWith("Paragraph")) {
      drawParagraph(canvas, fontSize, padding, textWidth)
    } else if (variant.startsWith("TextLine")) {
      drawTextLinesByWords(canvas, padding, textWidth)
    } else {
      var shaper: Shaper = null
      if (variant.startsWith("Primitive")) {
        shaper = Shaper.makePrimitive
      } else if (variant.startsWith("ShaperDrivenWrapper")) {
        shaper = Shaper.makeShaperDrivenWrapper
      } else if (variant.startsWith("ShapeDontWrapOrReorder")) {
        shaper = Shaper.makeShapeDontWrapOrReorder
      } else if (variant.startsWith("CoreText")) {
        shaper = if ("Mac OS X" == System.getProperty("os.name")) {
          Shaper
            .makeCoreText
        } else {
          null
        }
      } else {
        shaper = Shaper.makeShapeThenWrap
      }
      if (shaper != null) {
        if (variant.endsWith("by words")) {
          drawByWords(shaper, canvas, padding, textWidth)
        } else {
          drawTogether(shaper, canvas, padding, textWidth)
        }
        shaper.close()
      }
    }
  }

  override def scale = false
}