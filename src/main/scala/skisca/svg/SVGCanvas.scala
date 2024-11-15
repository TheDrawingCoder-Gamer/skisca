package gay.menkissing.skisca.svg

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object SVGCanvas {
  /**
   * Returns a new canvas that will generate SVG commands from its draw calls, and send
   * them to the provided stream. Ownership of the stream is not transfered, and it must
   * remain valid for the lifetime of the returned canvas.
   *
   * The canvas may buffer some drawing calls, so the output is not guaranteed to be valid
   * or complete until the canvas instance is deleted.
   *
   * @param bounds defines an initial SVG viewport (viewBox attribute on the root SVG element).
   * @param out    stream SVG commands will be written to
   * @return new Canvas
   */
  def make(bounds: Rect, out: WStream): Canvas = {
    make(bounds, out, false, true)
  }

  /**
   * Returns a new canvas that will generate SVG commands from its draw calls, and send
   * them to the provided stream. Ownership of the stream is not transfered, and it must
   * remain valid for the lifetime of the returned canvas.
   *
   * The canvas may buffer some drawing calls, so the output is not guaranteed to be valid
   * or complete until the canvas instance is deleted.
   *
   * @param bounds             defines an initial SVG viewport (viewBox attribute on the root SVG element).
   * @param out                stream SVG commands will be written to
   * @param convertTextToPaths emit text as &lt;path&gt;s
   * @param prettyXML          add newlines and tabs in output
   * @return new Canvas
   */
  @NotNull
  @Contract("_, _, _, _ -> new") def make(@NotNull bounds: Rect, @NotNull out: WStream, convertTextToPaths: Boolean, prettyXML: Boolean): Canvas = {
    Stats.onNativeCall()
    val ptr = _nMake(bounds._left, bounds._top, bounds._right, bounds._bottom, Native
      .getPtr(out), 0 | (if (convertTextToPaths) {
      1
    } else {
      0
    }) | (if (prettyXML) {
      0
    } else {
      2
    }))
    new Canvas(ptr, true, out)
  }

  @ApiStatus.Internal
  @native def _nMake(left: Float, top: Float, right: Float, bottom: Float, wstreamPtr: Long, flags: Int): Long

  Library.staticLoad()
}