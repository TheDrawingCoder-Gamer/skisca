package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*

import java.util

class ParagraphStyleScene extends Scene {
  var fc = new FontCollection
  fc.setDefaultFontManager(FontMgr.getDefault)
  var slabo: Typeface = Typeface.makeFromFile(Scene.file("fonts/Slabo13px-Regular.ttf"))
  


  def drawLine(canvas: Canvas, text: String, ps: ParagraphStyle, width: Float): Unit = {
    {
      val pb = new ParagraphBuilder(ps, fc)
      try {
        pb.addText(text)
        try {
          val p = pb.build
          try {
            p.layout(Math.min(200, width))
            p.paint(canvas, 0, 0)
            canvas.translate(0, p.getHeight)
          } finally {
            if (p != null) p.close()
          }
        }
      } finally {
        if (pb != null) pb.close()
      }
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(30, 30)
    drawStyles(canvas, width)
    canvas.translate(0, 30)
    drawUpdates(canvas)
  }

  def drawStyles(canvas: Canvas, width: Int): Unit = {
    {
      val ts = new TextStyle().setColor(0xFF000000)
      try {
        {
          val ps = new ParagraphStyle().setTextStyle(ts)
          try {
            drawLine(canvas, "Full many a glorious morning have I seen", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        val fontFamilies = Array[String]("System Font", "Apple Color Emoji")
        {
          val ss = new StrutStyle().setFontFamilies(fontFamilies).setFontStyle(FontStyle.BOLD_ITALIC).setFontSize(20)
                                   .setHeight(2).setLeading(3).setEnabled(true).setHeightForced(true)
                                   .setHeightOverridden(true)
          val ps = new ParagraphStyle().setTextStyle(ts).setStrutStyle(ss)
          try {
            assert(ss == ps.getStrutStyle)
            assert(Array.equals(fontFamilies.asInstanceOf[Array[AnyRef]], ss.getFontFamilies.asInstanceOf[Array[AnyRef]]))
            assert(FontStyle.BOLD_ITALIC.equals(ss.getFontStyle))
            assert(20 == ss.getFontSize)
            assert(2 == ss.getHeight)
            assert(3 == ss.getLeading)
            assert(ss.isEnabled)
            assert(ss.isHeightForced)
            assert(ss.isHeightOverridden)
            drawLine(canvas, "Flatter the mountain tops with sovereign eye,", ps, width)
          } finally {
            if (ss != null) ss.close()
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setDirection(Direction.RTL)
          try {
            assert(Direction.RTL eq ps.getDirection)
            drawLine(canvas, "Kissing with golden face the meadows green,", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setAlignment(Alignment.CENTER)
          try {
            assert(Alignment.CENTER eq ps.getAlignment)
            assert(Alignment.CENTER eq ps.getEffectiveAlignment)
            drawLine(canvas, "Gilding pale streams with heavenly alchemy;", ps, width)
            assert(null == ps.getEllipsis)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setMaxLinesCount(2).setEllipsis(",,,")
          try {
            assert(2 == ps.getMaxLinesCount)
            assert(",,," == ps.getEllipsis, "Expected ',,,', got '" + ps.getEllipsis + "'")
            drawLine(canvas, "Anon permit the basest clouds to ride\nWith ugly rack on his celestial face,", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setHeight(40)
          try {
            assert(40 == ps.getHeight)
            drawLine(canvas, "And from the forlorn world his visage hide,", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setHeightMode(HeightMode.DISABLE_FIRST_ASCENT)
          try {
            assert(HeightMode.DISABLE_FIRST_ASCENT eq ps.getHeightMode)
            drawLine(canvas, "Stealing unseen to west with this disgrace:", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setHeightMode(HeightMode.DISABLE_LAST_DESCENT)
          try {
            assert(HeightMode.DISABLE_LAST_DESCENT eq ps.getHeightMode)
            drawLine(canvas, "Even so my sun one early morn did shine,", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).setHeightMode(HeightMode.DISABLE_ALL)
          try {
            assert(HeightMode.DISABLE_ALL eq ps.getHeightMode)
            drawLine(canvas, "With all triumphant splendour on my brow;", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
        try {
          val ps = new ParagraphStyle().setTextStyle(ts).disableHinting
          try {
            assert(!ps.isHintingEnabled)
            drawLine(canvas, "But out, alack, he was but one hour mine,", ps, width)
          } finally {
            if (ps != null) ps.close()
          }
        }
      } finally {
        if (ts != null) ts.close()
      }
    }
    // The region cloud hath mask’d him from me now.
    // Yet him for this my love no whit disdaineth;
    // Suns of the world may stain when heaven’s sun staineth.
  }

  def drawUpdates(canvas: Canvas): Unit = {
    try {
      val ts = new TextStyle().setColor(0xFF000000).setFontSize(16)
      val ps = new ParagraphStyle().setTextStyle(ts)
      val pb = new ParagraphBuilder(ps, fc)
      val red = Paint().setColor(0xFFCC3333)
      val redBg = Paint().setColor(0xFFFFEEEE)
      try {
        pb.addText("тут размер, тут цвет, а тут фон, вот")
        try {
          val p = pb.build
          try {
          } finally {
            if (p != null) p.close()
          }
        }
      } finally {
        if (ts != null) ts.close()
        if (ps != null) ps.close()
        if (pb != null) pb.close()
        if (red != null) red.close()
        if (redBg != null) redBg.close()
      }
    }
  }
}