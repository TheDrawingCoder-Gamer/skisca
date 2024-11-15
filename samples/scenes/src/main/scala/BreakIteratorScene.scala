package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import scala.util.boundary

import java.util.stream.*

class BreakIteratorScene extends Scene {
  _variants = Array[String]("ICU", "java.text")
  private[scenes] val mono11 = new Font(Scene.jbMono, 11)
  private[scenes] var x = 0
  private[scenes] var y = 0

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    x = 20
    y = 60
    var text = "🐉☺️❤️👮🏿👨‍👩‍👧‍👦🚵🏼‍♀️🇷🇺🏴󠁧󠁢󠁥󠁮󠁧󠁿*️⃣ǍǍZ̵̡̢͇͓͎͖͎̪͑͜ͅͅबिक्"
    if ("ICU" == _variants(_variantIdx)) {
      drawCharacterICU(canvas, height, text)
    } else {
      drawCharacterJavaText(canvas, height, text)
    }
    x = width / 2 + 10
    y = 60
    text = "One, (two; three). FoUr,five!"
    if ("ICU" == _variants(_variantIdx)) {
      drawWordICU(canvas, height, text)
    } else {
      drawWordJavaText(canvas, height, text)
    }
  }

  def drawUnicode(canvas: Canvas, str: String): Unit = {
    val decoded = str.codePoints.mapToObj((c: Int) => String.format("U+%4s", java.lang.Long.toString(c, 16).toUpperCase)
                                                            .replaceAll(" ", "0")).collect(Collectors.joining(" "))
    try {
      val line = TextLine.make(decoded, mono11)
      try {
        canvas.drawTextLine(line, x + 50, y, Scene.blackFill)
      } finally {
        if (line != null) line.close()
      }
    }
  }

  def drawSubstring(canvas: Canvas, str: String, height: Int): Unit = {
    {
      val line = TextLine.make(str, Scene.inter13)
      try {
        canvas.drawTextLine(line, x, y, Scene.blackFill)
      } finally {
        if (line != null) line.close()
      }
    }
    y += 20
    if (y + 20 > height - 20) {
      x += 100
      y = 20
    }
  }

  def drawCharacterICU(canvas: Canvas, height: Int, text: String): Unit = {
    try {
      val iter = BreakIterator.makeCharacterInstance
      try {
        iter.setText(text)
        var start = iter.first
        boundary {
          while (true) {
            val end = iter.next
            if (end == BreakIterator.DONE) boundary.break()
            drawUnicode(canvas, text.substring(start, end))
            drawSubstring(canvas, text.substring(start, end), height)
            start = end
          }
        }
      } finally {
        if (iter != null) iter.close()
      }
    }
  }

  def drawCharacterJavaText(canvas: Canvas, height: Int, text: String): Unit = {
    val iter = java.text.BreakIterator.getCharacterInstance
    iter.setText(text)
    var start = iter.first
    boundary {
      while (true) {
        val end = iter.next
        if (end == java.text.BreakIterator.DONE) boundary.break()
        drawUnicode(canvas, text.substring(start, end))
        drawSubstring(canvas, text.substring(start, end), height)
        start = end
      }
    }
  }

  def drawWordICU(canvas: Canvas, height: Int, text: String): Unit = {
    {
      val iter = BreakIterator.makeWordInstance
      try {
        iter.setText(text)
        var start = iter.first
        boundary {
          while (true) {
            val end = iter.next
            if (end == BreakIterator.DONE) boundary.break()
            if (iter.getRuleStatus != BreakIterator.WORD_NONE) drawSubstring(canvas, text.substring(start, end), height)
            start = end
          }
        }
      } finally {
        if (iter != null) iter.close()
      }
    }
  }

  def drawWordJavaText(canvas: Canvas, height: Int, text: String): Unit = {
    val iter = java.text.BreakIterator.getWordInstance
    iter.setText(text)
    var start = iter.first
    boundary {
      while (true) {
        val end = iter.next
        if (end == java.text.BreakIterator.DONE) boundary.break()
        drawSubstring(canvas, text.substring(start, end), height)
        start = end
      }
    }
  }
}