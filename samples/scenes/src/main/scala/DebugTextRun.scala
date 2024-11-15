package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

case class DebugTextRun(
  info: RunInfo,
  font: Font,
  bounds: Rect,
  glyphs: Array[Short],
  positions: Array[Point],
  clusters: Array[Int]
)