package gay.menkissing.skisca.paragraph

import org.jetbrains.annotations.*

enum HeightMode extends Enum[HeightMode] {
  case ALL, DISABLE_FIRST_ASCENT, DISABLE_LAST_DESCENT, DISABLE_ALL
}