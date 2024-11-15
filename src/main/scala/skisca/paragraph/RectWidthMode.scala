package gay.menkissing.skisca.paragraph

import org.jetbrains.annotations.*

enum RectWidthMode extends Enum[RectWidthMode] {
  case

  /** Provide tight bounding boxes that fit widths to the runs of each line independently. */
  TIGHT,

  /** Extends the width of the last rect of each line to match the position of the widest rect over all the lines. */
  MAX
}