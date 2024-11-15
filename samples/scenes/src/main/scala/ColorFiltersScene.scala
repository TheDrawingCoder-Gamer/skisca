package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.IOException
import java.nio.file.{Files, Path}

class ColorFiltersScene extends Scene {
  private[scenes] var image: Image = try {
    Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/circus.jpg"))))
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }
  

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(30, 30)
    val tablePosterize = new Array[Byte](256)
    for (i <- 0 until 256) {
      tablePosterize(i) = (i & 0x80).toByte
    }
    val tableInv = new Array[Byte](256)
    for (i <- 0 until 256) {
      tableInv(i) = (255 - i).toByte
    }
    val filters = Array[Array[ColorFilter]](Array[ColorFilter](ColorFilter
      .makeBlend(0x80CC3333, BlendMode.SRC_OVER), ColorFilter.makeBlend(0xFFCC3333, BlendMode.SCREEN), ColorFilter
      .makeBlend(0xFFCC3333, BlendMode.OVERLAY), ColorFilter
      .makeComposed(ColorFilter.makeBlend(0x80CC3333, BlendMode.SRC_OVER), ColorFilter
        .makeBlend(0x803333CC, BlendMode.SRC_OVER)), ColorFilter
      .makeMatrix(new ColorMatrix(0.21f, 0.72f, 0.07f, 0, 0, 0.21f, 0.72f, 0.07f, 0, 0, 0.21f, 0.72f, 0.07f, 0, 0, 0, 0, 0, 1, 0)), ColorFilter
      .makeHSLAMatrix(new ColorMatrix(0, 0, 0, 0, Scene.phase, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0)), ColorFilter
      .makeLerp(ColorFilter.makeBlend(0x80CC3333, BlendMode.SRC_OVER), ColorFilter
        .makeBlend(0x803333CC, BlendMode.SRC_OVER), Scene.phase), ColorFilter
      .makeLighting(0x80CC3333, 0x803333CC)), Array[ColorFilter](ColorFilter
      .makeHighContrast(true, InversionMode.NO, 0), ColorFilter
      .makeHighContrast(false, InversionMode.NO, 0.5f), ColorFilter
      .makeHighContrast(false, InversionMode.NO, -0.5f), ColorFilter
      .makeHighContrast(false, InversionMode.BRIGHTNESS, 0), ColorFilter
      .makeHighContrast(false, InversionMode.LIGHTNESS, 0), ColorFilter
      .makeHighContrast(true, InversionMode.LIGHTNESS, 1), ColorFilter.makeTable(tablePosterize), ColorFilter
      .makeTableARGB(null, tableInv, null, null)), Array[ColorFilter](ColorFilter.getLinearToSRGBGamma, ColorFilter
      .getSRGBToLinearGamma, ColorFilter.getLuma))
    {
      val paint = Paint()
      try {
        for (i <- filters.indices) {
          canvas.save
          for (filter <- filters(i)) {
            paint.setColorFilter(filter)
            canvas.drawImageRect(image, Rect.makeWH(image.getWidth, image.getHeight), Rect
              .makeXYWH(0, 0, 160, 160), SamplingMode.LINEAR, paint, false)
            if (i < filters.length - 1) filter.close()
            canvas.translate(170, 0)
          }
          canvas.restore
          canvas.translate(0, 170)
        }
      } finally {
        if (paint != null) paint.close()
      }
    }
  }
}