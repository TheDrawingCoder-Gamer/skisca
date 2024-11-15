package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.IOException
import java.nio.file.*
import java.util.*

class ImageBenchScene @throws[IOException]
  extends Scene {
  private[scenes] var sprites: Array[Image] = new Array[Image](14)
  for (i <- 0 until 14) {
    sprites(i) = Image.makeDeferredFromEncodedBytes(Files
      .readAllBytes(java.nio.file.Path.of(Scene.file("images/sprites/bunny" + i + ".png"))))
  }
  _variants = Array[String]("One image", "14 images")

  private[scenes] var all: Image = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(java.nio.file.Path.of(Scene.file("images/sprites/all.png"))))
  private[scenes] var xs: Array[Float] = null
  private[scenes] var ys: Array[Float] = null
  private[scenes] var dx: Array[Float] = null
  private[scenes] var dy: Array[Float] = null
  private[scenes] var phases: Array[Int] = null
  private[scenes] var lastPhaseChange: Long = 0

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    if (xs == null) {
      xs = new Array[Float](50000)
      ys = new Array[Float](50000)
      dx = new Array[Float](50000)
      dy = new Array[Float](50000)
      phases = new Array[Int](50000)
      val random = new Random
      for (i <- 0 until 50000) {
        xs(i) = random.nextFloat * (width - 32)
        ys(i) = random.nextFloat * (height - 32)
        dx(i) = (random.nextFloat - 0.5f) * 6f
        dy(i) = (random.nextFloat - 0.5f) * 6f
        phases(i) = random.nextInt(4)
      }
    }
    val updatePhase = System.currentTimeMillis - lastPhaseChange > 300
    if (updatePhase) lastPhaseChange = System.currentTimeMillis
    for (i <- 0 until 50000) {
      if ("One image" == _variants(_variantIdx)) {
        val image = i % 14
        canvas.drawImageRect(all, Rect.makeXYWH(32 * phases(i), image * 32, 32, 32), Rect
          .makeXYWH(xs(i), ys(i), 32, 32), SamplingMode.DEFAULT, null, true)
      }
      else {
        val image = Math.ceil(14 * i / 50000).toInt
        canvas.drawImageRect(sprites(image), Rect.makeXYWH(32 * phases(i), 0, 32, 32), Rect
          .makeXYWH(xs(i), ys(i), 32 , 32), SamplingMode.DEFAULT, null, true)
      }
      if (updatePhase) phases(i) = (phases(i) + 1) % 4
      if (xs(i) + dx(i) + 32 >= width && dx(i) > 0) dx(i) = -dx(i)
      if (xs(i) + dx(i) <= 0 && dx(i) < 0) dx(i) = -dx(i)
      xs(i) = xs(i) + dx(i)
      if (ys(i) + dy(i) + 32 >= height && dy(i) > 0) dy(i) = -dy(i)
      if (ys(i) + dy(i) <= 0 && dy(i) < 0) dy(i) = -dy(i)
      ys(i) = ys(i) + dy(i)
    }
  }
}