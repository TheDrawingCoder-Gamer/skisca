package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.{Canvas, Font, Image, Paint, *}
import io.github.humbleui.types.{IRect, Point, Rect}

import java.awt.{FontMetrics => _, *}
import java.awt
import java.awt.image as awtimage
import java.awt.geom.AffineTransform
import java.util
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.swing.*
import javax.swing.plaf.metal.{DefaultMetalTheme, MetalLookAndFeel, OceanTheme}

object SwingScene {
  // public final javax.swing.JPanel panel;
  var panels = new collection.mutable.ArrayBuffer[Pair[String, JPanel]]
}

class SwingScene extends Scene {
  // System.out.println(Arrays.toString(UIManager.getInstalledLookAndFeels()));
  // javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
  // javax.swing.UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
  // javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
  // javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
  // javax.swing.JFrame f=new javax.swing.JFrame();//creating instance of JFrame
  // var b=new javax.swing.JComboBox<String>(new String[] {"Swing Button"});//creating instance of JButton
  // b.setBounds(130,100,200, 50);//x axis, y axis, width, height
  // f.add(b);//adding button in JFrame
  // f.setSize(400,500);//400 width and 500 height
  // f.setLayout(null);//using no layout managers
  // f.setVisible(true);//making the frame visible
  try {
    for (lfi <- javax.swing.UIManager.getInstalledLookAndFeels) {
      if ((lfi.getName ne "Mac OS X") && (lfi.getName ne "GTK+")) {
        //              if (lfi.getName() == "CDE/Motif") {
        //              if (lfi.getName() == "Metal") {
        javax.swing.UIManager.setLookAndFeel(lfi.getClassName)
        SwingScene.panels.append(new Pair(lfi.getName, panel))
      }
    }
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }
  private[scenes] var startTime: Long = 0
  private[scenes] var iters = 0
  private[scenes] val columns = 1

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    if (startTime == 0) startTime = System.currentTimeMillis
    //        else if (System.currentTimeMillis() - startTime > 30000) {
    //            System.out.println(String.format("Average paint performance %f ms", (double) (System.currentTimeMillis() - startTime) / iters));
    //            System.exit(0);
    //        }
    iters += 1
    val g = new SkiaGraphics(canvas)
    for (pair <- SwingScene.panels) {
      val panel = pair.getSecond
      //            ((JLabel) panel.getComponent(0)).setText("iter " + iters);
      for (i <- 0 until columns) {
        canvas.save
        panel.paint(g)
        canvas.restore
        canvas.translate(180, 0)
      }
    }
    g.dispose()
  }

  private[scenes] def panel = {
    val panel = new JPanel
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS))
    var y = -25
    val label = new JLabel("iter 0")
    y += 35
    label.setBounds(10, y, 160, 25)
    panel.add(label)
    val combobox = new JComboBox[String](Array[String]("javax.swing.JComboBox"))
    y += 35
    combobox.setBounds(10, y, 160, 25)
    panel.add(combobox)
    var checkbox = new JCheckBox("Checkbox", false)
    y += 35
    checkbox.setBounds(10, y, 160, 25)
    panel.add(checkbox)
    checkbox = new JCheckBox("Checkbox", true)
    y += 35
    checkbox.setBounds(10, y, 160, 25)
    panel.add(checkbox)
    var radio = new JRadioButton("JRadioButton", false)
    y += 35
    radio.setBounds(10, y, 160, 25)
    panel.add(radio)
    radio = new JRadioButton("JRadioButton", true)
    y += 35
    radio.setBounds(10, y, 160, 25)
    panel.add(radio)
    val slider: JSlider = new JSlider() {
      @throws[HeadlessException]
      override def getMousePosition: awt.Point = {
        null
      }
    }
    y += 35
    slider.setBounds(10, y, 160, 25)
    panel.add(slider)
    val textfield = new JTextField("JTextField")
    y += 35
    textfield.setBounds(10, y, 160, 25)
    panel.add(textfield)
    val textarea = new JTextArea("JTextArea")
    y += 35
    textarea.setBounds(10, y, 160, 25)
    panel.add(textarea)
    val progress = new JProgressBar
    progress.setValue(30)
    y += 35
    progress.setBounds(10, y, 160, 25)
    panel.add(progress)
    panel.setSize(180, y + 35)
    panel
  }
}

object SkiaGraphics {
  private val defaultFont = new awt.Font(java.awt.Font.DIALOG, java.awt.Font.PLAIN, 12)
  private val defaultSkiaFont = skiaFont(defaultFont)
  var canvasMatrix: Matrix33 = Matrix33.IDENTITY
  var canvasClip: Rectangle = null
  val fontCache = new ConcurrentHashMap[awt.Font, Font]
  def skiaFont(font: awt.Font): Font = {
    val style: FontStyle = font.getStyle match {
      case awt.Font.PLAIN => FontStyle.NORMAL
      case awt.Font.BOLD => FontStyle.BOLD
      case awt.Font.ITALIC => FontStyle.ITALIC
      case s if s == awt.Font.ITALIC + awt.Font.BOLD => FontStyle.BOLD_ITALIC
      case s => throw new RuntimeException("Unknown font style: " + s + " in " + font)
    }
    var typeface = FontMgr.getDefault.matchFamiliesStyle(Array[String]("System Font", "Segoe UI", "Ubuntu"), style)
    if (typeface == null) typeface = Typeface.makeDefault
    new Font(typeface, font.getSize)
  }

  val rasterCache = new ConcurrentHashMap[awt.image.BufferedImage, Image]
}

class SkiaGraphics(val canvas: Canvas, val indent: String, var matrix: Matrix33, var clip: Rectangle, var font: awt.Font, var skiaFont: Font) extends Graphics2D {
  this.clip = clip
  this.skiaFont = skiaFont
  var color: Color = null
  var backgroundColor: Color = null
  final val paint = Paint().setColor(0xFFFFFFFF).setStrokeWidth(1).setStrokeCap(PaintStrokeCap.SQUARE)
  final val backgroundPaint = Paint().setColor(0xFF000000)

  def this(canvas: Canvas) = {
    this(canvas, "", Matrix33.IDENTITY, null, null, null)
  }

  def beforeDraw(): Unit = {
    if ((SkiaGraphics.canvasMatrix ne matrix) || (SkiaGraphics.canvasClip ne clip)) {
      canvas.restore
      canvas.save
      canvas.concat(matrix)
      if (clip != null) canvas.clipRect(Rect.makeXYWH(clip.x, clip.y, clip.width, clip.height))
      SkiaGraphics.canvasMatrix = matrix
      SkiaGraphics.canvasClip = clip
    }
  }

  def log(format: String, args: Any*): Unit = {

    // System.out.println(indent + String.format(format, args));
  }

  override def draw(s: Shape): Unit = {
    log("draw")
  }

  override def drawImage(img: awt.Image, xform: awt.geom.AffineTransform, obs: awtimage.ImageObserver): Boolean = {
    log("drawImage")
    false
  }

  override def drawImage(img: awt.image.BufferedImage, op: awt.image.BufferedImageOp, x: Int, y: Int): Unit = {
    log("drawImage")
  }

  override def drawRenderedImage(img: awt.image.RenderedImage, xform: awt.geom.AffineTransform): Unit = {
    log("drawRenderedImage")
  }

  override def drawRenderableImage(img: awt.image.renderable.RenderableImage, xform: awt.geom.AffineTransform): Unit = {
    log("drawRenderableImage")
  }

  override def drawString(str: String, x: Int, y: Int): Unit = {
    drawString(str, x.toFloat, y.toFloat)
  }

  override def drawString(str: String, x: Float, y: Float): Unit = {
    log("[+] drawString")
    if (font == null) setFont(SkiaGraphics.defaultFont)
    beforeDraw()
    canvas.drawString(str, x, y, skiaFont, paint)
  }

  override def drawString(iterator: java.text.AttributedCharacterIterator, x: Int, y: Int): Unit = {
    log("drawString")
  }

  override def drawString(iterator: java.text.AttributedCharacterIterator, x: Float, y: Float): Unit = {
    log("drawString")
  }

  override def drawGlyphVector(g: awt.font.GlyphVector, x: Float, y: Float): Unit = {
    log("drawGlyphVector")
  }

  override def fill(s: Shape): Unit = {
    log("[+] fill " + s)
    beforeDraw()
    if (s.isInstanceOf[awt.geom.Path2D]) {
      {
        val path = Path()
        try {
          val iter = s.getPathIterator(null)
          val segment = new Array[Float](6)
          while (!iter.isDone) {
            val `type` = iter.currentSegment(segment)
            `type` match {
              case java.awt.geom.PathIterator.SEG_MOVETO => {
                path.moveTo(new Point(segment(0), segment(1)))
              } // System.out.println("MOVETO " + segment[0] + " " + segment[1]);
              case java.awt.geom.PathIterator.SEG_LINETO => {
                path.lineTo(new Point(segment(0), segment(1)))
              } // System.out.println("LINETO " + segment[0] + " " + segment[1]);
              case java.awt.geom.PathIterator.SEG_QUADTO => {
                path.quadTo(segment(0), segment(1), segment(2), segment(3))
              } // System.out.println("QUADTO " + segment[0] + " " + segment[1] + " " + segment[2] + " " + segment[3]);
              case java.awt.geom.PathIterator.SEG_CUBICTO => {
                path.cubicTo(segment(0), segment(1), segment(2), segment(3), segment(4), segment(5))
              } // System.out.println("CUBICTO " + segment[0] + " " + segment[1] + " " + segment[2] + " " + segment[3] + " " + segment[4] + " " + segment[5]);
              case java.awt.geom.PathIterator.SEG_CLOSE => {
                path.closePath
              }
            }
            iter.next()
          }
          s.asInstanceOf[awt.geom.Path2D].getWindingRule match {
            case java.awt.geom.Path2D.WIND_EVEN_ODD => {
              path.setFillMode(PathFillMode.EVEN_ODD)
            }
            case java.awt.geom.Path2D.WIND_NON_ZERO => {
              path.setFillMode(PathFillMode.WINDING)
            }
          }
          canvas.drawPath(path, paint)
        } finally {
          if (path != null) path.close()
        }
      }
    }
  }

  override def hit(rect: Rectangle, s: Shape, onStroke: Boolean): Boolean = {
    log("hit")
    false
  }

  override def getDeviceConfiguration: GraphicsConfiguration = {
    log("[+] getDeviceConfiguration")
    SkiaGraphicsConfig.INSTANCE
  }

  override def setComposite(comp: Composite): Unit = {
    log("[/] setComposite " + comp)
    if (comp eq java.awt.AlphaComposite.Clear) {
      paint.setBlendMode(BlendMode.CLEAR)
    } else if (comp eq java.awt.AlphaComposite.SrcOver) {
      paint.setBlendMode(BlendMode.SRC_OVER)
    } else if (comp.isInstanceOf[AlphaComposite]) {
      log(" UNKNOWN COMPOSITE MODE " + comp.asInstanceOf[AlphaComposite]
                                           .getRule)
    }
  }

  override def setPaint(paint: awt.Paint): Unit = {
    if (paint.isInstanceOf[Color]) {
      log("[+] setPaint " + paint)
      setColor(paint.asInstanceOf[Color])
    }
    else if (paint.isInstanceOf[LinearGradientPaint]) {
      val gr = paint.asInstanceOf[LinearGradientPaint]
      val colors = new Array[Int](gr.getColors.length)
      for (i <- 0 until colors.length) {
        colors(i) = gr.getColors.apply(i).getRGB
      }
      log("[+] setPaint " + gr.getStartPoint + " " + gr.getEndPoint + " " + util.Arrays.toString(colors) + " " + util
        .Arrays.toString(gr.getFractions))
      val shader = Shader
        .makeLinearGradient(gr.getStartPoint.getX.toFloat, gr.getStartPoint.getY.toFloat, gr.getEndPoint.getX
                                                                                            .toFloat, gr.getEndPoint
                                                                                                        .getY
                                                                                                        .toFloat, colors, gr
          .getFractions)
      this.paint.setShader(shader)
    }
    else {
      log("setPaint " + paint)
    }
  }

  override def setStroke(s: Stroke): Unit = {
    if (s.isInstanceOf[awt.BasicStroke]) {
      val ss = s.asInstanceOf[awt.BasicStroke]
      log("[+] setStroke " + ss)
      paint.setStrokeWidth(ss.getLineWidth)
    }
    else {
      log("setStroke " + s)
    }
  }

  override def setRenderingHint(hintKey: RenderingHints.Key, hintValue: AnyRef): Unit = {
    log("setRenderingHint " + hintKey + "=" + hintValue)
  }

  override def getRenderingHint(hintKey: RenderingHints.Key): AnyRef = {
    log("getRenderingHint " + hintKey)
    null
  }

  override def setRenderingHints(hints: util.Map[_, _]): Unit = {
    log("setRenderingHints")
  }

  override def addRenderingHints(hints: util.Map[_, _]): Unit = {
    log("addRenderingHints")
  }

  override def getRenderingHints: RenderingHints = {
    log("getRenderingHints")
    null
  }

  override def translate(x: Int, y: Int): Unit = {
    translate(x.toDouble, y.toDouble)
  }

  override def translate(tx: Double, ty: Double): Unit = {
    log("[+] translate %f, %f", tx, ty)
    matrix = matrix.makeConcat(Matrix33.makeTranslate(tx.toFloat, ty.toFloat))
    if (clip != null) {
      clip = new Rectangle(clip.x, clip.y, clip.width, clip.height)
      clip.translate(-tx.toInt, -ty.toInt)
    }
  }

  override def rotate(theta: Double): Unit = {
    log("rotate " + theta)
  }

  override def rotate(theta: Double, x: Double, y: Double): Unit = {
    log("rotate")
  }

  override def scale(sx: Double, sy: Double): Unit = {
    log("scale")
  }

  override def shear(shx: Double, shy: Double): Unit = {
    log("shear")
  }

  override def transform(Tx: AffineTransform): Unit = {
    log("transform")
  }

  override def setTransform(Tx: AffineTransform): Unit = {
    log("setTransform")
  }

  override def getTransform: awt.geom.AffineTransform = {
    log("getTransform")
    AffineTransform.getTranslateInstance(0, 0)
  }

  override def getPaint: awt.Paint = {
    log("getPaint")
    null
  }

  override def getComposite: Composite = {
    log("getComposite")
    null
  }

  override def setBackground(color: Color): Unit = {
    log("[+] setBackground " + color)
    this.backgroundColor = color
    backgroundPaint.setColor(color.getRGB)
  }

  override def getBackground: Color = {
    log("[+] getBackground")
    backgroundColor
  }

  override def getStroke: Stroke = {
    log("getStroke")
    null
  }

  override def clip(s: Shape): Unit = {
    if (s.isInstanceOf[awt.Rectangle]) {
      log("[+] clip " + s)
      val r = s.asInstanceOf[awt.Rectangle]
      clipRect(r.x, r.y, r.width, r.height)
    }
    else {
      log("clip " + s)
    }
  }

  override def getFontRenderContext: awt.font.FontRenderContext = {
    log("getFontRenderContext")
    null
  }

  override def create: Graphics = {
    log("[+] create")
    new SkiaGraphics(canvas, indent + "  ", matrix, clip, font, skiaFont)
  }

  override def getColor: Color = {
    log("[+] getColor")
    color
  }

  override def setColor(c: Color): Unit = {
    log("[+] setColor " + c)
    this.color = c
    paint.setColor(if (c == null) {
      0xFFFFFFFF
    } else {
      c.getRGB
    })
    paint.setShader(null)
  }

  override def setPaintMode(): Unit = {
    log("setPaintMode")
  }

  override def setXORMode(c1: Color): Unit = {
    log("setXORMode")
  }

  override def getFont: awt.Font = {
    log("[+] getFont")
    if (font == null) setFont(SkiaGraphics.defaultFont)
    font
  }

  override def setFont(font: awt.Font): Unit = {
    log("[+] setFont " + font)
    if (this.font ne font) {
      this.font = font
      var cachedFont = SkiaGraphics.fontCache.get(font)
      if (cachedFont == null) {
        cachedFont = SkiaGraphics.skiaFont(font)
        SkiaGraphics.fontCache.put(font, cachedFont)
      }
      skiaFont = cachedFont
    }
  }

  override def getFontMetrics(f: awt.Font): awt.FontMetrics = {
    log("getFontMetrics")
    null
  }

  override def getClipBounds: Rectangle = {
    log("[+] getClipBounds => " + clip)
    clip
  }

  override def clipRect(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("[+] clipRect %d %d %d %d", x, y, width, height)
    if (clip == null) {
      setClip(x, y, width, height)
    } else {
      val r = clip.intersection(new Rectangle(x, y, width, height))
      setClip(r.x, r.y, r.width, r.height)
    }
  }

  override def setClip(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("[+] setClip %d %d %d %d", x, y, width, height)
    clip = new Rectangle(x, y, width, height)
  }

  override def getClip: Shape = {
    log("[+] getClip => " + clip)
    clip
  }

  override def setClip(clip: Shape): Unit = {
    if (clip.isInstanceOf[awt.Rectangle]) {
      val r = clip.asInstanceOf[awt.Rectangle]
      setClip(r.x, r.y, r.width, r.height)
    }
    else {
      log("setClip " + clip)
    }
  }

  override def copyArea(x: Int, y: Int, width: Int, height: Int, dx: Int, dy: Int): Unit = {
    log("copyArea")
  }

  override def drawLine(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    log("[+] drawLine %d, %d -> %d, %d", x1, y1, x2, y2)
    beforeDraw()
    canvas.drawLine(x1, y1, x2, y2, paint)
  }

  override def fillRect(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("[+] fillRect %d, %d, %d, %d", x, y, width, height)
    beforeDraw()
    canvas.drawRect(Rect.makeXYWH(x, y, width, height), paint)
  }

  override def clearRect(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("clearRect %d, %d, %d, %d", x, y, width, height)
  }

  override def drawRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int): Unit = {
    log("drawRoundRect")
  }

  override def fillRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int): Unit = {
    log("fillRoundRect")
  }

  override def drawOval(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("drawOval")
    paint.setMode(PaintMode.STROKE)
    canvas.drawOval(Rect.makeXYWH(x, y, width, height), paint)
    paint.setMode(PaintMode.FILL)
  }

  override def fillOval(x: Int, y: Int, width: Int, height: Int): Unit = {
    log("[+] fillOval")
    canvas.drawOval(Rect.makeXYWH(x, y, width, height), paint)
  }

  override def drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int): Unit = {
    log("[+] drawArc")
    paint.setMode(PaintMode.STROKE)
    canvas.drawArc(x, y, x + width, y + height, startAngle, arcAngle, false, paint)
    paint.setMode(PaintMode.FILL)
  }

  override def fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int): Unit = {
    log("[+] fillArc")
    canvas.drawArc(x, y, x + width, y + height, startAngle, arcAngle, false, paint)
  }

  override def drawPolyline(xPoints: Array[Int], yPoints: Array[Int], nPoints: Int): Unit = {
    log("drawPolyline")
  }

  override def drawPolygon(xPoints: Array[Int], yPoints: Array[Int], nPoints: Int): Unit = {
    log("drawPolygon")
  }

  override def fillPolygon(xPoints: Array[Int], yPoints: Array[Int], nPoints: Int): Unit = {
    log("[+] fillPolygon " + util.Arrays.toString(xPoints) + " " + util.Arrays.toString(yPoints) + " " + nPoints)
    beforeDraw()
    {
      val path = Path()
      try {
        path.moveTo(xPoints(0), yPoints(0))
        for (i <- 1 until nPoints) {
          path.lineTo(xPoints(i), yPoints(i))
        }
        path.lineTo(xPoints(0), yPoints(0))
        path.closePath
        canvas.drawPath(path, paint)
      } finally {
        if (path != null) path.close()
      }
    }
  }

  override def drawImage(img: awt.Image, x: Int, y: Int, observer: awtimage.ImageObserver): Boolean = {
    drawImage(img, x, y, img
      .getWidth(null), img.getHeight(null), null, observer)
  }

  override def drawImage(img: awt.Image, x: Int, y: Int, width: Int, height: Int, observer: awtimage.ImageObserver): Boolean = drawImage(img, x, y, x + width, y + height, 0, 0, width, height, null, observer)

  override def drawImage(img: awt.Image, x: Int, y: Int, bgcolor: Color, observer: awtimage.ImageObserver): Boolean = {
    drawImage(img, x, y, img
      .getWidth(null), img.getHeight(null), bgcolor, observer)
  }

  override def drawImage(img: awt.Image, x: Int, y: Int, width: Int, height: Int, bgcolor: Color, observer: awtimage.ImageObserver): Boolean = drawImage(img, x, y, x + width, y + height, 0, 0, width, height, bgcolor, observer)

  override def drawImage(img: awt.Image, dx1: Int, dy1: Int, dx2: Int, dy2: Int, sx1: Int, sy1: Int, sx2: Int, sy2: Int, observer: awtimage.ImageObserver): Boolean = drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null, observer)

  override def drawImage(img: awt.Image, dx1: Int, dy1: Int, dx2: Int, dy2: Int, sx1: Int, sy1: Int, sx2: Int, sy2: Int, bgcolor: Color, observer: awtimage.ImageObserver): Boolean = {
    if (img.isInstanceOf[SkiaVolatileImage]) {
      log("[+] drawImage dx1=%d dy1=%d dx2=%d dy2=%d sx1=%d sy1=%d sx2=%d sy2=%d color=%s observer=%s", dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
      beforeDraw()
      try {
        val image = img.asInstanceOf[SkiaVolatileImage].surface.makeImageSnapshot
        try {
          canvas.drawImageRect(image, Rect.makeLTRB(sx1, sy1, sx2, sy2), Rect.makeLTRB(dx1, dy1, dx2, dy2))
        } finally {
          if (image != null) image.close()
        }
      }
    }
    else if (img.isInstanceOf[awt.image.AbstractMultiResolutionImage]) {
      val variant = img.asInstanceOf[awt.image.AbstractMultiResolutionImage].getResolutionVariants.get(0)
      if (variant
        .isInstanceOf[awt.image.BufferedImage]) {
        drawImage(variant, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
      } else {
        log("drawImage %s dx1=%d dy1=%d dx2=%d dy2=%d sx1=%d sy1=%d sx2=%d sy2=%d color=%s observer=%s", img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
      }
    }
    else if (img.isInstanceOf[awt.image.BufferedImage]) {
      log("[+] drawImage dx1=%d dy1=%d dx2=%d dy2=%d sx1=%d sy1=%d sx2=%d sy2=%d color=%s observer=%s", dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
      beforeDraw()
      val bi = img.asInstanceOf[awt.image.BufferedImage]
      canvas.drawImageRect(skImage(bi), Rect.makeLTRB(sx1, sy1, sx2, sy2), Rect.makeLTRB(dx1, dy1, dx2, dy2))
    }
    else {
      log("drawImage %s dx1=%d dy1=%d dx2=%d dy2=%d sx1=%d sy1=%d sx2=%d sy2=%d color=%s observer=%s", img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
    }
    false
  }

  def skImage(bi: awt.image.BufferedImage): Image = {
    var i = SkiaGraphics.rasterCache.get(bi)
    if (i != null) return i
    val b = Bitmap()
    b.allocN32Pixels(bi.getWidth, bi.getHeight)
    for (x <- 0 until bi.getWidth) {
      for (y <- 0 until bi.getHeight) {
        val color = bi.getRGB(x, y)
        b.erase(color, IRect.makeXYWH(x, y, 1, 1))
      }
    }
    i = Image.makeRasterFromBitmap(b)
    SkiaGraphics.rasterCache.put(bi, i)
    i
  }

  override def dispose(): Unit = {
    log("[+] dispose")
    paint.close()
    backgroundPaint.close()
  }
}

object SkiaGraphicsConfig {
  val INSTANCE = new SkiaGraphicsConfig
}

class SkiaGraphicsConfig extends GraphicsConfiguration {
  def log(format: String, args: String*): Unit = {

    //        System.out.println("GC " + String.format(format, args));
  }

  override def getDevice: GraphicsDevice = {
    log("getDevice")
    null
  }

  override def getColorModel: awt.image.ColorModel = {
    log("getColorModel")
    null
  }

  override def getColorModel(transparency: Int): awt.image.ColorModel = {
    log("getColorModel(int)")
    null
  }

  override def getDefaultTransform: awt.geom.AffineTransform = {
    log("getDefaultTransform")
    null
  }

  override def getNormalizingTransform: awt.geom.AffineTransform = {
    log("getNormalizingTransform")
    null
  }

  override def getBounds: awt.Rectangle = {
    log("getBounds")
    null
  }

  @throws[java.awt.AWTException]
  override def createCompatibleVolatileImage(width: Int, height: Int, caps: ImageCapabilities, transparency: Int): awt.image.VolatileImage = {
    log("[+] createCompatibleVolatileImage " + width + "x" + height + " " + caps + " " + transparency)
    new SkiaVolatileImage(width, height, caps, transparency)
  }
}

class SkiaVolatileImage(val width: Int, val height: Int, val caps: ImageCapabilities, transparency: Int) extends awt.image.VolatileImage {
  log("new SkiaVolatileImage(%d, %d, %s, %d)", width, height, caps, transparency)
  this.surface = Surface.makeRaster(ImageInfo.makeN32Premul(width, height))
  final var surface: Surface = null

  def log(format: String, args: Any*): Unit = {

    //        System.out.println("VI " + String.format(format, args));
  }

  override def getSnapshot: awt.image.BufferedImage = {
    log("getSnapshot")
    null
  }

  override def getWidth: Int = {
    log("[+] getWidth => " + width)
    width
  }

  override def getHeight: Int = {
    log("[+] getHeight => " + height)
    height
  }

  override def createGraphics: Graphics2D = {
    log("createGraphics")
    new SkiaGraphics(surface.getCanvas)
  }

  override def validate(gc: GraphicsConfiguration): Int = {
    log("validate")
    0
  }

  override def contentsLost: Boolean = {
    log("contentLost")
    false
  }

  override def getCapabilities: ImageCapabilities = {
    log("[+] getCapabilities")
    caps
  }

  override def getWidth(observer: awtimage.ImageObserver): Int = {
    log("[+] getWidth(ImageObserver) => " + width)
    width
  }

  override def getHeight(observer: awtimage.ImageObserver): Int = {
    log("[+] getHeight(ImageObserver) => " + height)
    height
  }

  override def getProperty(name: String, observer: awtimage.ImageObserver): AnyRef = {
    log("getProperty(" + name + ", ImageObserver)")
    null
  }
}