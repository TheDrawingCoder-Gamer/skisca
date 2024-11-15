package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

class ParagraphScene extends Scene {
  var fc = new FontCollection
  var lastUpdate:Long = 0
  fc.setDefaultFontManager(FontMgr.getDefault)
  val fm = new TypefaceFontProvider
  fm.registerTypeface(Scene.jbMono)
  fm.registerTypeface(Scene.inter, "Interface")
  fc.setAssetFontManager(fm)


  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    if (System.currentTimeMillis - lastUpdate > 1000) {
      val cache = fc.getParagraphCache
      // if ((System.currentTimeMillis() / 1000) % 2 == 0) {
      //     System.out.println("ParagrapCache.enabled = true");
      //     cache.reset();
      //     cache.setEnabled(true);
      // } else {
      //     System.out.println("ParagrapCache.enabled = false");
      //     // cache.abandon();
      //     cache.setEnabled(false);
      // }
      // cache.printStatistics();
      lastUpdate = System.currentTimeMillis
    }
    canvas.translate(30, 30)
    {
      val defaultTs = new TextStyle().setColor(0xFF000000)
      val ps = new ParagraphStyle
      val pb = new ParagraphBuilder(ps, fc)
      val boundaries = Paint().setColor(0xFFFAA6B2).setMode(PaintMode.STROKE).setStrokeWidth(1f)
      val placeholders = Paint().setColor(0xFFFAA6B2)
      try {
        // default style
        pb.pushStyle(defaultTs)
        // single style
        try {
          val ts = new TextStyle().setColor(0xFF2a9d8f)
          try {
            pb.pushStyle(ts)
            pb.addText("Shall I compare thee to a summer’s day?\n")
            pb.popStyle
          } finally {
            if (ts != null) ts.close()
          }
        }
        // mixed colors
        try {
          val ts = new TextStyle().setColor(0xFF2a9d8f)
          try {
            pb.addText("Thou art")
            pb.pushStyle(ts)
            pb.addText(" more lovely and")
            pb.popStyle
            pb.addText(" more temperate:\n")
          } finally {
            if (ts != null) ts.close()
          }
        }
        // mixing font sizes
        try {
          val ts = new TextStyle().setColor(0xFF000000).setFontSize(18)
          val ts2 = new TextStyle().setColor(0xFF000000).setFontSize(9)
          try {
            pb.addText("Rough winds")
            pb.pushStyle(ts)
            pb.addText(" do shake")
            pb.popStyle.pushStyle(ts2)
            pb.addText(" the darling buds")
            pb.popStyle
            pb.addText(" of May,\n")
          } finally {
            if (ts != null) ts.close()
            if (ts2 != null) ts2.close()
          }
        }
        // pushing twice
        try {
          val ts = new TextStyle().setColor(0xFF000000).setFontSize(18)
          val ts2 = new TextStyle().setColor(0xFF2a9d8f)
          try {
            pb.addText("And summer’s")
            pb.pushStyle(ts)
            pb.addText(" lease hath")
            pb.pushStyle(ts2)
            pb.addText(" all")
            pb.popStyle
            pb.addText(" too short")
            pb.popStyle
            pb.addText(" a date:\n")
          } finally {
            if (ts != null) ts.close()
            if (ts2 != null) ts2.close()
          }
        }
        // cyrillic
        pb.addText("То нам слепит глаза небесный глаз,\n") // + "Sometime too hot the eye of heaven shines,\n"

        // mixing fonts
        try {
          val ts = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("Verdana"))
          val ts2 = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("Georgia"))
          val ts3 = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("Courier New"))
          try {
            pb.pushStyle(ts)
            pb.addText("And often")
            pb.popStyle.pushStyle(ts2)
            pb.addText(" is his gold")
            pb.popStyle.pushStyle(ts3)
            pb.addText(" complexion dimm’d;\n")
            pb.popStyle
          } finally {
            if (ts != null) ts.close()
            if (ts2 != null) ts2.close()
            if (ts3 != null) ts3.close()
          }
        }
        // emojis
        try {
          val ts = new TextStyle().setColor(0xFF000000)
                                  .setFontFamilies(Array[String]("System Font", "Apple Color Emoji"))
          try {
            pb.pushStyle(ts)
            pb.addText("And every 🧑🏿‍🦰 fair 🦾 from\nfair 🥱 sometime 🧑🏾‍⚕️ declines 👩‍👩‍👧‍👧,\n")
            pb.popStyle
          } finally {
            if (ts != null) ts.close()
          }
        }
        // fonts
        try {
          val ts = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("JetBrains Mono"))
          val ts2 = new TextStyle().setColor(0xFF2a9d8f).setFontFamilies(Array[String]("Interface"))
          val ts3 = new TextStyle().setColor(0xFF000000).setFontFamilies(Array[String]("Inter"))
          try {
            pb.pushStyle(ts)
            pb.addText("By chance ")
            pb.popStyle.pushStyle(ts2)
            pb.addText("or nature’s changing course ")
            pb.popStyle.pushStyle(ts3)
            pb.addText("untrimm’d\n")
            pb.popStyle
          } finally {
            if (ts != null) ts.close()
            if (ts2 != null) ts2.close()
            if (ts3 != null) ts3.close()
          }
        }
        // placeholders
        // pb.setParagraphStyle(ps);
        pb.addText("But thy")
        pb.addPlaceholder(new PlaceholderStyle(20, 40, PlaceholderAlignment.BASELINE, BaselineMode.ALPHABETIC, 0))
        pb.addText("eternal")
        pb.addPlaceholder(new PlaceholderStyle(20, 40, PlaceholderAlignment.ABOVE_BASELINE, BaselineMode.ALPHABETIC, 0))
        pb.addText("summer")
        pb.addPlaceholder(new PlaceholderStyle(20, 40, PlaceholderAlignment.BELOW_BASELINE, BaselineMode
          .IDEOGRAPHIC, 0))
        pb.addText("shall")
        pb.addPlaceholder(new PlaceholderStyle(40, 20, PlaceholderAlignment.TOP, BaselineMode.ALPHABETIC, 0))
        pb.addText("not")
        pb.addPlaceholder(new PlaceholderStyle(20, 40, PlaceholderAlignment.MIDDLE, BaselineMode.ALPHABETIC, 0))
        pb.addText("fade,\n")
        // Nor lose possession of that fair thou ow’st;
        // Nor shall death brag thou wander’st in his shade,
        // When in eternal lines to time thou grow’st:
        //    So long as men can breathe or eyes can see,
        //    So long lives this, and this gives life to thee.
        try {
          val p = pb.build
          try {
            p.layout(Float.PositiveInfinity)
            val minW = p.getMinIntrinsicWidth
            val maxW = p.getMaxIntrinsicWidth
            val range = maxW - minW
            var w = maxW
            while (w >= minW) {
              p.layout(Math.ceil(w).toFloat)
              p.paint(canvas, 0, 0)
              val h = p.getHeight
              canvas.drawRect(Rect.makeXYWH(0, 0, minW, h), boundaries)
              canvas.drawRect(Rect.makeXYWH(0, 0, w, h), boundaries)
              // canvas.drawRect(Rect.makeXYWH(0, 0, maxW, h), boundaries);
              for (placeholder <- p.getRectsForPlaceholders) {
                canvas.drawRect(placeholder.rect, placeholders)
              }
              canvas.translate(w + 15, 0)
              w -= range / 5
            }
          } finally {
            if (p != null) p.close()
          }
        }
      } finally {
        if (defaultTs != null) defaultTs.close()
        if (ps != null) ps.close()
        if (pb != null) pb.close()
        if (boundaries != null) boundaries.close()
        if (placeholders != null) placeholders.close()
      }
    }
  }
}