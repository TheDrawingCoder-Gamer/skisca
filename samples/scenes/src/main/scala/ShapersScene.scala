package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

import java.util.*

class ShapersScene extends Scene {
  final var firaCode11: Font = new Font(Typeface.makeFromFile(Scene.file("fonts/FiraCode-Regular.ttf")), 11)
  final val stroke = Paint().setColor(0x203333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)
  final val fill = Paint().setColor(0xFF000000)

  def drawWithShaper(canvas: Canvas, maxWidth: Float, color: Int, name: String, shaper: Shaper): Unit = {
    fill.setColor(color)
    val text = "Incomprehensibilities word word word word ararar123456 +++ <-> *** c̝̣̱̲͈̝ͨ͐̈ͪͨ̃ͥͅh̙̬̿̂a̯͎͍̜͐͌͂̚o̬s͉̰͊̀ " + name
    try {
      val blob = shaper.shape(text, firaCode11, ShapingOptions.DEFAULT, maxWidth, new Point(0, 0))
      try {
        canvas.drawTextBlob(blob, 0, 0, fill)
        canvas.translate(0, blob.getBounds.getHeight)
      } finally {
        if (blob != null) blob.close()
      }
    }
    try {
      val blob = shaper
        .shape(text + " RTL", firaCode11, ShapingOptions.DEFAULT.copy(leftToRight = false), maxWidth, new Point(0, 0))
      try {
        canvas.drawTextBlob(blob, 0, 0, fill)
        canvas.translate(0, blob.getBounds.getHeight)
      } finally {
        if (blob != null) blob.close()
      }
    }
    var features = "ss01 cv01 onum -calt"
    try {
      val blob = shaper.shape(text + " " + features, firaCode11, ShapingOptions.DEFAULT
                                                                               .withFeatures(features), maxWidth, new Point(0, 0))
      try {
        canvas.drawTextBlob(blob, 0, 0, fill)
        canvas.translate(0, blob.getBounds.getHeight)
      } finally {
        if (blob != null) blob.close()
      }
    }
    features = "ss01[42:45] cv01[42:45] onum[48:51] calt[59:60]=0"
    try {
      val blob = shaper.shape(text + " " + features, firaCode11, ShapingOptions.DEFAULT
                                                                               .withFeatures(features), maxWidth, new Point(0, 0))
      try {
        canvas.drawTextBlob(blob, 0, 0, fill)
        canvas.translate(0, blob.getBounds.getHeight)
      } finally {
        if (blob != null) blob.close()
      }
    }
    val height = 0
    try {
      val blob = shaper.shape(text, firaCode11, ShapingOptions.DEFAULT, 75, new Point(0, 0))
      try {
        canvas.drawTextBlob(blob, 0, 0, fill)
        val bounds = blob.getBounds
        canvas.drawRect(Rect.makeXYWH(0, 0, 75, bounds.getHeight), stroke)
        canvas.translate(0, bounds.getHeight)
      } finally {
        if (blob != null) blob.close()
      }
    }
  }

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val fontMgr = FontMgr.getDefault
    {
      val shaper = Shaper.makePrimitive
      try {
        canvas.save
        canvas.translate(20, 20)
        drawWithShaper(canvas, width / 2 - 30, 0xFFf94144, "Primitive", shaper)
        canvas.restore
      } finally {
        if (shaper != null) shaper.close()
      }
    }
    {
      val shaper = Shaper.make
      try {
        canvas.save
        canvas.translate(20, height / 3)
        drawWithShaper(canvas, width / 2 - 30, 0xFF577590, "make", shaper)
        canvas.restore
      } finally {
        if (shaper != null) shaper.close()
      }
    }
    if (System.getProperty("os.name") == "Mac OS X") {
      canvas.save
      canvas.translate(20, height * 2 / 3)
      {
        val shaper = Shaper.makeCoreText
        try {
          drawWithShaper(canvas, width / 2 - 30, 0xFFf3722c, "CoreText", shaper)
        } finally {
          if (shaper != null) shaper.close()
        }
      }
      canvas.restore
    }
    {
      val shaper = Shaper.makeShaperDrivenWrapper
      try {
        canvas.save
        canvas.translate(width / 2 + 10, 20)
        drawWithShaper(canvas, width / 2 - 30, 0xFF43aa8b, "ShaperDrivenWrapper", shaper)
        canvas.restore
      } finally {
        if (shaper != null) shaper.close()
      }
    }
    {
      val shaper = Shaper.makeShapeThenWrap
      try {
        canvas.save
        canvas.translate(width / 2 + 10, height / 3)
        drawWithShaper(canvas, width / 2 - 30, 0xFFf8961e, "ShapeThenWrap", shaper)
        canvas.restore
      } finally {
        if (shaper != null) shaper.close()
      }
    }
    {
      val shaper = Shaper.makeShapeDontWrapOrReorder
      try {
        canvas.save
        canvas.translate(width / 2 + 10, height * 2 / 3)
        drawWithShaper(canvas, width / 2 - 30, 0xFF90be6d, "ShapeDontWrapOrReorder", shaper)
        canvas.restore
      } finally {
        if (shaper != null) shaper.close()
      }
    }
    canvas.restore
  }
}