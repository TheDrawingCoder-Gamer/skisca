package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import gay.menkissing.skisca.paragraph.*

import java.util

class TextStyleScene extends Scene {
  var fc = new FontCollection
  var slabo: Typeface = null
  fc.setDefaultFontManager(FontMgr.getDefault)
  slabo = Typeface.makeFromFile(Scene.file("fonts/Slabo13px-Regular.ttf"))


  def drawLine(canvas: Canvas, text: String, ts: TextStyle): Unit = {
    {
      val ps = new ParagraphStyle
      val pb = new ParagraphBuilder(ps, fc)
      try {
        pb.pushStyle(ts)
        pb.addText(text)
        {
          val p = pb.build
          try {
            p.layout(Float.PositiveInfinity)
            p.paint(canvas, 0, 0)
            canvas.translate(0, p.getHeight + 5)
          } finally {
            if (p != null) p.close()
          }
        }
      } finally {
        if (ps != null) ps.close()
        if (pb != null) pb.close()
      }
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(30, 30)
    {
      val ts = new TextStyle().setColor(0xFFcc3333)
      try {
        assert(0xFFcc3333 == ts.getColor)
        drawLine(canvas, "Shall I compare thee to a summer’s day?", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val sh = Shader.makeLinearGradient(0, 0, 0, 13, Array[Int](0xFF3A1C71, 0xFFD76D77, 0xFFFFAF7B))
      val p = Paint().setShader(sh)
      val ts = new TextStyle().setForeground(p)
      try {
        assert(p == ts.getForeground)
        drawLine(canvas, "Thou art more lovely and more temperate:", ts)
        ts.setForeground(null)
        assert(null == ts.getForeground)
      } finally {
        if (sh != null) sh.close()
        if (p != null) p.close()
        if (ts != null) ts.close()
      }
    }
    {
      val sh = Shader.makeLinearGradient(0, 0, 0, 13, Array[Int](0xFF3A1C71, 0xFFD76D77, 0xFFFFAF7B))
      val p = Paint().setShader(sh)
      val ts = new TextStyle().setBackground(p)
      try {
        assert(p == ts.getBackground)
        drawLine(canvas, "Rough winds do shake the darling buds of May,", ts)
      } finally {
        if (sh != null) sh.close()
        if (p != null) p.close()
        if (ts != null) ts.close()
      }
    }
    var d = DecorationStyle.NONE.copy(underline = true, color = 0xFF3A1C71)
    {
      val ts = new TextStyle().setColor(0xFF000000).setDecorationStyle(d)
      try {
        assert(d.equals(ts.getDecorationStyle), "Expected " + d + ", got " + ts.getDecorationStyle)
        assert(d.underline && !d.overline && !d.lineThrough)
        drawLine(canvas, "And summer’s lease hath all too short a date;", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    d = DecorationStyle.NONE.copy(underline = true, color = 0xFFFFAF7B, gaps = false)
    {
      val ts = new TextStyle().setColor(0xFF000000).setDecorationStyle(d)
      try {
        assert(d.equals(ts.getDecorationStyle))
        drawLine(canvas, "Sometime too hot the eye of heaven shines,", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    for (lineStyle <- DecorationLineStyle.values) {
      d = DecorationStyle.NONE.copy(underline = true, color = 0xFF3A1C71, lineStyle = lineStyle)
      {
        val ts = new TextStyle().setColor(0xFF000000).setDecorationStyle(d)
        try {
          assert(d.equals(ts.getDecorationStyle))
          drawLine(canvas, "And often is his gold complexion dimm'd; (opaque gyroscope)", ts)
        } finally {
          if (ts != null) ts.close()
        }
      }
    }
    d = DecorationStyle.NONE.copy(overline = true, lineThrough = true, color = 0xFFD76D77, thicknessMultiplier = 3)
    {
      val ts = new TextStyle().setColor(0xFF000000).setDecorationStyle(d)
      try {
        assert(d.equals(ts.getDecorationStyle))
        assert(!d.underline && d.overline && d.lineThrough)
        drawLine(canvas, "And every fair from fair sometime declines,", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setFontStyle(FontStyle.BOLD_ITALIC)
      try {
        assert(FontStyle.BOLD_ITALIC.equals(ts.getFontStyle))
        drawLine(canvas, "By chance or nature’s changing course untrimm'd;", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    val shadows = Array[Shadow](new Shadow(0x803A1C71, -1, -1, 0), new Shadow(0xFFD76D77, 3, 3, 3))
    {
      val ts = new TextStyle().setColor(0xFF000000).addShadows(shadows)
      try {
        assert(Array.equals(shadows.asInstanceOf[Array[AnyRef]], ts.getShadows.asInstanceOf[Array[AnyRef]]))
        drawLine(canvas, "But thy eternal summer shall not fade,", ts)
        ts.clearShadows
        assert(Array.equals(new Array[Shadow](0).asInstanceOf[Array[AnyRef]], ts.getShadows.asInstanceOf[Array[AnyRef]]))
      } finally {
        if (ts != null) ts.close()
      }
    }
    val fontFeatures = FontFeature.parse("cv06 cv07")
    val fontFamilies = Array[String]("System Font", "Apple Color Emoji")
    {
      val ts = new TextStyle().setColor(0xFF000000).setFontFamilies(fontFamilies).addFontFeatures(fontFeatures)
      try {
        assert(Array.equals(fontFamilies.asInstanceOf[Array[AnyRef]], ts.getFontFamilies.asInstanceOf[Array[AnyRef]]), "Expected " + fontFamilies.mkString("Array(", ",", ")") + ", got " + ts.getFontFamilies.mkString("Array(", ",", ")"))
        assert(Array.equals(fontFeatures.asInstanceOf[Array[AnyRef]], ts.getFontFeatures.asInstanceOf[Array[AnyRef]]))
        drawLine(canvas, "Nor lose possession of that fair thou 🧑🏿‍🦰 ow’st;", ts)
        ts.clearFontFeatures
        assert(Array.equals(FontFeature.EMPTY.asInstanceOf[Array[AnyRef]], ts.getFontFeatures.asInstanceOf[Array[AnyRef]]))
        val m = ts.getFontMetrics
        if (OperatingSystem.CURRENT ne OperatingSystem.LINUX) {
          assert(m.top < m.ascent && m.ascent < m
            .descent && m.descent < m.bottom)
        }
        drawLine(canvas, m.toString, ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setFontSize(20)
      try {
        assert(20 == ts.getFontSize)
        drawLine(canvas, "Nor shall death brag thou wander’st in his shade,", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setHeight(Some(3f))
      try {
        assert(ts.getHeight.contains(3f))
        drawLine(canvas, "So long as men can breathe or eyes can see,", ts)
        ts.setHeight(None)
        assert(ts.getHeight.isEmpty)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setLetterSpacing(3)
      try {
        assert(3f == ts.getLetterSpacing)
        drawLine(canvas, "When in eternal lines to time thou grow’st:", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setWordSpacing(3)
      try {
        assert(3f == ts.getWordSpacing)
        drawLine(canvas, "So long as men can breathe or eyes can see,", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    // TODO doesn’t work?
    {
      val ts = new TextStyle().setColor(0xFF000000).setTypeface(slabo).setFontFamily("Slabo 13px")
      try {
        assert(slabo == ts.getTypeface)
        drawLine(canvas, "So long lives this, and this gives life to thee. rrrrr", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    // TODO single font?
    for (locale <- Array[String]("zh-Hans", "zh-Hant", "zh-Hant-HK", "ja", "ko", "vi-Hani")) {
      // for (String family: new String[] { "PingFang SC", "PingFang TC", "PingFang HK", "Apple SD Gothic Neo", "Hiragino Sans" }) {
      {
        val ts = new TextStyle().setColor(0xFF000000).setLetterSpacing(2)
                                .setLocale(locale) /*.setFontFamilies(new String[] { "Noto Sans", "Noto Sans JP", "Noto Sans TC", "Noto Sans SC"}) */
        try {
          assert(locale == ts.getLocale)
          drawLine(canvas, "刃令免入全关具刃化外情才抵次海直真示神空者草蔥角道雇骨 " + locale, ts)
        } finally {
          if (ts != null) ts.close()
        }
      }
    }
    for (locale <- Array[String]("ru", "bg")) {
      {
        val ts = new TextStyle().setColor(0xFF000000).setLocale(locale).setFontFamily("System Font")
        try {
          assert(locale == ts.getLocale)
          drawLine(canvas, "Привет как дела это тест кириллицы / болгарицы. locale=\"" + locale + "\"", ts)
        } finally {
          if (ts != null) ts.close()
        }
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setBaselineMode(BaselineMode.IDEOGRAPHIC)
      try {
        assert(BaselineMode.IDEOGRAPHIC eq ts.getBaselineMode)
        drawLine(canvas, "Baseline Mode", ts)
      } finally {
        if (ts != null) ts.close()
      }
    }
    {
      val ts = new TextStyle().setColor(0xFF000000).setPlaceholder
      try {
        assert(ts.isPlaceholder)
      } finally {
        if (ts != null) ts.close()
      }
    }
  }
}