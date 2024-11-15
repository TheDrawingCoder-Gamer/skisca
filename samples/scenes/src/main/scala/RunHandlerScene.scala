package gay.menkissing.skisca.examples.scenes

import Scene.*

import gay.menkissing.skisca.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

import java.util.*
import java.util.stream.*
import scala.util.boundary

class RunHandlerScene extends Scene {
  lato36 = new Font(Typeface.makeFromFile(file("fonts/Lato-Regular.ttf")), 36)
  inter9 = new Font(inter, 9)
  _variants = Array[String]("Approximate All", "Approximate Spaces", "Approximate Punctuation", "Approximate None")
  final var lato36: Font = null
  final var inter9: Font = null
  final val boundsStroke = Paint().setColor(0x403333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)
  final val boundsFill = Paint().setColor(0x403333CC)
  final val textFill = Paint().setColor(0xFF000000)

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(20, 20)
    val text = "hello мир, дружба! fi fl 👃 one two ثلاثة 12 👂 34 خمسة"
    {
      val shaper = Shaper.makeShapeThenWrap
      // Shaper.makeCoreText();
      val tbHandler = new TextBlobBuilderRunHandler(text, new Point(0, 0))
      val handler = new DebugTextBlobHandler().withRuns
      try {
        val opts = _variants(_variantIdx) match {
          case "Approximate Spaces" => ShapingOptions.DEFAULT.copy(approximatePunctuation = false)
          case "Approximate Punctuation" => ShapingOptions.DEFAULT.copy(approximateSpaces = false)
          case "Approximate None" => {
            ShapingOptions.DEFAULT.copy(approximateSpaces = false, approximatePunctuation = false)
          }
          case _ => ShapingOptions.DEFAULT
        }
        // TextBlobBuilderRunHandler
        shaper.shape(text, lato36, opts, width - 40, tbHandler)
        try {
          val blob = tbHandler.makeBlob
          try {
            canvas.drawTextBlob(blob, 0, 0, textFill)
            canvas.translate(0, blob.getBounds.getBottom + 20)
          } finally {
            if (blob != null) blob.close()
          }
        }
        // DebugTextBlobHandler
        shaper.shape(text, lato36, opts, width - 40, handler)
        try {
          val blob = handler.makeBlob
          try {
            canvas.drawTextBlob(blob, 0, 0, textFill)
          } finally {
            if (blob != null) blob.close()
          }
        }
        val taken = new collection.mutable.ListBuffer[Rect]
        val interMetrics = inter9.getMetrics
        var maxBottom: Float = 0
        
        for (run <- handler._runs) {
          val runBounds = run.bounds
          canvas.drawRect(runBounds, boundsStroke)
          val info = run.info
          try {
            val builder = new TextBlobBuilder
            try {
              val lh = interMetrics.getHeight
              var yPos = -interMetrics.ascent
              val padding = 6
              val margin = 6
              val font = run.font
              // build details blob
              try {
                val typeface = font.getTypeface
                try {
                  builder.appendRun(inter9, typeface.getFamilyName + " " + font.getSize + "px", 0, yPos)
                } finally {
                  if (typeface != null) typeface.close()
                }
              }
              builder.appendRun(inter9, "bidi " + info.bidiLevel, 0, yPos + lh)
              builder.appendRun(inter9, "adv (" + info.advanceX + ", " + info.advanceY + ")", 0, yPos + lh * 2)
              builder.appendRun(inter9, "range " + info.rangeBegin + ".." + info.getRangeEnd + " “" + text
                .substring(info.rangeBegin, info.getRangeEnd) + "”", 0, yPos + lh * 3)
              builder.appendRun(inter9, info.glyphCount + " glyphs: " + run
                .glyphs.mkString, 0, yPos + lh * 4)
              builder.appendRun(inter9, "x positions " + java.util.Arrays.stream(run.positions).map(_.getX)
                                                             .map(_.toString).collect(Collectors
                  .joining(", ", "[", "]")), 0, yPos + lh * 5)
              builder.appendRun(inter9, "y position " + run.positions(0).getY, 0, yPos + lh * 6)
              builder.appendRun(inter9, "clusters " + run.clusters.mkString, 0, yPos + lh * 7)
              try {
                val detailsBlob = builder.build
                try {
                  // try to fit in
                  val detailsWidth = detailsBlob.getBounds.getWidth
                  val detailsHeight = detailsBlob.getBounds.getHeight
                  yPos = runBounds.getBottom + margin
                  boundary {
                    while (yPos < height) {
                      val detailsOuter = Rect.makeXYWH(runBounds
                        .getLeft, yPos, detailsWidth + 2 * padding + margin, detailsHeight + 2 * padding + margin)
                      val detailsBorder = Rect
                        .makeXYWH(runBounds.getLeft, yPos, detailsWidth + 2 * padding, detailsHeight + 2 * padding)
                      val detailsInner = Rect
                        .makeXYWH(runBounds.getLeft + padding, yPos + padding, detailsWidth, detailsHeight)
                      if (taken.forall((r: Rect) => r.intersect(detailsOuter) == null)) {
                        // draw details
                        canvas.drawRect(detailsBorder, boundsFill)
                        canvas.drawLine(runBounds.getLeft, runBounds.getBottom, detailsBorder.getLeft, detailsBorder
                          .getBottom, boundsStroke)
                        canvas.drawTextBlob(detailsBlob, detailsInner.getLeft, detailsInner.getTop, textFill)
                        taken.append(detailsOuter)
                        maxBottom = Math.max(maxBottom, detailsOuter.getBottom)
                        boundary.break()
                      }
                      yPos += margin
                    }
                  }
                } finally {
                  if (detailsBlob != null) detailsBlob.close()
                }
              }
            } finally {
              if (builder != null) builder.close()
            }
          }
        }
      } finally {
        if (shaper != null) shaper.close()
        if (tbHandler != null) tbHandler.close()
        if (handler != null) handler.close()
      }
    }
  }
}