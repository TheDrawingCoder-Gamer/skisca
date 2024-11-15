package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.*
import java.util.*
import java.util.stream.*
import scala.collection.mutable as mut
import scala.util.boundary

object CodecScene {
  private[scenes] class Animation {
    private[scenes] var codec: Codec = null
    private[scenes] var bitmap: Bitmap = null
    private[scenes] var prevFrame = -1
    private[scenes] var durations: Array[Int] = null
    private[scenes] var totalDuration = 0L
  }
}

class CodecScene extends Scene {
  private[scenes] val stroke = Paint().setColor(0x80CC3333).setMode(PaintMode.STROKE).setStrokeWidth(1)
  private[scenes] val formats = new mut.ArrayBuffer[Pair[String, Bitmap]]
  private[scenes] val orientations = new mut.ArrayBuffer[Pair[String, Codec]]
  private[scenes] val animations = new mut.ArrayBuffer[Pair[String, CodecScene.Animation]]
  private[scenes] var x: Float = .0
  private[scenes] var y: Float = .0
  private[scenes] val rowH = 100
  private[scenes] val columnW = 100
  val supportedFormats = mut.ListBuffer[String]("bmp.bmp", "gif.gif",  "jpeg.jpg", "png.png", "webp_lossy.webp", "webp_loseless.webp", "favicon.ico")
  for (file <- supportedFormats) {
    {
      val codec = Codec.makeFromData(Data.makeFromFileName(Scene.file("images/codecs/" + file)))
      try {
        formats.append(new Pair(file + "\n" + codec.getEncodedImageFormat, codec.readPixels.setImmutable))
      } catch {
        case e: Exception =>
          formats.append(new Pair(file + "\n" + e.getMessage, null))
      } finally {
        if (codec != null) codec.close()
      }
    }
  }
  for (file <- Array[String]("orient_tl.jpg", "orient_tr.jpg", "orient_br.jpg", "orient_bl.jpg", "orient_lt.jpg", "orient_lb.jpg", "orient_rb.jpg", "orient_rt.jpg")) {
    orientations.append(new Pair(file, Codec.makeFromData(Data.makeFromFileName(Scene.file("images/codecs/" + file)))))
  }
  for (file <- Array[String]("animated.gif", "animated.webp")) {
    val animation = new CodecScene.Animation
    animation.codec = Codec.makeFromData(Data.makeFromFileName(Scene.file("images/codecs/" + file)))
    animation.bitmap = Bitmap()
    animation.bitmap.allocPixels(animation.codec.getImageInfo)
    animation.durations = animation.codec.getFramesInfo.map(_.duration)
    animation.totalDuration = animation.durations.sum
    animations.append(new Pair(file, animation))
  }


  def drawOne(canvas: Canvas, width: Int, s: String, draw: Runnable): Unit = {
    if (x + columnW >= width) {
      x = 20
      y += rowH + 60
    }
    canvas.save
    canvas.translate(x, y)
    draw.run()
    val lines = s.lines.toArray
    for (i <- 0 until lines.length) {
      canvas.drawString(lines(i).asInstanceOf[String], 0, rowH + 20 + i * 20, Scene.inter13, Scene.blackFill)
    }
    canvas.restore
    x += columnW + 20
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    x = 20
    y = 20
    for (pair <- formats) {
      val label = pair.getFirst
      val bitmap = pair.getSecond
      if (bitmap == null) {
        drawOne(canvas, width, label, () => {
          canvas.drawRect(Rect.makeXYWH(0, 0, columnW, rowH), stroke)
          canvas.drawLine(0, 0, columnW, rowH, stroke)
          canvas.drawLine(0, rowH, columnW, 0, stroke)
        })
      } else {
        drawOne(canvas, width, label, () => {
          try {
            val image = Image.makeRasterFromBitmap(bitmap)
            try {
              canvas.drawImageRect(image, Rect.makeXYWH(0, 0, columnW, rowH))
            } finally {
              if (image != null) image.close()
            }
          }
        })
      }
    }
    x = 20
    y += rowH + 60
    for (pair <- orientations) {
      val label = pair.getFirst
      val codec = pair.getSecond
      val origin = codec.getEncodedOrigin
      try {
        val bitmap = codec.readPixels.setImmutable
        try {
          val bitmapWidth = if (origin.swapsWidthHeight) {
            codec.getHeight
          } else {
            codec.getWidth
          }
          val bitmapHeight = if (origin.swapsWidthHeight) {
            codec.getWidth
          } else {
            codec.getHeight
          }
          drawOne(canvas, width, label + "\n" + codec.getEncodedImageFormat, () => {
            canvas.save
            canvas.concat(origin.toMatrix(bitmapWidth, bitmapHeight))
            try {
              val image = Image.makeRasterFromBitmap(bitmap)
              try {
                canvas.drawImageRect(image, Rect.makeXYWH(0, 0, codec.getWidth, codec.getHeight))
              } finally {
                if (image != null) image.close()
              }
            }
            canvas.restore
            canvas.drawRect(Rect.makeXYWH(0, 0, bitmapWidth, bitmapHeight), stroke)
          })
        } finally {
          if (bitmap != null) bitmap.close()
        }
      }
    }
    x = 20
    y += rowH + 60
    for (pair <- animations) {
      val label = pair.getFirst
      val animation = pair.getSecond
      val codec = animation.codec
      var duration = 0
      var frame = 0
      val now = System.currentTimeMillis % animation.totalDuration
      boundary {
        while (frame < animation.durations.length) {
          duration += animation.durations(frame)
          if (duration >= now) boundary.break()
          frame += 1
        }
      }
      val finalFrame = frame
      drawOne(canvas, width, label + "\n" + codec.getEncodedImageFormat, () => {
        try {
          val bitmap = Bitmap()
          try {
            bitmap.allocPixels(codec.getImageInfo)
            codec.readPixels(bitmap, finalFrame)
            try {
              val image = Image.makeRasterFromBitmap(bitmap.setImmutable)
              try {
                canvas.drawImageRect(image, Rect.makeXYWH(0, 0, columnW, rowH))
              } finally {
                if (image != null) image.close()
              }
            }
          } finally {
            if (bitmap != null) bitmap.close()
          }
        }
      })
      drawOne(canvas, width, label + "\n" + codec.getEncodedImageFormat + " + priorFrame", () => {
        codec.readPixels(animation.bitmap, finalFrame, animation.prevFrame)
        try {
          val image = Image.makeRasterFromBitmap(animation.bitmap)
          try {
            canvas.drawImageRect(image, Rect.makeXYWH(0, 0, columnW, rowH))
          } finally {
            if (image != null) image.close()
          }
        }
      })
    }
  }
}