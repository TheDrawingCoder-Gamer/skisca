package gay.menkissing.skisca.paragraph

import io.github.humbleui.types.Rect

case class TextBox(rect: Rect, direction: Direction) {
  def this(l: Float, t: Float, r: Float, b: Float, direction: Int) = {
    this(Rect.makeLTRB(l, t, r, b), Direction.fromOrdinal(direction))
  }
}