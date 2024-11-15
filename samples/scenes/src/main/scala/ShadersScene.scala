package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class ShadersScene extends Scene {
  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(20, 20)
    drawColors(canvas)
    drawShaders(canvas)
    drawGradients(canvas)
    drawLinearCS(canvas)
    drawBlending(canvas)
  }

  private def drawColors(canvas: Canvas): Unit = {
    canvas.save
    {
      val fill = new Paint
      try {
        fill.setColor(0xFFFF0000)
        canvas.drawRect(Rect.makeXYWH(0, 0, 60, 20), fill)
        fill.setColor(0xFF00FF00)
        canvas.drawRect(Rect.makeXYWH(0, 20, 60, 20), fill)
        fill.setColor(0xFF0000FF)
        canvas.drawRect(Rect.makeXYWH(0, 40, 60, 20), fill)
        canvas.translate(70, 0)
        fill.setColor4f(new Color4f(1, 0, 0, 1))
        canvas.drawRect(Rect.makeXYWH(0, 0, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 1, 0, 1))
        canvas.drawRect(Rect.makeXYWH(0, 20, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 0, 1, 1))
        canvas.drawRect(Rect.makeXYWH(0, 40, 60, 20), fill)
        canvas.translate(70, 0)
        fill.setColor4f(new Color4f(1, 0, 0, 1), ColorSpace.getSRGB)
        canvas.drawRect(Rect.makeXYWH(0, 0, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 1, 0, 1), ColorSpace.getSRGB)
        canvas.drawRect(Rect.makeXYWH(0, 20, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 0, 1, 1), ColorSpace.getSRGB)
        canvas.drawRect(Rect.makeXYWH(0, 40, 60, 20), fill)
        canvas.translate(70, 0)
        fill.setColor4f(new Color4f(1, 0, 0, 1), ColorSpace.getDisplayP3)
        canvas.drawRect(Rect.makeXYWH(0, 0, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 1, 0, 1), ColorSpace.getDisplayP3)
        canvas.drawRect(Rect.makeXYWH(0, 20, 60, 20), fill)
        fill.setColor4f(new Color4f(0, 0, 1, 1), ColorSpace.getDisplayP3)
        canvas.drawRect(Rect.makeXYWH(0, 40, 60, 20), fill)
        canvas.translate(70, 0)
      } finally {
        if (fill != null) fill.close()
      }
    }
    canvas.restore
    canvas.translate(0, 70)
  }

  private def drawShaders(canvas: Canvas): Unit = {
    canvas.save
    var percent = Math.abs((System.currentTimeMillis % 3000) / 10f - 150f) - 25f
    percent = Math.max(0f, Math.min(100f, percent)).round
    val shaders = Array[Shader](Shader.makeEmpty, Shader.makeColor(0xFF247ba0), Shader
      .makeColor(new Color4f(0.5f, 0.5f, 0.5f), ColorSpace.getSRGBLinear), Shader
      .makeBlend(BlendMode.SRC_OVER, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)), Shader
      .makeBlend(BlendMode.SCREEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)), Shader
      .makeBlend(BlendMode.OVERLAY, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)), Shader
      .makeBlend(BlendMode.DARKEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)), Shader
      .makeBlend(BlendMode.LIGHTEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)), Shader
      .makeLinearGradient(0, 0, 60, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd))
      .makeWithColorFilter(ColorFilter.makeBlend(0xFFCC3333, BlendMode.SCREEN)))
    {
      val fill = new Paint
      try {
        for (sh <- shaders) {
          fill.setShader(sh)
          canvas.drawRect(Rect.makeXYWH(0, 0, 60, 60), fill)
          canvas.translate(70, 0)
          sh.close()
        }
      } finally {
        if (fill != null) fill.close()
      }
    }
    canvas.restore
    canvas.translate(0, 70)
  }

  private def drawGradients(canvas: Canvas): Unit = {
    canvas.save
    val shaders = Array[Shader](Shader.makeLinearGradient(0, 0, 60, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeLinearGradient(20, 0, 40, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeLinearGradient(20, 0, 40, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd), null, GradientStyle.DEFAULT
                                                                                               .copy(tileMode = FilterTileMode
                                                                                                 .REPEAT)), Shader
      .makeLinearGradient(20, 0, 40, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd), null, GradientStyle.DEFAULT
                                                                                               .copy(tileMode = FilterTileMode
                                                                                                 .MIRROR)), Shader
      .makeLinearGradient(20, 0, 40, 0, Array[Int](0xFF247ba0, 0xFFf3ffbd), null, GradientStyle.DEFAULT
                                                                                               .copy(tileMode = FilterTileMode
                                                                                                 .DECAL)), Shader
      .makeLinearGradient(0, 0, 0, 60, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeLinearGradient(0, 0, 0, 60, Array[Int](0xFF247ba0, 0xFFf3ffbd), null, GradientStyle.DEFAULT
                                                                                              .copy(localMatrix = Matrix33
                                                                                                .makeRotate(45))), Shader
      .makeLinearGradient(0, 0, 60, 60, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeLinearGradient(0, 0, 60, 0, Array[Int](0xFF247ba0, 0x00000000)), Shader
      .makeLinearGradient(0, 0, 60, 0, Array[Int](0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf)), Shader
      .makeLinearGradient(0, 0, 60, 0, Array[Int](0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf), Array[Float](0f, 0.1f, 0.2f, 0.9f, 1f)), Shader
      .makeRadialGradient(30, 30, 30, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeRadialGradient(30, 30, 10, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeRadialGradient(30, 30, 10, Array[Int](0xFF247ba0, 0xFFf3ffbd), null, GradientStyle.DEFAULT
                                                                                             .copy(tileMode = FilterTileMode
                                                                                               .REPEAT)), Shader
      .makeTwoPointConicalGradient(20, 20, 10, 40, 40, 40, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeSweepGradient(30, 30, Array[Int](0xFF247ba0, 0xFFf3ffbd)), Shader
      .makeSweepGradient(30, 30, 45, 315, Array[Int](0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf), null, GradientStyle
        .DEFAULT.copy(tileMode = FilterTileMode.DECAL)))
    {
      val fill = new Paint
      try {
        for (sh <- shaders) {
          fill.setShader(sh)
          canvas.drawRect(Rect.makeXYWH(0, 0, 60, 60), fill)
          canvas.translate(70, 0)
          sh.close()
        }
      } finally {
        if (fill != null) fill.close()
      }
    }
    {
      val sh = Shader.makeSweepGradient(30, 30, Array[Int](0xFFFF00FF, 0xFF00FFFF, 0xFFFFFF00, 0xFFFF00FF))
      val stroke = new Paint().setShader(sh).setMode(PaintMode.STROKE).setStrokeWidth(10)
      try {
        canvas.drawCircle(30, 30, 30, stroke)
        canvas.translate(70, 0)
      } finally {
        if (sh != null) sh.close()
        if (stroke != null) stroke.close()
      }
    }
    {
      val p1 = new Path().lineTo(30.1f, 0).lineTo(0, 32.5f).closePath
      val s1 = Shader
        .makeLinearGradient(0, 32.5f, 30.1f, 0, Array[Int](0xFF0095D5, 0xFF3C83DC, 0xFF6D74E1, 0xFF806EE3), Array[Float](0.1183f, 0.4178f, 0.6962f, 0.8333f))
      val f1 = new Paint().setShader(s1)
      val p2 = new Path().moveTo(30.1f, 0).lineTo(0, 31.7f).lineTo(0, 60).lineTo(30.1f, 29.9f).lineTo(60, 0).closePath
      val s2 = Shader
        .makeLinearGradient(0, 60, 60, 0, Array[Int](0xFFC757BC, 0xFFD0609A, 0xFFE1725C, 0xFFEE7E2F, 0xFFF58613, 0xFFF88909), Array[Float](0.1075f, 0.2138f, 0.4254f, 0.6048f, 0.743f, 0.8232f))
      val f2 = new Paint().setShader(s2)
      val p3 = new Path().moveTo(0, 60).lineTo(30.1f, 29.9f).lineTo(60, 60).closePath
      val s3 = Shader
        .makeLinearGradient(0, 60, 30.1f, 29.9f, Array[Int](0xFF0095D5, 0xFF238AD9, 0xFF557BDE, 0xFF7472E2, 0xFF806EE3), Array[Float](0f, 0.3f, 0.62f, 0.8643f, 1f))
      val f3 = new Paint().setShader(s3)
      try {
        canvas.drawPath(p1, f1)
        canvas.drawPath(p2, f2)
        canvas.drawPath(p3, f3)
        canvas.translate(70, 0)
      } finally {
        if (p1 != null) p1.close()
        if (s1 != null) s1.close()
        if (f1 != null) f1.close()
        if (p2 != null) p2.close()
        if (s2 != null) s2.close()
        if (f2 != null) f2.close()
        if (p3 != null) p3.close()
        if (s3 != null) s3.close()
        if (f3 != null) f3.close()
      }
    }
    canvas.restore
    canvas.translate(0, 70)
  }

  private def linearCS(x0: Float, y0: Float, x1: Float, y1: Float, colorFrom: Int, colorTo: Int, cs: ColorSpace, steps: Int) = {
    val from = ColorSpace.getSRGB.convert(cs, Color4f(colorFrom))
    val to = ColorSpace.getSRGB.convert(cs, Color4f(colorTo))
    val colors = new Array[Color4f](steps + 1)
    val pos = new Array[Float](steps + 1)
    for (i <- 0 until steps + 1) {
      pos(i) = i.toFloat / steps
      colors(i) = from.makeLerp(to, pos(i))
    }
    Shader.makeLinearGradient(x0, y0, x1, y1, colors, cs, pos, GradientStyle.DEFAULT)
  }

  private def drawLinearCS(canvas: Canvas): Unit = {
    for (cs <- Array[ColorSpace](null, ColorSpace.getSRGB, ColorSpace.getSRGBLinear)) {
      canvas.save
      val colors = Array[Array[Int]](Array[Int](0xFFFF0000, 0xFF00FF00), Array[Int](0xFFFF0000, 0xFF0000FF), Array[Int](0xFFFF0000, 0xFF00FFFF), Array[Int](0xFF0000FF, 0xFF00FF00), Array[Int](0xFF00FFFF, 0xFFFF00FF), Array[Int](0xFF00FF00, 0xFFFF00FF), Array[Int](0xFF000000, 0xFFFFFFFF), Array[Int](0xFF555555, 0xFFAAAAAA))
      try {
        val fill = new Paint
        try {
          for (pair <- colors) {
            try {
              val shader = linearCS(31, 0, 129, 0, pair(0), pair(1), cs, 10)
              try {
                fill.setColor(pair(0))
                canvas.drawRect(Rect.makeXYWH(0, 0, 30, 30), fill)
                fill.setShader(shader)
                canvas.drawRect(Rect.makeXYWH(31, 0, 98, 30), fill)
                fill.setShader(null)
                fill.setColor(pair(1))
                canvas.drawRect(Rect.makeXYWH(130, 0, 30, 30), fill)
                canvas.translate(170, 0)
              } finally {
                if (shader != null) shader.close()
              }
            }
          }
        } finally {
          if (fill != null) fill.close()
        }
      }
      canvas.restore
      canvas.translate(0, 40)
    }
  }

  private def drawBlending(canvas: Canvas): Unit = {
    val pink = 0xFFFF0080
    val purple = 0xFF8000FF
    val orange = 0xFFFFA500
    val transparentBlack = 0x00000000
    try {
      val fill = new Paint
      val gr1 = Shader.makeLinearGradient(0, 0, 0, 100, Array[Int](pink, purple))
      val gr2 = Shader.makeLinearGradient(0, 0, 400, 0, Array[Int](orange, transparentBlack))
      val gr3 = Shader
        .makeLinearGradient(0, 0, 400, 0, Array[Int](orange, transparentBlack), null, GradientStyle.DEFAULT
                                                                                                   .copy(premul = false))
      val gr4 = Shader.makeBlend(BlendMode.SRC_OVER, gr1, gr2)
      try {
        canvas.save
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setColor(pink))
        canvas.translate(90, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 400, 80), fill.setShader(gr1))
        canvas.translate(410, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setShader(null).setColor(purple))
        canvas.restore
        canvas.translate(0, 90)
        canvas.save
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setColor(orange))
        canvas.translate(90, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 400, 80), fill.setShader(gr2))
        canvas.translate(410, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setShader(null).setColor(transparentBlack))
        canvas.restore
        canvas.translate(0, 90)
        canvas.save
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setColor(orange))
        canvas.translate(90, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 400, 80), fill.setShader(gr3))
        canvas.translate(410, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setShader(null).setColor(transparentBlack))
        canvas.restore
        canvas.translate(0, 90)
        canvas.save
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setColor(orange))
        canvas.translate(90, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 400, 80), fill.setShader(gr4))
        canvas.translate(410, 0)
        canvas.drawRect(Rect.makeXYWH(0, 0, 80, 80), fill.setShader(gr1))
        canvas.restore
      } finally {
        if (fill != null) fill.close()
        if (gr1 != null) gr1.close()
        if (gr2 != null) gr2.close()
        if (gr3 != null) gr3.close()
        if (gr4 != null) gr4.close()
      }
    }
  }
}