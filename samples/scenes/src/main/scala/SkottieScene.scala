package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.resources.*
import gay.menkissing.skisca.skottie.*
import gay.menkissing.skisca.sksg.*
import io.github.humbleui.types.*

import java.io.*
import java.nio.file.{Path, *}

class SkottieScene extends Scene {
  try {
    _variants = Files.list(Path.of(Scene.file("animations"))).map(_.getFileName).map(_.toString).sorted.toArray(new Array[String](_))
    _variantIdx = 7
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }
  private var animation: Animation = null
  private val ic = new InvalidationController
  private var animationVariant: String = null
  private var error: String = null
  private val logger = new Logger() {
    override def log(level: LogLevel, message: String, json: String): Unit = {
      error = "[" + level + "] " + message + " in " + json
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    if (animationVariant ne _variants(_variantIdx)) {
      if (animation != null) animation.close()
      animation = null
      error = null
      try {
        val dir = Scene.file("animations")
        val resourceProvider = CachingResourceProvider
          .make(DataURIResourceProviderProxy.make(FileResourceProvider.make(dir, false), false))
        animation = new AnimationBuilder(AnimationBuilderFlag.DEFER_IMAGE_LOADING, AnimationBuilderFlag
          .PREFER_EMBEDDED_FONTS).setLogger(logger).setResourceProvider(resourceProvider)
                                 .buildFromFile(dir + "/" + _variants(_variantIdx))
      } catch {
        case e: IllegalArgumentException =>
      }
      animationVariant = _variants(_variantIdx)
    }
    if (animation == null) {
      Scene.drawStringCentered(canvas, "animations/" + _variants(_variantIdx) + ": " + error, width / 2, height / 2, Scene.inter13, Scene.blackFill)
      return
    }
    val progress = (System.currentTimeMillis % (1000 * animation.getDuration).toLong) / (1000 * animation.getDuration)
    ic.reset()
    animation.seek(progress, ic)
    var animationWidth = width - 20
    var animationHeight = animationWidth / animation.getWidth * animation.getHeight
    if (animationHeight > height - 20) {
      animationWidth = (animationWidth / (animationHeight / (height - 20))).toInt
      animationHeight = height - 20
    }
    val scale = animationWidth / animation.getWidth
    val bounds = Rect
      .makeXYWH((width - animationWidth) / 2f, (height - animationHeight) / 2f - 2, animationWidth, animationHeight)
    {
      val paint = Paint().setColor(0xFF64C7BE).setMode(PaintMode.STROKE).setStrokeWidth(1)
      try {
        canvas.drawRect(Rect
          .makeXYWH(bounds.getLeft - 0.5f, bounds.getTop - 0.5f, bounds.getWidth + 1f, bounds.getHeight + 1f), paint)
        paint.setMode(PaintMode.FILL)
        canvas.drawRect(Rect
          .makeXYWH(bounds.getLeft - 1, bounds.getBottom, ((bounds.getWidth + 2) * progress).toFloat, 4), paint)
        animation.render(canvas, bounds, RenderFlag.SKIP_TOP_LEVEL_ISOLATION)
        paint.setColor(0x40CC3333).setMode(PaintMode.STROKE).setStrokeWidth(4)
        val dirtyRect = ic.getBounds.scale(scale).offset(bounds.getLeft, bounds.getTop).intersect(bounds)
        if (dirtyRect != null) canvas.drawRect(dirtyRect, paint)
      } finally {
        if (paint != null) paint.close()
      }
    }
  }
}