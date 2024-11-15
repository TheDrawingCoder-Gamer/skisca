package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.svg.*
import io.github.humbleui.types.*

class SVGScalingScene extends Scene {
  final val border = Paint().setColor(0xFF3333CC).setMode(PaintMode.STROKE).setStrokeWidth(1)
  final val fill = Paint().setColor(0xFFCC3333)

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(40, 60)
    canvas.save
    for (img <- Array[String]("images/svg/bug_none.svg", "images/svg/bug_viewport.svg", "images/svg/bug_viewbox.svg", "images/svg/bug_viewport_viewbox.svg")) {
      canvas.drawString(img, 0, -20, Scene.inter13, fill)
      {
        val data = Data.makeFromFileName(Scene.file(img))
        val svg = new SVGDOM(data)
        try {
          val containerSize = svg.getRoot.getIntrinsicSize(new SVGLengthContext(width, height, 90f))
          if (!containerSize.isEmpty) drawIcon(canvas, containerSize, svg)
          drawIcon(canvas, new Point(8, 8), svg)
          drawIcon(canvas, new Point(16, 16), svg)
          drawIcon(canvas, new Point(32, 32), svg)
          drawIcon(canvas, new Point(64, 64), svg)
        } finally {
          if (data != null) data.close()
          if (svg != null) svg.close()
        }
      }
      canvas.translate(40, 0)
    }
    canvas.restore
    canvas.translate(0, 100)
    canvas.save
    canvas.scale(3, 3)
    {
      val data = Data.makeFromFileName(Scene.file("images/svg/ratio.svg"))
      val dom = new SVGDOM(data)
      try {
        // (width > height) meet
        drawRatio(canvas, dom, 0, 0, 20, 10, SVGPreserveAspectRatioAlign.XMID_YMID, SVGPreserveAspectRatioScale.MEET)
        drawRatio(canvas, dom, 25, 0, 20, 10, SVGPreserveAspectRatioAlign.XMIN_YMID, SVGPreserveAspectRatioScale.MEET)
        drawRatio(canvas, dom, 50, 0, 20, 10, SVGPreserveAspectRatioAlign.XMAX_YMID, SVGPreserveAspectRatioScale.MEET)
        // (width > height) slice
        drawRatio(canvas, dom, 0, 15, 20, 10, SVGPreserveAspectRatioAlign.XMID_YMID, SVGPreserveAspectRatioScale.SLICE)
        drawRatio(canvas, dom, 25, 15, 20, 10, SVGPreserveAspectRatioAlign.XMID_YMIN, SVGPreserveAspectRatioScale.SLICE)
        drawRatio(canvas, dom, 50, 15, 20, 10, SVGPreserveAspectRatioAlign.XMID_YMAX, SVGPreserveAspectRatioScale.SLICE)
        // (width < height) meet
        drawRatio(canvas, dom, 75, 0, 10, 25, SVGPreserveAspectRatioAlign.XMID_YMIN, SVGPreserveAspectRatioScale.MEET)
        drawRatio(canvas, dom, 90, 0, 10, 25, SVGPreserveAspectRatioAlign.XMIN_YMID, SVGPreserveAspectRatioScale.MEET)
        drawRatio(canvas, dom, 105, 0, 10, 25, SVGPreserveAspectRatioAlign.XMAX_YMAX, SVGPreserveAspectRatioScale.MEET)
        // (width < height) slice
        drawRatio(canvas, dom, 120, 0, 10, 25, SVGPreserveAspectRatioAlign.XMIN_YMID, SVGPreserveAspectRatioScale.SLICE)
        drawRatio(canvas, dom, 135, 0, 10, 25, SVGPreserveAspectRatioAlign.XMID_YMID, SVGPreserveAspectRatioScale.SLICE)
        drawRatio(canvas, dom, 150, 0, 10, 25, SVGPreserveAspectRatioAlign.XMAX_YMID, SVGPreserveAspectRatioScale.SLICE)
        // none
        drawRatio(canvas, dom, 0, 30, 160, 60, SVGPreserveAspectRatioAlign.NONE, SVGPreserveAspectRatioScale.MEET)
        drawRatio(canvas, dom, 0, 100, 160, 60, SVGPreserveAspectRatioAlign.NONE, SVGPreserveAspectRatioScale.SLICE)
      } finally {
        if (data != null) data.close()
        if (dom != null) dom.close()
      }
    }
    canvas.restore
  }

  def drawIcon(canvas: Canvas, bounds: Point, svg: SVGDOM): Unit = {
    try {
      val root = svg.getRoot
      try {
        val lc = new SVGLengthContext(bounds)
        val width = lc.resolve(root.getWidth, SVGLengthType.HORIZONTAL)
        val height = lc.resolve(root.getHeight, SVGLengthType.VERTICAL)
        svg.setContainerSize(bounds)
        val scale = Math.min(bounds.getX / width, bounds.getY / height)
        canvas.save
        canvas.scale(scale, scale)
        svg.render(canvas)
        canvas.restore
      } finally {
        if (root != null) root.close()
      }
    }
    canvas.drawRect(Rect.makeWH(bounds.getX, bounds.getY), border)
    canvas.drawString(String.format("%.0f×%.0f", bounds.getX, bounds.getY), 0, bounds.getY + 20, Scene.inter13, fill)
    canvas.translate(bounds.getX + 30, 0)
  }

  def drawRatio(canvas: Canvas, dom: SVGDOM, x: Float, y: Float, w: Float, h: Float, align: SVGPreserveAspectRatioAlign, scale: SVGPreserveAspectRatioScale): Unit = {
    canvas.save
    canvas.translate(x, y)
    canvas.clipRect(Rect.makeXYWH(0, 0, w, h))
    canvas.clear(0xFFEEEEEE)
    try {
      val root = dom.getRoot
      try {
        root.setWidth(new SVGLength(w)).setHeight(new SVGLength(h))
            .setPreserveAspectRatio(new SVGPreserveAspectRatio(align, scale))
      } finally {
        if (root != null) root.close()
      }
    }
    dom.render(canvas)
    canvas.restore
  }
}