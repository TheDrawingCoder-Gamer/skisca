package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

class ParagraphMetricsScene extends Scene {
  var fc = new FontCollection
  var lastUpdate = 0
  var inter13: Font = new Font(Scene.inter, 13)
  var detailsFill: Paint = Paint().setColor(0xFFCC3333)
  var boundariesStroke: Paint = Paint().setColor(0xFFFAA6B2).setMode(PaintMode.STROKE).setStrokeWidth(1f)
  fc.setDefaultFontManager(FontMgr.getDefault)
  val fm = new TypefaceFontProvider
  fm.registerTypeface(Scene.jbMono)
  fm.registerTypeface(Scene.inter, "Interface")
  fc.setAssetFontManager(fm)


  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.save
    canvas.translate(30, 30)
    drawSelection(canvas, xpos - 30f, ypos - 30f)
    canvas.restore
    canvas.save
    canvas.translate(30, height / 2 + 15)
    drawIndices(canvas)
    canvas.restore
    canvas.save
    canvas.translate(width / 2 + 15, 30)
    drawPositions(canvas)
    canvas.restore
  }

  def drawSelection(canvas: Canvas, dx: Float, dy: Float): Unit = {
    {
      val defaultTs = new TextStyle().setFontSize(24).setColor(0xFF000000).setHeight(Some(1.2f))
      val largeTs = new TextStyle().setFontSize(36).setColor(0xFF000000)
      val smallTs = new TextStyle().setFontSize(12).setColor(0xFF000000)
      val ligaTs = new TextStyle().setFontSize(24).setColor(0xFF000000).setFontFamilies(Array[String]("Interface"))
      val zapfinoTs = new TextStyle().setFontSize(24).setColor(0xFF000000).setFontFamilies(Array[String]("Zapfino"))
      val ps = new ParagraphStyle
      val pb = new ParagraphBuilder(ps, fc)
      try {
        // default style
        pb.pushStyle(defaultTs)
        pb.addText("123 567 ")
        pb.pushStyle(ligaTs)
        pb.addText("<-> ")
        pb.popStyle
        pb.addText("👩 👩👩 🧑🏿 🧑🏿🧑🏿 👮‍♀️ 👮‍♀️👮‍♀️ 👩‍👩‍👧‍👧 👩‍👩‍👧‍👧👩‍👩‍👧‍👧\n")
        pb.addText("The following ")
        pb.pushStyle(largeTs)
        pb.addText("sentence")
        pb.popStyle
        pb.addText(" is true\n")
        pb.pushStyle(largeTs)
        pb.addText("The previous       ")
        pb.popStyle
        pb.addText("sentence")
        pb.pushStyle(largeTs)
        pb.addText(" is false\n")
        pb.popStyle
        pb.pushStyle(defaultTs)
        pb.addText("— Vicious circularity, \n")
        pb.pushStyle(smallTs)
        pb.addText("  or infinite regress\n")
        pb.popStyle
        pb.addText("hello мир дружба <<<")
        pb.addPlaceholder(new PlaceholderStyle(50, 2f, PlaceholderAlignment.BASELINE, BaselineMode.ALPHABETIC, 0f))
        pb.addText(">>> fi fl 👃 one two ثلاثة 12 👂 34 خمسة\n")
        pb.addText("x̆x̞̊x̃ c̝̣̱̲͈̝ͨ͐̈ͪͨ̃ͥͅh̙̬̿̂a̯͎͍̜͐͌͂̚o̬s͉̰͊̀ ")
        pb.pushStyle(zapfinoTs)
        pb.addText("fiz officiad\n")
        pb.popStyle
        try {
          val p = pb.build
          try {
            p.layout(600f)
            // getLineMetrics
            for (lm <- p.getLineMetrics) {
              canvas.drawRect(Rect
                .makeXYWH(lm.left.toFloat, (lm.baseline - lm.ascent).toFloat, lm
                  .width.toFloat, (lm.ascent + lm.descent).toFloat), boundariesStroke)
              canvas
                .drawLine(lm.left.toFloat, lm.baseline.toFloat, (lm.left + lm.width)
                  .toFloat, lm.baseline.toFloat, boundariesStroke)
            }
            // getGlyphPositionAtCoordinate
            val glyphx = p.getGlyphPositionAtCoordinate(dx, dy).position
            {
              val blue = Paint().setColor(0x80b3d7ff)
              val orange = Paint().setColor(0x80ffd7b3)
              try {
                // getRectsForRange
                for (box <- p.getRectsForRange(0, glyphx, RectHeightMode.TIGHT, RectWidthMode.TIGHT)) {
                  canvas.drawRect(box.rect, blue)
                }
                // getWordBoundary
                val word = p.getWordBoundary(glyphx)
                for (box <- p.getRectsForRange(word.getStart, word.getEnd, RectHeightMode.TIGHT, RectWidthMode.TIGHT)) {
                  canvas.drawRect(box.rect, orange)
                }
              } finally {
                if (blue != null) blue.close()
                if (orange != null) orange.close()
              }
            }
            p.paint(canvas, 0, 0)
            canvas.drawString("idx: " + glyphx, 630, 20, inter13, detailsFill)
          } finally {
            if (p != null) p.close()
          }
        }
      } finally {
        if (defaultTs != null) defaultTs.close()
        if (largeTs != null) largeTs.close()
        if (smallTs != null) smallTs.close()
        if (ligaTs != null) ligaTs.close()
        if (zapfinoTs != null) zapfinoTs.close()
        if (ps != null) ps.close()
        if (pb != null) pb.close()
      }
    }
  }

  def drawIndices(canvas: Canvas): Unit = {
    try {
      val defaultTs = new TextStyle().setFontSize(16).setColor(0xFF000000).setFontFamilies(Array[String]("Interface"))
                                     .setHeight(Some(1.5f))
      val ps = new ParagraphStyle
      val pb = new ParagraphBuilder(ps, fc)
      try {
        pb.pushStyle(defaultTs)
        val text = "12345\n" + "абвгд\n" + "𝕨𝕨𝕨𝕨𝕨\n" + "space     \n" + (if (System.getProperty("os.name").toLowerCase
                                                                                   .contains("linux")) {
          ""
        } else {
          "\\r\\n\r\n"
        }) + "\\n\\n\n\n" + "👩👩👩👩👩\n" + "<-><->\n" + "🧑🏿🧑🏿🧑🏿🧑🏿🧑🏿\n" + "👮‍♀️👮‍♀️👮‍♀️👮‍♀️👮‍♀️\n" + "👩‍👩‍👧‍👧👩‍👩‍👧‍👧👩‍👩‍👧‍👧👩‍👩‍👧‍👧👩‍👩‍👧‍👧"
        pb.addText(text)
        {
          val paragraph = pb.build
          try {
            paragraph.layout(600f)
            paragraph.paint(canvas, 0, 0)
            for (lm <- paragraph.getLineMetrics) {
              canvas.drawRect(Rect
                .makeXYWH(lm.left.toFloat, (lm.baseline - lm.ascent).toFloat, lm
                  .width.toFloat, (lm.ascent + lm.descent).toFloat), boundariesStroke)
              canvas
                .drawLine(lm.left.toFloat, lm.baseline.toFloat, (lm.left + lm.width)
                  .toFloat, lm.baseline.toFloat, boundariesStroke)
              canvas.drawString(String
                .format("start=%d, end w/o space=%d, end=%d, end w/ newline=%d", lm.startIndex, lm
                  .endExclusingWhitespaces, lm.endIndex, lm.endIncludingNewline), 150, lm.baseline
                                                                                                  .toFloat, inter13, detailsFill)
            }
          } finally {
            if (paragraph != null) paragraph.close()
          }
        }
      } finally {
        if (defaultTs != null) defaultTs.close()
        if (ps != null) ps.close()
        if (pb != null) pb.close()
      }
    }
  }

  def drawPositions(canvas: Canvas): Unit = {
    for (text <- Array[String]("ggg", "жжж", "𝕨𝕨𝕨𝕨𝕨", "𨭎", "𨭎𨭎", "ggg𨭎𨭎𨭎", "👩", "ggg👩👩👩", "👨👩👧👦", "👩‍👩‍👧‍👧", "👩‍👩‍👧‍👧👩‍👩‍👧‍👧👩‍👩‍👧‍👧", "sixستةten", "x̆x̞̊x̃", "<->")) {
      try {
        val defaultTs = new TextStyle().setFontSize(16).setColor(0xFF000000).setFontFamilies(Array[String]("Interface"))
                                       .setHeight(Some(1.5f))
        val ps = new ParagraphStyle
        val pb = new ParagraphBuilder(ps, fc)
        try {
          pb.pushStyle(defaultTs)
          pb.addText(text)
          {
            val paragraph = pb.build
            try {
              paragraph.layout(600f)
              paragraph.paint(canvas, 0, 0)
              val lm = paragraph.getLineMetrics(0)
              var s = ""
              var x = 0
              while (x < lm.width + 10) {
                s = s + paragraph.getGlyphPositionAtCoordinate(x, lm.baseline.toFloat).position + " "
                x += 1
              }
              canvas.drawString(s, 100, lm.baseline.toFloat, inter13, detailsFill)
              canvas.translate(0, paragraph.getHeight)
            } finally {
              if (paragraph != null) paragraph.close()
            }
          }
        } finally {
          if (defaultTs != null) defaultTs.close()
          if (ps != null) ps.close()
          if (pb != null) pb.close()
        }
      }
    }
  }
}