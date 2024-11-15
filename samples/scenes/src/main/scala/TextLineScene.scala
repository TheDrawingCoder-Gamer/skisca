package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.paragraph.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

import java.util.*
import java.util.stream.*
import Scene.*

class TextLineScene extends Scene {
  _variants = Array[String]("Set 1", "Set 2", "Set 3 Text Line", "Set 3 Paragraph")
  _variantIdx = 2
  var fc = new FontCollection
  fc.setDefaultFontManager(FontMgr.getDefault)
  val fm = new TypefaceFontProvider
  fm.registerTypeface(inter)
  fm.registerTypeface(jbMono)
  fc.setAssetFontManager(fm)
  if ("Mac OS X" == System.getProperty("os.name")) {
    zapfino18 = new Font(FontMgr.getDefault.matchFamilyStyle("Zapfino", FontStyle.NORMAL), 18)
    fm.registerTypeface(zapfino18.getTypeface)
  }
  private val fill = new Paint().setColor(0xFFCC3333)
  private val blueFill = new Paint().setColor(0xFF3333CC)
  private val selectionFill = new Paint().setColor(0x80b3d7ff)
  private val stroke = new Paint().setColor(0x803333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)
  private val strokeRed = new Paint().setColor(0x80CC3333).setMode(PaintMode.STROKE).setStrokeWidth(1)
  private val inter9 = new Font(inter, 9).setSubpixel(true)
  private val inter36 = new Font(inter, 36)
  private val jbMono36 = new Font(jbMono, 36)
  private var zapfino18: Font = null
  // private Font emoji36 = new Font(FontMgr.getDefault().matchFamilyStyleCharacter(null, FontStyle.NORMAL, null, "🧛".codePointAt(0)), 36);


  def drawTable(canvas: Canvas, data: Array[String]): Point = {
    var leftWidth: Float = 0
    var leftHeight: Float = 0
    var i = 0
    while (i < data.length) {
      try {
        val line = TextLine.make(data(i), inter9)
        try {
          canvas.drawTextLine(line, 0, leftHeight - line.getAscent, blueFill)
          leftWidth = Math.max(leftWidth, line.getWidth)
          leftHeight += line.getHeight
        } finally {
          if (line != null) line.close()
        }
      }
      i += 2
    }
    var rightWidth: Float = 0
    var rightHeight: Float = 0
    i = 1
    while (i < data.length) {
      try {
        val line = TextLine.make(data(i), inter9)
        try {
          canvas.drawTextLine(line, leftWidth + 5, rightHeight - line.getAscent, blueFill)
          rightWidth = Math.max(rightWidth, line.getWidth)
          rightHeight += line.getHeight
        } finally {
          if (line != null) line.close()
        }
      }
      i += 2
    }
    new Point(leftWidth + 5 + rightWidth, Math.max(leftHeight, rightHeight))
  }

  def drawLine(canvas: Canvas, text: String, font: Font, cursor: Point): Float = drawLine(canvas, Array[String](text), font, cursor)

  def drawLine(canvas: Canvas, texts: Array[String], font: Font, daCursor: Point): Float = {
    var cursor = daCursor
    var bottom: Float = 0
    canvas.save
    for (text <- texts) {
      try {
        val line = TextLine.make(text, font)
        val blob = line.getTextBlob
        try {
          val offset = line.getOffsetAtCoord(cursor.getX)
          // bounds
          val lineWidth = line.getWidth
          val lineHeight = line.getHeight
          val baseline = -line.getAscent
          canvas.drawRect(Rect.makeXYWH(0, 0, lineWidth, lineHeight), stroke)
          canvas.drawLine(0, baseline, lineWidth, baseline, stroke)
          // canvas.drawRect(blob.getBounds().translate(0, baseline), strokeRed);
          // canvas.drawRect(blob.getTightBounds().translate(0, baseline), strokeRed);
          // canvas.drawRect(blob.getBlockBounds().translate(0, baseline), strokeRed);
          // selection
          var coord = line.getCoordAtOffset(offset)
          canvas.drawRect(Rect.makeLTRB(0, 0, coord, lineHeight), selectionFill)
          // strict left selection
          val leftOffset = line.getLeftOffsetAtCoord(cursor.getX)
          coord = line.getCoordAtOffset(leftOffset)
          canvas.drawRect(Rect.makeLTRB(0, 0, coord, lineHeight), selectionFill)
          // text
          canvas.drawTextLine(line, 0, baseline, fill)
          // coords
          for (i <- 0 until text.length + 1) {
            coord = line.getCoordAtOffset(i)
            canvas.drawLine(coord, baseline, coord, baseline + 4, stroke)
          }
          // extra info
          canvas.save
          canvas.translate(0, lineHeight + 5)
          val positionsXY = line.getPositions
          var positions = "["
          var i = 0
          while (i < positionsXY.length) {
            positions = positions + formatFloat(positionsXY(i)) + ", "
            i += 2
          }
          positions = (if (positions.length <= 2) {
            positions
          } else {
            positions.substring(0, positions.length - 2)
          }) + "] .. " + formatFloat(lineWidth)
          val tableSize = drawTable(canvas, Array[String]("Chars", text.chars.mapToObj((c: Int) => String
            .format(if (c <= 256) {
              "%02X"
            } else {
              "%04X"
            }, c)).collect(Collectors.joining(" ")), "Code points", text.codePoints
                                                                        .mapToObj((c: Int) => String
                                                                                    .format(if (c <= 256) {
                                                                                      "%02X"
                                                                                    } else {
                                                                                      "%04X"
                                                                                    }, c)).collect(Collectors
              .joining(" ")), "Break Positions", formatFloatArray(TextLine
            ._nGetBreakPositions(line._ptr)), "Break Offsets", TextLine
            ._nGetBreakOffsets(line._ptr).mkString, "Glyphs", line
            .getGlyphs.mkString, "Positions", positions, "Coord", Integer
            .toString(cursor.getX.toInt), "Offset", offset + " (" + leftOffset + ")  /" + " " + text.length))
          canvas.restore
          val offsetLeft = Math.max(100, Math.max(lineWidth, tableSize.getX)) + 10
          canvas.translate(offsetLeft, 0)
          cursor = cursor.offset(-offsetLeft, 0)
          bottom = Math.max(bottom, lineHeight + 5 + tableSize.getY)
        } finally {
          if (line != null) line.close()
          if (blob != null) blob.close()
        }
      }
    }
    canvas.restore
    canvas.translate(0, bottom + 10)
    bottom + 10
  }

  def drawParagraph(canvas: Canvas, texts: Array[String], font: Font, daCursor: Point): Float = {
    var cursor = daCursor
    var bottom: Float = 0
    canvas.save
    for (text <- texts) {
      {
        val defaultTs = new TextStyle().setFontFamily(font.getTypeface.getFamilyName).setFontSize(36)
                                       .setColor(0xFFCC3333)
        val ps = new ParagraphStyle
        val pb = new ParagraphBuilder(ps, fc)
        try {
          pb.pushStyle(defaultTs)
          pb.addText(text)
          {
            val p = pb.build
            try {
              p.layout(Float.PositiveInfinity)
              val lm = p.getLineMetrics(0)
              val offset = p.getGlyphPositionAtCoordinate(cursor.getX, 0).position
              // bounds
              val lineWidth = lm.width.toFloat
              val lineHeight = lm.height.toFloat
              val baseline = lm.ascent.toFloat
              canvas.drawRect(Rect.makeXYWH(0, 0, lineWidth, lineHeight), stroke)
              canvas.drawLine(0, baseline, lineWidth, baseline, stroke)
              // selection
              var coord: Float = 0
              var rects = p.getRectsForRange(0, offset, RectHeightMode.TIGHT, RectWidthMode.TIGHT)
              for (rect <- rects) {
                canvas.drawRect(rect.rect, selectionFill)
                coord = rect.rect.getRight
              }
              // text
              p.paint(canvas, 0, 0)
              // coords
              for (i <- 0 until text.length + 1) {
                rects = p.getRectsForRange(0, i, RectHeightMode.TIGHT, RectWidthMode.TIGHT)
                for (rect <- rects) {
                  coord = rect.rect.getRight
                  canvas.drawLine(coord, baseline, coord, baseline + 4, stroke)
                }
              }
              // extra info
              canvas.save
              canvas.translate(0, lineHeight + 5)
              val tableSize = drawTable(canvas, Array[String]("Chars", text.chars.mapToObj((c: Int) => String
                .format(if (c <= 256) {
                  "%02X"
                } else {
                  "%04X"
                }, c)).collect(Collectors.joining(" ")), "Coord", Integer
                .toString(cursor.getX.toInt), "Offset", offset + " " + text.length))
              canvas.restore
              val offsetLeft = Math.max(100, Math.max(lineWidth, tableSize.getX)) + 10
              canvas.translate(offsetLeft, 0)
              cursor = cursor.offset(-offsetLeft, 0)
              bottom = Math.max(bottom, lineHeight + 5 + tableSize.getY)
            } finally {
              if (p != null) p.close()
            }
          }
        } finally {
          if (defaultTs != null) defaultTs.close()
          if (ps != null) ps.close()
          if (pb != null) pb.close()
        }
      }
    }
    canvas.restore
    canvas.translate(0, bottom + 10)
    bottom + 10
  }

  def drawIt(canvas: Canvas, texts: Array[String], font: Font, cursor: Point): Float = {
    if (_variants(_variantIdx)
      .endsWith("Paragraph")) {
      drawParagraph(canvas, texts, font, cursor)
    } else {
      drawLine(canvas, texts, font, cursor)
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    var cursor = new Point(xpos, ypos)
    canvas.translate(20, 20)
    cursor = cursor.offset(-20, -20)
    if (_variants(_variantIdx).startsWith("Set 1")) {
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("", "one", "yf", "два", "الخطوط", "🧛", "one yf الخطوط два 🧛"), inter36, cursor))
      if (zapfino18 != null) {
        cursor = cursor
          .offset(0, -drawIt(canvas, Array[String]("fiz officiad"), zapfino18, cursor))
      } // swashes
      // cursor = cursor.offset(0, -drawIt(canvas, "sixستةten", inter36, cursor));
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("الكلب", "sixستةten", "one اثنان 12 واحد two"), inter36, cursor)) // RTL
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("<->", "a<->b", "🧑🏿", "a🧑🏿b"), inter36, cursor)) // Ligatures
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("x̆x̞̊x̃", "c̝̣̱̲͈̝ͨ͐̈ͪͨ̃ͥͅh̙̬̿̂a̯͎͍̜͐͌͂̚o̬s͉̰͊̀"), inter36, cursor)) // Zero-witdh modifiers
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("क्", "क्‍", "👨👩👧👦", "👨‍👩‍👧‍👦", "a👨‍👩‍👧‍👦b"), inter36, cursor)) // ZWJ
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("می‌خواهم‎", "میخواهم"), inter36, cursor)) // ZWNJ
    }
    else if (_variants(_variantIdx).startsWith("Set 2")) {
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("", "🧛", "🧛🧛", "🧛a🧛", "a🧛a🧛a", "🧛 🧛"), inter36, cursor))
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("☹️", "☹️☹️", "☹️a☹️", "a☹️a☹️a", "☹️ ☹️"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("🤵🏽", "👨‍🏭", "🇦🇱", "*️⃣"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("🏴󠁧󠁢󠁳󠁣󠁴󠁿", "🚵🏻‍♀️"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("😮‍💨", "❤️‍🔥", "🧔‍♀", "👨🏻‍❤️‍💋‍👨🏼"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("Ǎ", "Ǎ", "x̆x̞̊x̃", "<->"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("Z̵̡̢͇͓͎͖͎̪͑͜ͅͅ"), inter36, cursor))
    }
    else if (_variants(_variantIdx).startsWith("Set 3")) {
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("abcdef"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("-><=><->=>"), inter36, cursor))
      cursor = cursor
        .offset(0, -drawIt(canvas, Array[String]("aăå̞ã dấu hỏi k̟t̠ c̝̣̱̲͈̝ͨ͐̈ͪͨ̃ͥͅh̙̬̿̂a̯͎͍̜͐͌͂̚o̬s͉̰͊̀"), jbMono36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("☹️🤵🏽👨‍🏭🇦🇱*️⃣🏴󠁧󠁢󠁳󠁣󠁴󠁿🚵🏻‍♀️🤦🏼‍♂"), inter36, cursor))
      cursor = cursor.offset(0, -drawIt(canvas, Array[String]("🧔‍♀👨🏻‍❤️‍💋‍👨🏼"), inter36, cursor))
    }
  }
  // Furthermore, العربية نص جميل. द क्विक ब्राउन फ़ॉक्स jumps over the lazy 🐕.
  // A true 🕵🏽‍♀️ will spot the tricky selection in this BiDi text: ניפגש ב09:35 בחוף הים
}