package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*
import scala.collection.mutable as mut

import java.util.*

class BackdropScene extends Scene {
  private val shapes = new mut.ArrayBuffer[Pair[Path, Integer]](100)
  val random = new Random(0)
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
  

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    assert(null != canvas.getBaseProps)
    assert(null != canvas.getTopProps)
    try {
      val fill = Paint()
      try {
        for (tuple <- shapes) {
          fill.setColor(tuple.getSecond)
          canvas.drawPath(tuple.getFirst, fill)
        }
      }
      finally
      {
        if (fill != null) fill.close()
      }
    }
    val screen = Rect.makeXYWH(0, 0, width, height)
    // Just backdrop
    try {
      val backdrop = ImageFilter.makeBlur(20, 20, FilterTileMode.CLAMP)
      try {
        var rect = Rect.makeXYWH(xpos - 310, ypos - 310, 200, 200).intersect(screen)
        if (rect != null) {
          val layer = canvas.save
          canvas.clipRect(rect, true)
          canvas.saveLayer(new SaveLayerRec(rect, null, backdrop))
          canvas.drawColor(0x40FFFFFF)
          canvas.restoreToCount(layer)
        }
        rect = Rect.makeXYWH(xpos - 100, ypos - 310, 200, 200).intersect(screen)
        if (rect != null) {
          val layer = canvas.save
          canvas.clipRect(rect, true)
          canvas.saveLayer(new SaveLayerRec(rect, null, backdrop))
          canvas.translate(xpos - 100, ypos - 310)
          canvas.drawColor(0x80000000)
          try {
            val fill = Paint().setColor(0x80FFFFFF)
            try {
              canvas.drawRect(Rect.makeXYWH(50, 50, 100, 100), fill)
            } finally {
              if (fill != null) fill.close()
            }
          }
          canvas.restoreToCount(layer)
        }
      } finally {
        if (backdrop != null) backdrop.close()
      }
    }
    // Backdrop in paint, smaller blur
    try {
      val backdrop = ImageFilter.makeBlur(5, 5, FilterTileMode.CLAMP)
      val paint = Paint().setImageFilter(backdrop)
      try {
        val rect = Rect.makeXYWH(xpos + 110, ypos - 310, 200, 200).intersect(screen)
        if (rect != null) {
          val layer = canvas.save
          canvas.clipRect(rect, true)
          canvas.saveLayer(new SaveLayerRec(null, paint, SaveLayerRecFlag.INIT_WITH_PREVIOUS))
          canvas.drawColor(0x40FFFFFF)
          canvas.restoreToCount(layer)
        }
      } finally {
        if (backdrop != null) backdrop.close()
        if (paint != null) paint.close()
      }
    }
    // ColorFilter
    val grayscaleMatrix = new ColorMatrix(0.21f, 0.72f, 0.07f, 0, 0, 0.21f, 0.72f, 0.07f, 0, 0, 0.21f, 0.72f, 0.07f, 0, 0, 0, 0, 0, 1, 0)
    try {
      val colorFilter = ColorFilter.makeMatrix(grayscaleMatrix)
      val backdrop = ImageFilter.makeColorFilter(colorFilter, null, null)
      try {
        val rect = Rect.makeXYWH(xpos - 310, ypos - 100, 200, 200).intersect(screen)
        if (rect != null) {
          val layer = canvas.save
          canvas.clipRect(rect, true)
          canvas.saveLayer(new SaveLayerRec(rect, null, backdrop, SaveLayerRecFlag.INIT_WITH_PREVIOUS))
          canvas.restoreToCount(layer)
        }
      } finally {
        if (colorFilter != null) colorFilter.close()
        if (backdrop != null) backdrop.close()
      }
    }
    // makeImageSnapshot
    val rect = Rect.makeXYWH(xpos - 100, ypos - 100, 200, 200).intersect(screen)
    if (rect != null) {
      try {
        val surface = canvas.getSurface
        val image = surface.makeImageSnapshot(rect.scale(dpi).toIRect)
        val filter = ImageFilter.makeBlur(20, 20, FilterTileMode.CLAMP)
        val paint = Paint().setImageFilter(filter)
        val fill = Paint().setColor(0x40FFFFFF)
        try {
          val layer = canvas.save
          canvas.clipRect(rect, true)
          canvas.drawImageRect(image, rect, paint)
          canvas.restoreToCount(layer)
          canvas.drawRect(rect, fill)
        } finally {
          if (surface != null) surface.close()
          if (image != null) image.close()
          if (filter != null) filter.close()
          if (paint != null) paint.close()
          if (fill != null) fill.close()
        }
      }
    }
  }
}