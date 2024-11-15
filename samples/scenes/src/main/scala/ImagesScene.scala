package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.IOException
import java.nio.file.{Files, Path}
import scala.util.Using

class ImagesScene extends Scene {
  final protected var circus: Image = null
  final protected var circusCropped: Image = null
  final protected var cloud: Image = null
  final protected var ducks: Image = null
  final protected var tests: Array[Image] = null
  try {
    circus = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/circus.jpg"))))
    circusCropped = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/circus.jpg"))))
    cloud = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/cloud.png"))))
    ducks = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/ducks.jpg"))))
    tests = Array[Image](Image
      .makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/icc-v2-gbr.jpg")))), Image
      .makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/purple-displayprofile.png")))), Image
      .makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/wide-gamut.png")))), Image
      .makeDeferredFromEncodedBytes(Files
        .readAllBytes(Path.of(Scene.file("images/wide_gamut_yellow_224_224_64.jpeg")))), Image
      .makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/webkit_logo_p3.png")))))
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }


  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(30, 30)
    canvas.save
    canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 160, 160), null, true)
    canvas.translate(170, 0)
    canvas.drawImageRect(circusCropped, Rect.makeXYWH(0, 0, 320, 320), Rect.makeXYWH(0, 0, 160, 160), null, true)
    canvas.translate(170, 0)
    canvas.drawImageRect(cloud, Rect.makeXYWH(0, 0, 666, 456), Rect.makeXYWH(0, 0, 160, 110), null, true)
    canvas.drawImageRect(cloud, Rect.makeXYWH(0, 0, 666, 456), Rect.makeXYWH(0, 50, 160, 110), null, true)
    canvas.translate(170, 0)
    canvas.drawImageRect(ducks, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 80, 160), null, true)
    canvas.translate(90, 0)
    canvas.drawImageRect(ducks, Rect.makeXYWH(0, 0, 640, 640), Rect.makeXYWH(0, 0, 160, 80), null, true)
    canvas.translate(170, 0)
    canvas.restore
    canvas.translate(0, 170)
    canvas.save
    for (pair <- Pair.arrayOf("None/None", SamplingMode.DEFAULT, "Linear/None", SamplingMode
      .LINEAR, "Linear/Nearest", new FilterMipmap(FilterMode.LINEAR, MipmapMode
      .NEAREST), "Linear/Linear", new FilterMipmap(FilterMode.LINEAR, MipmapMode.LINEAR), "Mitchell", SamplingMode
      .MITCHELL, "Catmull-Rom", SamplingMode.CATMULL_ROM, "Anisotropic(10)", new SamplingModeAnisotropic(10))) {
      val name = pair.getFirst
      val mode = pair.getSecond
      canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 320, 640), Rect.makeXYWH(0, 0, 80, 160), mode, null, false)
      canvas.drawImageRect(circus, Rect.makeXYWH(200, 220, 60, 100), Rect.makeXYWH(80, 0, 80, 160), mode, null, false)
      canvas.drawString(name, 0, 175, Scene.inter13, Scene.blackFill)
      canvas.translate(170, 0)
    }
    canvas.restore
    canvas.translate(0, 200)
    canvas.save
    {
      val paint = Paint().setBlendMode(BlendMode.SCREEN)
      try {
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160))
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint)
      } finally {
        if (paint != null) paint.close()
      }
    }
    canvas.translate(170, 0)
    {
      val paint = Paint().setBlendMode(BlendMode.OVERLAY)
      try {
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160))
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint)
      } finally {
        if (paint != null) paint.close()
      }
    }
    canvas.translate(170, 0)
    {
      val blur = ImageFilter.makeBlur(5, 5, FilterTileMode.DECAL)
      val paint = Paint().setImageFilter(blur)
      try {
        canvas.drawImageRect(circus, Rect.makeXYWH(0, 0, 160, 160), paint)
      } finally {
        if (paint != null) paint.close()
        if (blur != null) blur.close()
      }
    }
    canvas.translate(170, 0)
    canvas.restore
    canvas.translate(0, 170)
    canvas.save
    var maxH = 0
    for (image <- tests) {
      maxH = Math.max(maxH, image.getHeight)
      canvas.drawImage(image, 0, 0)
      canvas.translate(image.getWidth + 10, 0)
    }
    canvas.restore
    canvas.translate(0, maxH + 10)
  }
}