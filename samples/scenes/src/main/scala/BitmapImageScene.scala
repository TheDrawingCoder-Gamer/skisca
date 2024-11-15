package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.*
import java.nio.*
import java.nio.file.{Path, *}
import java.util.*
import java.util.function.*

object BitmapImageScene {
  def luminocity(colorType: ColorType, color: Int): Float = {
    Color.getR(color) + Color.getG(color) + Color.getB(color)
    // return colorType.getR(color) + colorType.getG(color) + colorType.getB(color);
  }
}

class BitmapImageScene extends Scene {
  final var image: Image = try {
    Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/IMG_7098.jpeg"))))
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }
  
  var x = 0
  var y = 0

  def advance(canvas: Canvas, width: Int): Unit = {
    canvas.restore
    x += 220
    if (x + 220 >= width) {
      x = 20
      y += 240
    }
    canvas.save
    canvas.translate(x, y)
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.save
    canvas.translate(20, 20)
    x = 20
    y = 20
    // Image
    canvas.drawImageRect(image, Rect.makeXYWH(0, 0, 200, 200))
    canvas.drawString("Image", 0, 220, Scene.inter13, Scene.blackFill)
    advance(canvas, width)
    // Bitmap + Image.readPixels
    {
      val bitmap = Bitmap()
      try {
        bitmap.allocPixels(ImageInfo.makeS32(400, 400, ColorAlphaType.OPAQUE))
        image.readPixels(bitmap)
        try {
          val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(0, 0, 200, 200))
          } finally {
            if (image != null) image.close()
          }
        }
        canvas.drawString("Image.readPixels", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    // Bitmap + Image.readPixels(50, 50)
    {
      val bitmap = Bitmap()
      try {
        bitmap.allocPixels(new ImageInfo(300, 300, ColorType.RGBA_8888, ColorAlphaType.OPAQUE))
        image.readPixels(bitmap, 50, 50)
        try {
          val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(25, 25, 150, 150))
          } finally {
            if (image != null) image.close()
          }
        }
        canvas.drawString("Image.readPixels(50, 50)", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    var pixels: Array[Byte] = null
    var info: ImageInfo = null
    // Bitmap readPixels/installPixels
    {
      val bitmap = Bitmap.makeFromImage(image)
      try {
        info = bitmap.getImageInfo
        pixels = bitmap.readPixels
        pixelSorting(canvas, ByteBuffer.wrap(pixels), info)
        bitmap.installPixels(pixels)
        try {
          val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(0, 0, 200, 200))
          } finally {
            if (image != null) image.close()
          }
        }
        canvas.drawString("Bitmap.readPixels/installPixels", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    // Bitmap peekPixels
    {
      val bitmap = Bitmap.makeFromImage(image)
      try {
        pixelSorting(canvas, bitmap.peekPixels, bitmap.getImageInfo)
        try {
          val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(0, 0, 200, 200))
          } finally {
            if (image != null) image.close()
          }
        }
        canvas.drawString("Bitmap.peekPixels", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (bitmap != null) bitmap.close()
      }
    }
    // Image.makeRasterFromBytes
    {
      val imageFromPixels = Image.makeRasterFromBytes(info, pixels, info.getMinRowBytes)
      try {
        canvas.drawImageRect(imageFromPixels, Rect.makeXYWH(0, 0, 200, 200))
        canvas.drawString("Image.makeRasterFromBytes", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (imageFromPixels != null) imageFromPixels.close()
      }
    }
    // Image.makeRasterFromData
    {
      val imageFromData = Image.makeRasterFromData(info, Data.makeFromBytes(pixels), info.getMinRowBytes)
      try {
        canvas.drawImageRect(imageFromData, Rect.makeXYWH(0, 0, 200, 200))
        canvas.drawString("Image.makeRasterFromBytes + Data", 0, 220, Scene.inter13, Scene.blackFill)
        advance(canvas, width)
      } finally {
        if (imageFromData != null) imageFromData.close()
      }
    }
  }

  def pixelSorting(canvas: Canvas, pixels: ByteBuffer, info: ImageInfo): Unit = {
    val threshold = 100 + Scene.phase * 100
    // var threshold = 0.4f + phase() * 0.4f;
    val colorType = info.getColorType
    val cmp: java.util.Comparator[Integer] = (a: Integer, b: Integer) => java.lang.Float
      .compare(BitmapImageScene.luminocity(colorType, a), BitmapImageScene.luminocity(colorType, b))
    var x = 0
    while (x < info.width) {
      // read pixels
      val column = new Array[Integer](info.height)
      for (y <- 0 until info.height) {
        column(y) = pixels.getInt((y * info.width + x) * info.getBytesPerPixel) // Assume RGBA_8888
      }
      // sort pixels
      var lastIdx = 0
      for (y <- 0 until info.height - 1) {
        if (Math.abs(BitmapImageScene.luminocity(colorType, column(y)) - BitmapImageScene
          .luminocity(colorType, column(y + 1))) > threshold) {
          java.util.Arrays.parallelSort(column, lastIdx, y, cmp)
          lastIdx = y
        }
      }
      java.util.Arrays.parallelSort(column, lastIdx, info.height, cmp)
      // write pixels
      for (y <- 0 until info.height) {
        pixels.putInt((y * info.width + x) * info.getBytesPerPixel, column(y))
      }
      x += 1
    }
  }
}