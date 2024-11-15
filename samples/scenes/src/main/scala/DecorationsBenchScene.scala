package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class DecorationsBenchScene extends Scene {
  _variants = Array[String]("Path Effect", "Path", "Image")
  _variantIdx = 2
  var element: Path = Path().moveTo(0, -1.5f).lineTo(2, 0.5f).lineTo(4, -1.5f).lineTo(4, -0.5f).lineTo(2, 1.5f)
                                .lineTo(0, -0.5f).closePath.transform(Matrix33.makeScale(1))
  var effect: PathEffect = PathEffect.makePath1D(element, 1 * 4, 0, PathEffect.Style.TRANSLATE)
  var odd = 0xffde8ece
  var even = 0xff03c5cf
  var effectStrokeOdd: Paint = Paint().setColor(odd).setPathEffect(effect)
  var effectStrokeEven: Paint = Paint().setColor(even).setPathEffect(effect)
  var pathStrokeOdd: Paint = Paint().setColor(odd).setMode(PaintMode.STROKE).setStrokeWidth(1)
  var pathStrokeEven: Paint = Paint().setColor(even).setMode(PaintMode.STROKE).setStrokeWidth(1)
  var shaderStrokeOdd: Paint = null
  var shaderStrokeEven: Paint = null

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    if (shaderStrokeOdd == null) {
      try {
        val surface = Surface.makeRaster(ImageInfo.makeN32Premul(Math.ceil(4 * dpi).toInt, Math.ceil(3 * dpi).toInt))
        try {
          val c = surface.getCanvas
          c.scale(dpi, dpi)
          c.translate(0, 1.5f)
          c.drawPath(element, Paint().setColor(odd))
          try {
            val shader = surface.makeImageSnapshot
                                .makeShader(FilterTileMode.REPEAT, FilterTileMode.REPEAT, Matrix33.makeScale(1f / dpi))
            try {
              shaderStrokeOdd = Paint().setShader(shader)
            } finally {
              if (shader != null) shader.close()
            }
          }
        } finally {
          if (surface != null) surface.close()
        }
      }
    }
    if (shaderStrokeEven == null) {
      try {
        val surface = Surface.makeRaster(ImageInfo.makeN32Premul(Math.ceil(4 * dpi).toInt, Math.ceil(3 * dpi).toInt))
        try {
          val c = surface.getCanvas
          c.scale(dpi, dpi)
          c.translate(0, 1.5f)
          c.drawPath(element, Paint().setColor(even))
          try {
            val shader = surface.makeImageSnapshot
                                .makeShader(FilterTileMode.REPEAT, FilterTileMode.REPEAT, Matrix33.makeScale(1f / dpi))
            try {
              shaderStrokeEven = Paint().setShader(shader)
            } finally {
              if (shader != null) shader.close()
            }
          }
        } finally {
          if (surface != null) surface.close()
        }
      }
    }
    var y = 20
    while (y < height - 20) {
      var x = 20
      while (x < width - 20) {
        val right = Math.min(width - 20, x + 47)
        val odd = (x + y) % 2 == 1
        if ("Path Effect" == _variants(_variantIdx)) {
          drawPathEffect(canvas, x, right, y, odd)
        } else if ("Path" == _variants(_variantIdx)) {
          drawPath(canvas, x, right, y, odd)
        } else if ("Image" == _variants(_variantIdx)) drawImage(canvas, x, right, y, odd)
        x += 47
      }
      y += 17
    }
  }

  def drawPathEffect(canvas: Canvas, left: Float, right: Float, y: Float, odd: Boolean): Unit = {
    canvas.save
    canvas.clipRect(Rect.makeLTRB(left, y, right, y + 3))
    canvas.drawLine(left, y + 1.5f, right, y + 1.5f, if (odd) {
      effectStrokeOdd
    } else {
      effectStrokeEven
    })
    canvas.restore
  }

  def drawPath(canvas: Canvas, left: Float, right: Float, y: Float, odd: Boolean): Unit = {
    canvas.save
    val offset = left.toInt / 4 * 4
    canvas.translate(offset, y)
    val segments = Math.ceil((right - offset + 4) / 2).toInt
    val coords = new Array[Float](segments * 2)
    for (i <- 0 until segments) {
      coords(i * 2) = i * 2
      coords(i * 2 + 1) = if (i % 2 == 0) {
        0
      } else {
        3
      }
    }
    canvas.clipRect(Rect.makeLTRB(left - offset, -2, right - offset, 5))
    canvas.drawPolygon(coords, if (odd) {
      pathStrokeOdd
    } else {
      pathStrokeEven
    })
    canvas.restore
  }

  def drawImage(canvas: Canvas, left: Float, right: Float, y: Float, odd: Boolean): Unit = {
    canvas.save
    val offset = left.toInt / 4 * 4
    canvas.translate(offset, y)
    canvas.drawRect(Rect.makeLTRB(left - offset, 0, right - offset, 3), if (odd) {
      shaderStrokeOdd
    } else {
      shaderStrokeEven
    })
    canvas.restore
  }
}