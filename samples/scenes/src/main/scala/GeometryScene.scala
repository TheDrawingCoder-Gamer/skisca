package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class GeometryScene extends Scene {
  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val borderStroke = Paint().setColor(0xFFFF0751).setMode(PaintMode.STROKE).setStrokeWidth(1f)
    canvas.drawRect(Rect.makeLTRB(10, 10, width - 10, height - 10), borderStroke)
    canvas.translate(30, 30)
    drawPoints(canvas)
    drawLines(canvas)
    drawArcs(canvas)
    drawVertices(canvas)
    drawPatch(canvas)
    drawRotate(canvas)
    drawOtherTransform(canvas)
    drawRectInscribed(canvas, Paint().setColor(0xFF1D7AA2).setMode(PaintMode.STROKE).setStrokeWidth(1f))
    drawRectInscribed(canvas, Paint().setColor(0xFF6DC1B3).setMode(PaintMode.STROKE).setStrokeWidth(5f))
    drawRectInscribed(canvas, Paint().setColor(0xFF9BC730))
    drawClips(canvas)
    drawRegions(canvas, dpi)
  }

  def drawPoints(canvas: Canvas): Unit = {
    val strokeHalfPx = Paint().setStrokeWidth(0.5f)
    val stroke1px = Paint().setStrokeWidth(1f)
    val stroke2px = Paint().setStrokeWidth(2f)
    val stroke5px = Paint().setStrokeWidth(5f)
    val stroke5pxRound = Paint().setStrokeWidth(5f).setStrokeCap(PaintStrokeCap.ROUND)
    val stroke5pxSquare = Paint().setStrokeWidth(5f).setMode(PaintMode.STROKE).setStrokeCap(PaintStrokeCap.SQUARE)
    canvas.save
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), strokeHalfPx)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0.5f, 30.5f, 10.5f, 10.5f, 20.5f, 20.5f, 30.5f, 0.5f), strokeHalfPx)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke1px)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke2px)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5px)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5pxRound)
    canvas.translate(40, 0)
    canvas.drawPoints(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5pxSquare)
    canvas.translate(40, 0)
    canvas.drawLines(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5px)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), strokeHalfPx)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke1px)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke2px)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5px)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5pxRound)
    canvas.translate(40, 0)
    canvas.drawPolygon(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), stroke5pxSquare)
    canvas.translate(40, 0)
    canvas.drawPath(Path().addPoly(Array[Float](0, 30, 10, 10, 20, 20, 30, 0), false), stroke5pxSquare)
    canvas.translate(40, 0)
    canvas.restore
    canvas.translate(0, 50)
  }

  def drawLines(canvas: Canvas): Unit = {
    val stroke1px = Paint().setStrokeWidth(1f)
    val stroke5px = Paint().setStrokeWidth(5f)
    val stroke5pxRound = Paint().setStrokeWidth(5f).setStrokeCap(PaintStrokeCap.ROUND)
    val stroke5pxSquare = Paint().setStrokeWidth(5f).setStrokeCap(PaintStrokeCap.SQUARE)
    canvas.save
    canvas.drawLine(0, 0, 0, 30, stroke1px)
    canvas.drawLine(10, 0, 10, 30, stroke5px)
    canvas.drawLine(20, 0, 20, 30, stroke5pxRound)
    canvas.drawLine(30, 0, 30, 30, stroke5pxSquare)
    canvas.drawLine(40, 0, 70, 0, stroke1px)
    canvas.drawLine(40, 10, 70, 10, stroke5px)
    canvas.drawLine(40, 20, 70, 20, stroke5pxRound)
    canvas.drawLine(40, 30, 70, 30, stroke5pxSquare)
    canvas.drawLine(80, 0, 110, 30, stroke1px)
    canvas.drawLine(100, 0, 130, 30, stroke5px)
    canvas.drawLine(120, 0, 150, 30, stroke5pxRound)
    canvas.drawLine(140, 0, 170, 30, stroke5pxSquare)
    canvas.drawLine(160, 0, 190, 30, stroke1px.setAntiAlias(false))
    canvas.drawLine(180, 0, 210, 30, stroke5px.setAntiAlias(false))
    canvas.drawLine(200, 0, 230, 30, stroke5pxRound.setAntiAlias(false))
    canvas.drawLine(220, 0, 250, 30, stroke5pxSquare.setAntiAlias(false))
    canvas.restore
    canvas.translate(0, 50)
  }

  def drawVertices(canvas: Canvas): Unit = {
    canvas.save
    val positions = Array[Point](new Point(0, 0), new Point(40, 0), new Point(20, 20), new Point(40, 20), new Point(0, 40), new Point(40, 40))
    var colors = Array[Int](0xFFFF0000, 0xFF0000FF, 0xFFFFFF00, 0xFF00FFFF, 0xFF00FF00, 0xFFFF00FF)
    {
      val paint = Paint()
      val shader = Shader.makeLinearGradient(0, 0, 40, 0, Array[Int](0xFF277da1, 0xFFffba08))
      try {
        canvas.drawTriangles(positions, colors, paint)
        canvas.translate(50, 0)
        val indices = Array[Short](0, 1, 4)
        canvas.drawTriangles(positions, colors, null, indices, paint)
        canvas.translate(50, 0)
        canvas.drawTriangleStrip(positions, colors, paint)
        canvas.translate(50, 0)
        canvas.drawTriangleFan(positions, colors, paint)
        canvas.translate(50, 0)
        paint.setShader(shader)
        canvas.drawTriangles(positions, colors, paint)
        canvas.translate(50, 0)
        canvas.drawTriangleStrip(positions, colors, null, null, BlendMode.SRC_OVER, paint)
        canvas.translate(50, 0)
        colors = Array[Int](0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF)
        canvas.drawTriangleFan(positions, colors, paint)
        canvas.translate(50, 0)
        val texCoords = Array[Point](new Point(0, 0), new Point(40, 0), new Point(20, 20), new Point(40, 40), new Point(0, 40), new Point(30, 30))
        canvas.drawTriangles(positions, colors, texCoords, null, paint)
        canvas.translate(50, 0)
      } finally {
        if (paint != null) paint.close()
        if (shader != null) shader.close()
      }
    }
    canvas.restore
    canvas.translate(0, 50)
  }

  def drawPatch(canvas: Canvas): Unit = {
    canvas.save
    val cubics = Array[Point](new Point(30, 10), new Point(40, 20), new Point(50, 10), new Point(70, 30), /* new Point(70, 30), */ new Point(60, 40), new Point(70, 50), new Point(50, 70), /* new Point(50, 70), */ new Point(40, 60), new Point(30, 70), new Point(10, 50), /* new Point(10, 50), */ new Point(20, 40), new Point(10, 30))
    var colors = Array[Int](0xFFFF0000, 0xFF0000FF, 0xFFFFFF00, 0xFF00FFFF)
    {
      val paint = Paint()
      val shader = Shader.makeLinearGradient(0, 0, 70, 0, Array[Int](0xFF277da1, 0xFFffba08))
      try {
        canvas.drawPatch(cubics, colors, paint)
        canvas.translate(80, 0)
        paint.setShader(shader)
        canvas.drawPatch(cubics, colors, paint)
        canvas.translate(80, 0)
        canvas.drawPatch(cubics, colors, null, BlendMode.SRC_OVER, paint)
        canvas.translate(80, 0)
        colors = Array[Int](0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF)
        canvas.drawPatch(cubics, colors, paint)
        canvas.translate(80, 0)
        val texCoords = Array[Point](new Point(0, 0), new Point(70, 0), new Point(70, 70), new Point(0, 70))
        canvas.drawPatch(cubics, colors, texCoords, paint)
        canvas.translate(80, 0)
      } finally {
        if (paint != null) paint.close()
        if (shader != null) shader.close()
      }
    }
    canvas.restore
    canvas.translate(0, 80)
  }

  def drawRotate(canvas: Canvas): Unit = {
    val stroke1px = Paint().setStrokeWidth(1f)
    canvas.save
    canvas.translate(20, 20)
    var i = 0
    while (i < 360) {
      canvas.save
      canvas.rotate(i)
      canvas.drawLine(5, 0, 20, 0, stroke1px)
      canvas.restore
      i += 30
    }
    canvas.restore
    canvas.translate(0, 50)
  }

  def drawOtherTransform(canvas: Canvas): Unit = {
    val paint = Paint().setColor(0xFFFFCC66)
    canvas.save
    canvas.save
    canvas.skew(0.3F, 0)
    canvas.drawRect(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.restore
    canvas.translate(90, 0)
    canvas.save
    canvas.skew(0F, 0.1F)
    canvas.drawRect(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.restore
    canvas.translate(80, 0)
    canvas.save
    canvas.skew(-0.2F, 0F)
    canvas.drawOval(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.restore
    canvas.translate(75, 0)
    canvas.save
    val cos45 = Math.cos(Math.PI / 4).toFloat
    val rotateX45 = Matrix44.apply(1, 0, 0, 0, 0, cos45, -cos45, 0, 0, cos45, cos45, 0, 0, 0, 0, 1)
    canvas.concat(rotateX45)
    canvas.drawOval(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.restore
    canvas.translate(75, 0)
    canvas.restore
    canvas.translate(0, 60)
  }

  def drawArcs(canvas: Canvas): Unit = {
    val stroke1px = Paint().setMode(PaintMode.STROKE).setStrokeWidth(1f)
    val fill = Paint().setColor(0xFFFC7901)
    canvas.save
    var angle = 60f
    while (angle <= 360f) {
      canvas.drawArc(0, 0, 40, 40, 0, angle, false, stroke1px)
      canvas.translate(50, 0)
      angle += 60
    }
    angle = 60f
    while (angle <= 360f) {
      canvas.drawArc(0, 0, 40, 40, 0, -angle, true, stroke1px)
      canvas.translate(50, 0)
      angle += 60
    }
    canvas.restore
    canvas.translate(0, 50)
    canvas.save
    angle = 30f
    while (angle <= 180f) {
      canvas.drawArc(0, 0, 40, 40, angle, -angle * 2, false, fill)
      canvas.translate(50, 0)
      angle += 30
    }
    angle = 30f
    while (angle <= 180f) {
      canvas.drawArc(0, 0, 40, 40, 180 - angle, angle * 2, true, fill)
      canvas.translate(50, 0)
      angle += 30
    }
    canvas.restore
    canvas.translate(0, 50)
  }

  def drawRectInscribed(canvas: Canvas, paint: Paint): Unit = {
    canvas.save
    canvas.drawRect(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.translate(75, 0)
    canvas.drawOval(Rect.makeXYWH(0, 0, 65, 40), paint)
    canvas.translate(75, 0)
    canvas.drawCircle(20, 20, 20, paint)
    canvas.translate(50, 0)
    canvas.drawRRect(RRect.makeXYWH(0, 0, 65, 40, 10), paint)
    canvas.translate(75, 0)
    canvas.drawRRect(RRect.makeXYWH(0, 0, 65, 40, 10, 20), paint)
    canvas.translate(75, 0)
    canvas.drawRRect(RRect.makeXYWH(0, 0, 65, 40, 4, 8, 12, 16), paint)
    canvas.translate(75, 0)
    canvas.drawRRect(RRect.makeNinePatchXYWH(0, 0, 65, 40, 4, 8, 12, 16), paint)
    canvas.translate(75, 0)
    canvas.drawRRect(RRect.makeComplexXYWH(0, 0, 65, 40, Array[Float](2, 4, 6, 8, 10, 12, 14, 16)), paint)
    canvas.translate(75, 0)
    canvas.drawDRRect(RRect.makeXYWH(0, 0, 65, 40, 0), RRect.makeXYWH(10, 10, 45, 20, 20, 10), paint)
    canvas.translate(75, 0)
    canvas.drawDRRect(RRect.makeXYWH(0, 0, 65, 40, 10), RRect.makeXYWH(5, 5, 55, 30, 5, 5), paint)
    canvas.translate(75, 0)
    canvas.restore
    canvas.translate(0, 50)
  }

  private def drawClips(canvas: Canvas): Unit = {
    canvas.save
    canvas.save
    canvas.clipRect(Rect.makeXYWH(0, 0, 40, 40))
    canvas.drawPaint(Paint().setColor(0xFF50514F))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    canvas.clipRect(Rect.makeXYWH(0, 0, 30, 30))
    canvas.clipRect(Rect.makeXYWH(10, 10, 30, 30), ClipMode.INTERSECT)
    canvas.drawPaint(Paint().setColor(0xFFF55E58))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    canvas.clipRect(Rect.makeXYWH(0, 0, 30, 30))
    canvas.clipRect(Rect.makeXYWH(10, 10, 30, 30), ClipMode.DIFFERENCE)
    canvas.drawPaint(Paint().setColor(0xFFFFE15C))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    canvas.clipRect(Rect.makeXYWH(0, 0, 40, 40))
    canvas.translate(20, 20)
    canvas.rotate(15)
    canvas.clipRect(Rect.makeXYWH(-15, -15, 30, 30), ClipMode.DIFFERENCE, false)
    canvas.drawPaint(Paint().setColor(0xFF1D7AA2))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    canvas.clipRect(Rect.makeXYWH(0, 0, 40, 40))
    canvas.translate(20, 20)
    canvas.rotate(15)
    canvas.clipRect(Rect.makeXYWH(-15, -15, 30, 30), ClipMode.DIFFERENCE, true)
    canvas.drawPaint(Paint().setColor(0xFF6DC1B3))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    canvas.clipRRect(RRect.makeXYWH(0, 0, 40, 40, 10), false)
    canvas.clipRRect(RRect.makePillXYWH(10, 10, 30, 20), ClipMode.DIFFERENCE, true)
    canvas.drawPaint(Paint().setColor(0xFFFF928A))
    canvas.restore
    canvas.translate(50, 0)
    canvas.save
    val path = Path().moveTo(0, 12.5f).rCubicTo(0, -5f, 0, -7.5f, 2.5f, -10f)
                   .rCubicTo(2.5f, -2.5f, 5f, -2.5f, 10f, -2.5f).rLineTo(15f, 0).rCubicTo(5, 0, 7.5f, 0, 10, 2.5f)
                   .rCubicTo(2.5f, 2.5f, 2.5f, 5f, 2.5f, 10f).lineTo(40, 22f)
                   .arcTo(Rect.makeLTRB(22f, 22f, 40, 40), 0, 90, false).lineTo(18f, 40)
                   .arcTo(Rect.makeLTRB(0, 22f, 18f, 40), 90, 90, false).closePath
    canvas.clipPath(path, true)
    canvas.drawPaint(Paint().setColor(0xFF3F80A7))
    canvas.restore
    canvas.translate(50, 0)
    canvas.restore
    canvas.translate(0, 50)
  }

  private def drawRegions(canvas: Canvas, dpi: Float): Unit = {
    val bgPaint = Paint().setColor(0xFFEBD3AA)
    val stroke1 = Paint().setColor(0xFF3F80A7).setMode(PaintMode.STROKE).setStrokeWidth(1f)
    val stroke2 = Paint().setColor(0xFFF55E58).setMode(PaintMode.STROKE).setStrokeWidth(1f)
    var xOffset = 30
    val yOffset = 670
    canvas.save
    // setRect
    val r = new Region
    r.setRect(IRect
      .makeLTRB((xOffset * dpi).toInt, (yOffset * dpi).toInt, ((xOffset + 40) * dpi).toInt, ((yOffset + 40) * dpi)
        .toInt))
    canvas.save
    canvas.clipRegion(r)
    canvas.drawPaint(bgPaint)
    canvas.restore
    canvas.translate(50, 0)
    xOffset += 50
    // setRects
    r.setRects(Array[IRect](IRect
      .makeLTRB(((xOffset + 0) * dpi).toInt, ((yOffset + 0) * dpi).toInt, ((xOffset + 10) * dpi)
        .toInt, ((yOffset + 40) * dpi).toInt), IRect
      .makeLTRB(((xOffset + 30) * dpi).toInt, ((yOffset + 0) * dpi).toInt, ((xOffset + 40) * dpi)
        .toInt, ((yOffset + 40) * dpi).toInt), IRect
      .makeLTRB(((xOffset + 5) * dpi).toInt, ((yOffset + 15) * dpi).toInt, ((xOffset + 35) * dpi)
        .toInt, ((yOffset + 25) * dpi).toInt)))
    canvas.save
    canvas.clipRegion(r)
    canvas.drawPaint(bgPaint)
    canvas.restore
    canvas.translate(50, 0)
    xOffset += 50
    // setPath
    val path = Path().setFillMode(PathFillMode.EVEN_ODD).moveTo(xOffset * dpi, yOffset * dpi)
                   .rMoveTo(20 * dpi, 1.6f * dpi).rLineTo(11.7f * dpi, 36.2f * dpi).rLineTo(-30.8f * dpi, -22.4f * dpi)
                   .rLineTo(38.1f * dpi, 0f * dpi).rLineTo(-30.8f * dpi, 22.4f * dpi).closePath
    val r2 = new Region
    r2.setRect(IRect.makeLTRB(((xOffset + 7) * dpi).toInt, ((yOffset + 7) * dpi).toInt, ((xOffset + 33) * dpi)
      .toInt, ((yOffset + 33) * dpi).toInt))
    r.setEmpty
    r.setPath(path, r2)
    canvas.save
    canvas.clipRegion(r)
    canvas.drawPaint(bgPaint)
    canvas.restore
    canvas.translate(50, 0)
    xOffset += 50
    // op(IRect), getBounds, getBoundaryPath
    for (op <- Region.Op.values) {
      r.setRect(IRect
        .makeLTRB((xOffset * dpi).toInt, (yOffset * dpi).toInt, ((xOffset + 30) * dpi).toInt, ((yOffset + 40) * dpi)
          .toInt))
      r.op(IRect.makeLTRB(((xOffset + 10) * dpi).toInt, ((yOffset + 10) * dpi).toInt, ((xOffset + 40) * dpi)
        .toInt, ((yOffset + 30) * dpi).toInt), op)
      canvas.save
      canvas.clipRegion(r)
      canvas.drawPaint(bgPaint)
      canvas.restore
      val bounds = r.getBounds
      val boundaryPath = Path()
      r.getBoundaryPath(boundaryPath)
      canvas.save
      canvas.translate(-xOffset, -yOffset)
      canvas.scale(1f / dpi, 1f / dpi)
      canvas.drawRect(Rect.makeLTRB(bounds.getLeft, bounds.getTop, bounds.getRight, bounds.getBottom), stroke1)
      canvas.drawPath(boundaryPath, stroke2)
      canvas.restore
      var x = 5
      while (x < 40) {
        var y = 5
        while (y < 40) {
          val absX = (xOffset + x) * dpi
          val absY = (yOffset + y) * dpi
          if (r.contains(absX.toInt, absY.toInt)) canvas.drawPoint(x, y, stroke1)
          y += 10
        }
        x += 10
      }
      canvas.translate(50, 0)
      xOffset += 50
    }
    // op(Region, Region), getBounds, getBoundaryPath
    for (op <- Region.Op.values) {
      val rA = new Region
      rA.setRect(IRect
        .makeLTRB((xOffset * dpi).toInt, (yOffset * dpi).toInt, ((xOffset + 30) * dpi).toInt, ((yOffset + 40) * dpi)
          .toInt))
      val rB = new Region
      rB.setRect(IRect.makeLTRB(((xOffset + 10) * dpi).toInt, ((yOffset + 10) * dpi).toInt, ((xOffset + 40) * dpi)
        .toInt, ((yOffset + 30) * dpi).toInt))
      r.op(rA, rB, op)
      canvas.save
      canvas.clipRegion(r)
      canvas.drawPaint(bgPaint)
      canvas.restore
      val bounds = r.getBounds
      val boundaryPath = Path()
      r.getBoundaryPath(boundaryPath)
      canvas.save
      canvas.translate(-xOffset, -yOffset)
      canvas.scale(1f / dpi, 1f / dpi)
      canvas.drawRect(Rect.makeLTRB(bounds.getLeft, bounds.getTop, bounds.getRight, bounds.getBottom), stroke1)
      canvas.drawPath(boundaryPath, stroke2)
      canvas.restore
      var x = 5
      while (x < 40) {
        var y = 5
        while (y < 40) {
          val absX = (xOffset + x) * dpi
          val absY = (yOffset + y) * dpi
          if (r.contains(absX.toInt, absY.toInt)) canvas.drawPoint(x, y, stroke1)
          y += 10
        }
        x += 10
      }
      canvas.translate(50, 0)
      xOffset += 50
    }
    canvas.restore
    canvas.translate(0, 50)
  }
}