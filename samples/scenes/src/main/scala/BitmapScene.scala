package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.util.*

class BitmapScene extends Scene {
  val random = new Random(0)
  private val shapes = new scala.collection.mutable.ArrayBuffer[Pair[Path, Integer]](100)
  for (i <- 0 until 100) {
    val path = Path()
    random.nextInt(4) match {
      case 0 => {
        path.addRect(Rect.makeXYWH(-0.5f, -0.5f, 1, 1))
      }
      case 1 => {
        path.addCircle(0, 0, 0.5f)
      }
      case 2 => {
        path.moveTo(0, -0.5f).lineTo(0.5f, 0.36f).lineTo(-0.5f, 0.36f).closePath
      }
      case 3 => {
        path.addRRect(RRect.makeXYWH(-0.6f, -0.4f, 1.2f, 0.8f, 0.4f))
      }
    }
    path.transform(Matrix33.makeRotate(random.nextInt(360)))
    path.transform(Matrix33.makeScale(10 + random.nextInt(250)))
    path.transform(Matrix33.makeTranslate(random.nextInt(1920), random.nextInt(1080)))
    val color = 0xFF000000 | random.nextInt(0xFFFFFF)
    shapes.append(new Pair[Path, Integer](path, color))
  }
  

  private def drawGray(canvas: Canvas, target: IRect, dpi: Float): Unit = {
    {
      val bitmap = Bitmap()
      try {
        bitmap.allocPixels(new ImageInfo((target.getWidth * dpi).toInt, (target.getHeight * dpi).toInt, ColorType
          .GRAY_8, ColorAlphaType.OPAQUE))
        if (canvas.readPixels(bitmap, ((target.getLeft - 10) * dpi).toInt, ((target.getTop - 10) * dpi).toInt)) {
          try {
            val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
            try {
              canvas.drawImageRect(image, target.toRect)
            } finally {
              if (image != null) image.close()
            }
          }
        }
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  private def drawBlur(canvas: Canvas, daTarget: IRect, screen: IRect, radius: Int, dpi: Float): Unit = {
    var target = daTarget.intersect(screen)
    if (target == null) return
    try {
      val bitmap = Bitmap()
      try {
        val radiusDPI = (radius * dpi).toInt
        val extended = IRect
          .makeLTRB(target.getLeft - radiusDPI, target.getTop - radiusDPI, target.getRight + radiusDPI, target
            .getBottom + radiusDPI).intersect(screen)
        bitmap.allocPixels(ImageInfo
          .makeS32((extended.getWidth * dpi).toInt, (extended.getHeight * dpi).toInt, ColorAlphaType.OPAQUE))
        if (canvas.readPixels(bitmap, (extended.getLeft * dpi).toInt, (extended.getTop * dpi).toInt)) {
          {
            val shader = bitmap.makeShader(Matrix33.makeScale(1 / dpi))
            val blur = ImageFilter.makeBlur(radius, radius, FilterTileMode.CLAMP)
            val fill = Paint().setShader(shader).setImageFilter(blur)
            try {
              canvas.save
              canvas.translate(extended.getLeft, extended.getTop)
              val targetRelative = target.offset(-extended.getLeft, -extended.getTop).toRect
              canvas.clipRect(targetRelative)
              canvas.drawRect(Rect.makeXYWH(0, 0, extended.getWidth, extended.getHeight), fill)
              canvas.restore
            } finally {
              if (shader != null) shader.close()
              if (blur != null) blur.close()
              if (fill != null) fill.close()
            }
          }
        }
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  private def drawBitmapCanvas(canvas: Canvas, target: IRect, dpi: Float): Unit = {
    {
      val bitmap = Bitmap()
      val path = Path()
      val stroke = Paint().setColor(0xFFe76f51).setMode(PaintMode.STROKE).setStrokeWidth(10)
      try {
        bitmap.allocPixels(ImageInfo
          .makeS32((target.getWidth * dpi).toInt, (target.getHeight * dpi).toInt, ColorAlphaType.PREMUL))
        bitmap.erase(0x80a8dadc)
        var color = bitmap.getColor(10, 10)
        assert(0x80a7d9db == color, "Expected 0x" + Integer.toString(0x80a7d9db, 16) + ", got 0x" + Integer
          .toString(color, 16))
        var alpha = bitmap.getAlphaf(10, 10)
        assert(Math.abs(0.5f - alpha) < 0.01, "Expected 0.5f, got " + alpha)
        bitmap.erase(0x00FFFFFF, IRect
          .makeXYWH(((target.getWidth / 2 - 10) * dpi).toInt, ((target.getHeight / 2 - 10) * dpi).toInt, (20 * dpi)
            .toInt, (20 * dpi).toInt))
        color = bitmap.getColor((target.getWidth / 2 * dpi).toInt, (target.getHeight / 2 * dpi).toInt)
        assert(0x00000000 == color, "Expected 0x" + Integer.toString(0x00000000, 16) + ", got 0x" + Integer
          .toString(color, 16))
        alpha = bitmap.getAlphaf((target.getWidth / 2 * dpi).toInt, (target.getHeight / 2 * dpi).toInt)
        assert(Math.abs(0f - alpha) < 0.01, "Expected 0f, got " + alpha)
        val canvas2 = Canvas(bitmap)
        canvas2.scale(dpi, dpi)
        path.moveTo(0, target.getHeight / 2).lineTo(target.getWidth / 2, target.getHeight / 2 - target.getWidth / 2)
            .lineTo(target.getWidth, target.getHeight / 2)
            .lineTo(target.getWidth / 2, target.getHeight / 2 + target.getWidth / 2).closePath
        canvas2.drawPath(path, stroke)
        {
          val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
          try {
            canvas.drawImageRect(image, target.toRect)
          } finally {
            if (image != null) image.close()
          }
        }
      } finally {
        if (bitmap != null) bitmap.close()
        if (path != null) path.close()
        if (stroke != null) stroke.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  def drawPixels(canvas: Canvas, daTarget: IRect, screen: IRect, dpi: Float): Unit = {
    val target = daTarget.intersect(screen)
    if (target == null) return
    if (target.getWidth <= 20 || target.getHeight <= 20) return
    val inner = IRect.makeXYWH(target.getLeft + 10, target.getTop + 10, target.getWidth - 20, target.getHeight - 20)
    if (inner == null) return
    try {
      val bitmap = Bitmap()
      try {
        val srcInfo = ImageInfo
          .makeS32((target.getWidth * dpi).toInt, (target.getHeight * dpi).toInt, ColorAlphaType.OPAQUE)
        bitmap.allocPixelsFlags(srcInfo, true)
        if (canvas.readPixels(bitmap, (target.getLeft * dpi).toInt, (target.getTop * dpi).toInt)) {
          val dstInfo = new ImageInfo((inner.getWidth * dpi).toInt, (inner.getHeight * dpi).toInt, ColorType
            .ARGB_4444, ColorAlphaType.UNPREMUL)
          val rowBytes = dstInfo.getMinRowBytes
          val pixels = bitmap.readPixels(dstInfo, rowBytes, (10 * dpi).toInt, (10 * dpi).toInt)
          val bpp = dstInfo.getBytesPerPixel
          for (i <- 0 until pixels.length / bpp / 2) {
            for (j <- 0 until bpp) {
              val i1 = i * bpp + j
              val i2 = (pixels.length / bpp - i - 1) * bpp + j
              val b = pixels(i1)
              pixels(i1) = pixels(i2)
              pixels(i2) = b
            }
          }
          bitmap.installPixels(dstInfo, pixels, rowBytes)
          bitmap.notifyPixelsChanged
          try {
            val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
            try {
              canvas.drawImageRect(image, inner.toRect)
            } finally {
              if (image != null) image.close()
            }
          }
        }
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  def drawSubset(canvas: Canvas, target: IRect, screen: IRect, dpi: Float): Unit = {
    try {
      val src = Bitmap()
      val dst = Bitmap()
      try {
        val srcInfo = ImageInfo
          .makeS32((target.getWidth * dpi).toInt, (target.getHeight * dpi).toInt, ColorAlphaType.OPAQUE)
        src.allocPixels(srcInfo)
        if (canvas.readPixels(src, (target.getLeft * dpi).toInt, (target.getTop * dpi).toInt)) {
          src.extractSubset(dst, Rect
            .makeXYWH(target.getWidth / 4 * dpi, target.getHeight / 4 * dpi, target.getWidth / 2 * dpi, target
              .getHeight / 2 * dpi).toIRect)
          try {
            val image = Image.makeRasterFromBitmap(dst.setImmutable)
            try {
              canvas.drawImageRect(image, target.toRect)
            } finally {
              if (image != null) image.close()
            }
          }
        }
      } finally {
        if (src != null) src.close()
        if (dst != null) dst.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  def drawPixelRef(canvas: Canvas, target: IRect, screen: IRect, dpi: Float): Unit = {
    try {
      val src = Bitmap()
      val dst = Bitmap()
      try {
        val info = ImageInfo
          .makeS32((target.getWidth * dpi).toInt, (target.getHeight * dpi).toInt, ColorAlphaType.OPAQUE)
        src.allocPixels(info)
        if (canvas.readPixels(src, (target.getLeft * dpi).toInt, (target.getTop * dpi).toInt)) {
          val pixelRef = src.getPixelRef
          dst.setImageInfo(info.withWidthHeight((target.getWidth / 2 * dpi).toInt, (target.getHeight / 2 * dpi).toInt))
          dst.setPixelRef(pixelRef, (target.getWidth / 4 * dpi).toInt, (target.getHeight / 4 * dpi).toInt)
          try {
            val image = Image.makeRasterFromBitmap(dst.setImmutable)
            try {
              canvas.drawImageRect(image, target.toRect)
            } finally {
              if (image != null) image.close()
            }
          }
        }
      } finally {
        if (src != null) src.close()
        if (dst != null) dst.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  def drawAlpha(canvas: Canvas, target: IRect, screen: IRect, dpi: Float): Unit = {
    try {
      val src = Bitmap()
      val dst = Bitmap()
      try {
        val srcInfo = ImageInfo
          .makeS32((target.getWidth * dpi).toInt, (target.getHeight / 2 * dpi).toInt, ColorAlphaType.UNPREMUL)
        src.allocPixels(srcInfo)
        val len = (target.getWidth / 2 * dpi).toInt
        for (x <- 0 until len) {
          val alpha = (255f * x / len).toInt
          val color = (alpha << 24) | (alpha << 16) | (0 << 8) | (255 - alpha)
          src.erase(color, IRect.makeXYWH(x, 0, 1, (target.getHeight / 2 * dpi).toInt))
        }
        try {
          val image = Image.makeRasterFromBitmap(src.setImmutable)
          try {
            canvas
              .drawImageRect(image, Rect.makeXYWH(target.getLeft, target.getTop, target.getWidth, target.getHeight / 2))
          } finally {
            if (image != null) image.close()
          }
        }
        if (src.extractAlpha(dst)) {
          try {
            val image = Image.makeRasterFromBitmap(dst.setImmutable)
            try {
              canvas.drawImageRect(image, Rect
                .makeXYWH(target.getLeft, target.getTop + target.getHeight / 2, target.getWidth, target.getHeight / 2))
            } finally {
              if (image != null) image.close()
            }
          }
        }
      } finally {
        if (src != null) src.close()
        if (dst != null) dst.close()
      }
    }
    try {
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(target.toRect, stroke)
      } finally {
        if (stroke != null) stroke.close()
      }
    }
  }

  def drawErase(canvas: Canvas, target: IRect, screen: IRect, dpi: Float): Unit = {
    val w = target.getWidth / 2
    val h = target.getHeight / 2
    val srcInfo = ImageInfo.makeS32((w * dpi).toInt, (h * dpi).toInt, ColorAlphaType.UNPREMUL)
    val red = new Color4f(1f, 0, 0)
    try {
      val src = Bitmap()
      try {
        src.allocPixels(srcInfo)
        src.erase(red)
        val color = src.getColor4f((w / 2 * dpi).toInt, (h / 2 * dpi).toInt)
        assert(new Color4f(1, 0, 0) == color, "Expected " + new Color4f(1, 0, 0) + ", got " + color)
        try {
          val image = Image.makeRasterFromBitmap(src.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(target.getLeft, target.getTop, w, h))
          } finally {
            if (image != null) image.close()
          }
        }
      } finally {
        if (src != null) src.close()
      }
    }
    try {
      val src = Bitmap()
      try {
        src.allocPixels(srcInfo)
        src.erase(red)
        val color = src.getColor4f((w / 2 * dpi).toInt, (h / 2 * dpi).toInt)
        assert(red == color, "Expected " + red + ", got " + color)
        try {
          val image = Image.makeRasterFromBitmap(src.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(target.getLeft + w, target.getTop, w, h))
          } finally {
            if (image != null) image.close()
          }
        }
      } finally {
        if (src != null) src.close()
      }
    }
    try {
      val src = Bitmap()
      try {
        src.allocPixels(srcInfo)
        src.erase(red, IRect
          .makeXYWH((w / 4 * dpi).toInt, (h / 4 * dpi).toInt, (w / 2 * dpi).toInt, (h / 2 * dpi).toInt))
        val color = src.getColor4f((w / 2 * dpi).toInt, (h / 2 * dpi).toInt)
        assert(new Color4f(1, 0, 0) == color, "Expected " + new Color4f(1, 0, 0) + ", got " + color)
        {
          val image = Image.makeRasterFromBitmap(src.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(target.getLeft, target.getTop + h, w, h))
          } finally {
            if (image != null) image.close()
          }
        }
      } finally {
        if (src != null) src.close()
      }
    }
    try {
      val src = Bitmap()
      try {
        src.allocPixels(srcInfo)
        src.erase(red, IRect
          .makeXYWH((w / 4 * dpi).toInt, (h / 4 * dpi).toInt, (w / 2 * dpi).toInt, (h / 2 * dpi).toInt))
        val color = src.getColor4f((w / 2 * dpi).toInt, (h / 2 * dpi).toInt)
        assert(red == color, "Expected " + red + ", got " + color)
        try {
          val image = Image.makeRasterFromBitmap(src.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(target.getLeft + w, target.getTop + h, w, h))
          } finally {
            if (image != null) image.close()
          }
        }
      } finally {
        if (src != null) src.close()
      }
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    assert(null != canvas.getBaseProps)
    assert(null != canvas.getTopProps)
    {
      val fill = Paint()
      val stroke = Paint().setColor(0xFFE5E5E5).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        for (tuple <- shapes) {
          fill.setColor(tuple.getSecond)
          canvas.drawPath(tuple.getFirst, fill)
        }
        val screen = IRect.makeXYWH(0, 0, width, height)
        val bw = 200
        val bh = 200
        var left = xpos - bw - 20 - bw / 2 - 10
        var top = ypos - bh - 20 - bh / 2 - 10
        drawGray(canvas, IRect.makeXYWH(left, top, bw, bh), dpi)
        left = xpos - bw / 2 - 10
        drawBlur(canvas, IRect.makeXYWH(left, top, bw, bh), screen, 20, dpi)
        left = xpos + 10 + bw / 2
        drawBitmapCanvas(canvas, IRect.makeXYWH(left, top, bw, bh), dpi)
        left = xpos - bw - 20 - bw / 2 - 10
        top = ypos - bh / 2 - 10
        drawPixels(canvas, IRect.makeXYWH(left, top, bw, bh), screen, dpi)
        left = xpos - bw / 2 - 10
        drawSubset(canvas, IRect.makeXYWH(left, top, bw, bh), screen, dpi)
        left = xpos + bw / 2 + 10
        drawPixelRef(canvas, IRect.makeXYWH(left, top, bw, bh), screen, dpi)
        left = xpos - bw - 20 - bw / 2 - 10
        top = ypos + bh / 2 + 10
        drawAlpha(canvas, IRect.makeXYWH(left, top, bw, bh), screen, dpi)
        left = xpos - bw / 2 - 10
        drawErase(canvas, IRect.makeXYWH(left, top, bw, bh), screen, dpi)
      } finally {
        if (fill != null) fill.close()
        if (stroke != null) stroke.close()
      }
    }
  }
}