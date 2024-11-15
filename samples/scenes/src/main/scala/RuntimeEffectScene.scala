package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.io.*
import java.nio.*
import java.nio.file.{Path, *}

class RuntimeEffectScene extends Scene {
  final var _texture: Image = null
  final var _effectForShader: RuntimeEffect = null
  final var _effectForColorFilter: RuntimeEffect = null

  try {
    _texture = Image.makeDeferredFromEncodedBytes(Files.readAllBytes(Path.of(Scene.file("images/triangle.png"))))
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }
  _effectForShader = RuntimeEffect
    .makeForShader("uniform float xScale;\n" + "uniform float xBias;\n" + "uniform float yScale;\n" + "uniform float yBias;\n" + "uniform shader image;\n" + "half4 main(float2 xy) {\n" + "  half4 tex = image.eval(mod(xy, 100));\n" + "  return half4((xy.x - xBias) /  xScale / 2 + 0.5, (xy.y - yBias) / yScale / 2 + 0.5, tex.b, 1);\n" + "}")
  _effectForColorFilter = RuntimeEffect
    .makeForColorFilter("uniform float xScale;\n" + "uniform float xBias;\n" + "uniform float yScale;\n" + "uniform float yBias;\n" + "half4 main(half4 p) {\n" + "  return half4(xBias / xScale * p.r, yBias / yScale * p.g, p.b, p.a);\n" + "}")

  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    val bb = ByteBuffer.allocate(4 * 4).order(ByteOrder.nativeOrder)
    bb.putFloat(width.toFloat)
    bb.putFloat(xpos.toFloat)
    bb.putFloat(height.toFloat)
    bb.putFloat(ypos.toFloat)
    canvas.save
    {
      val data = Data.makeFromBytes(bb.array)
      try {
        canvas.translate(20, 20)
        {
          val child = _texture.makeShader
          val shader = _effectForShader.makeShader(data, Array[Shader](child), null)
          val paint = Paint().setShader(shader)
          try {
            canvas.drawRect(Rect.makeXYWH(0, 0, 200, 200), paint)
            canvas.drawString("makeForShader", 0, 220, Scene.inter13, Scene.blackFill)
            canvas.translate(220, 0)
          } finally {
            if (child != null) child.close()
            if (shader != null) shader.close()
            if (paint != null) paint.close()
          }
        }
        {
          val filter = _effectForColorFilter.makeColorFilter(data, null)
          val paint = Paint().setColorFilter(filter)
          try {
            canvas.drawImageRect(_texture, Rect.makeXYWH(0, 0, 200, 200), paint)
            canvas.drawString("makeForColorFilter", 0, 220, Scene.inter13, Scene.blackFill)
          } finally {
            if (filter != null) filter.close()
            if (paint != null) paint.close()
          }
        }
      } finally {
        if (data != null) data.close()
      }
    }
  }
}