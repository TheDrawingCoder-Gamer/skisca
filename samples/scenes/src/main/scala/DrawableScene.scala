package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

class DrawableScene extends Scene {
  final val inter18 = new Font(Scene.inter, 18)
  final val bounds = Paint().setColor(0x803333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    // System.out.println("\n--- new frame ---");
    canvas.translate(20, 40)
    // default
    val drawable = new SomeDrawable(inter18)
    val generation0 = drawable.getGenerationId
    drawable.draw(canvas)
    assert(generation0 == drawable.getGenerationId, "Expected " + generation0 + " == " + drawable.getGenerationId)
    canvas.drawRect(drawable.getBounds, bounds)
    canvas.translate(0, 36)
    drawable.draw(canvas, 100, 0)
    assert(generation0 == drawable.getGenerationId, "Expected " + generation0 + " == " + drawable.getGenerationId)
    canvas.drawRect(drawable.getBounds, bounds)
    canvas.translate(0, 36)
    drawable.draw(canvas, Matrix33.makeScale(4, 1.5f))
    assert(generation0 == drawable.getGenerationId, "Expected " + generation0 + " == " + drawable.getGenerationId)
    canvas.translate(0, 36)
    // changed
    drawable.setColor(0xFFCC3333)
    drawable.setText("changed")
    val generation1 = drawable.getGenerationId
    assert(generation0 != generation1, "Expected " + generation0 + " != " + generation1)
    assert(generation1 == drawable.getGenerationId, "Expected " + generation1 + " == " + drawable.getGenerationId)
    canvas.drawDrawable(drawable)
    assert(generation1 == drawable.getGenerationId, "Expected " + generation1 + " == " + drawable.getGenerationId)
    canvas.translate(0, 36)
    canvas.drawDrawable(drawable, 100, 0)
    assert(generation1 == drawable.getGenerationId, "Expected " + generation1 + " == " + drawable.getGenerationId)
    canvas.translate(0, 36)
    canvas.drawDrawable(drawable, Matrix33.makeScale(4, 1.5f))
    assert(generation1 == drawable.getGenerationId, "Expected " + generation1 + " == " + drawable.getGenerationId)
    canvas.translate(0, 36)
    // picture
    drawable.setColor(0xFF3333CC)
    drawable.setText("picture")
    val generation2 = drawable.getGenerationId
    try {
      val picture = drawable.makePictureSnapshot
      try {
        assert(generation2 == drawable.getGenerationId, "Expected " + generation2 + " == " + drawable.getGenerationId)
        canvas.drawPicture(picture)
        assert(generation2 == drawable.getGenerationId, "Expected " + generation2 + " == " + drawable.getGenerationId)
        canvas.translate(0, 36)
        canvas.drawPicture(picture)
        canvas.translate(0, 36)
      } finally {
        if (picture != null) picture.close()
      }
    }
    assert(generation2 == drawable.getGenerationId, "Expected " + generation2 + " == " + drawable.getGenerationId)
    try {
      val picture = drawable.makePictureSnapshot
      try {
        assert(generation2 == drawable.getGenerationId, "Expected " + generation2 + " == " + drawable.getGenerationId)
        canvas.drawPicture(picture)
        canvas.translate(0, 36)
      } finally {
        if (picture != null) picture.close()
      }
    }
    // drawable.close();
  }
}

class SomeDrawable(private var font: Font) extends Drawable {
  private var color = 0xFF000000
  private var text = "default"

  override def onDraw(canvas: Canvas): Unit = {
    // System.out.println(getGenerationId() + " Drawable::onDraw(color = " + color + ", text = \"" + text + "\")");
    try {
      val p = Paint().setColor(color)
      try {
        canvas.drawString(text, 0, 0, font, p)
      } finally {
        if (p != null) p.close()
      }
    }
  }

  override def onGetBounds: Rect = {
    // System.out.println(getGenerationId() + " Drawable::onGetBounds(color = " + color + ", text = \"" + text + "\")");
    font.measureText(text)
  }

  def setColor(color: Int): Unit = {
    this.color = color
    val prevGen = getGenerationId
    notifyDrawingChanged
    // System.out.println(prevGen + " -> " + getGenerationId() + " Drawable::notifyDrawingChanged");
  }

  def setText(text: String): Unit = {
    this.text = text
    val prevGen = getGenerationId
    notifyDrawingChanged
    // System.out.println(prevGen + " -> " + getGenerationId() + " Drawable::notifyDrawingChanged");
  }
}