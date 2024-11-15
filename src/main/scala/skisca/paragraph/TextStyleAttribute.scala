package gay.menkissing.skisca.paragraph

import org.jetbrains.annotations.*

enum TextStyleAttribute extends Enum[TextStyleAttribute] {
  case NONE, ALL_ATTRIBUTES, FONT, FOREGROUND, BACKGROUND, SHADOW, DECORATIONS, LETTER_SPACING, WORD_SPACING, FONT_EXACT
}