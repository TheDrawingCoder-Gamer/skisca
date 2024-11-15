package gay.menkissing.skisca

import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * Information about individual frames in a multi-framed image.
 */
case class AnimationFrameInfo(requiredFrame: Int, duration: Int, fullyReceived: Boolean, alphaTypeOrdinal: Int, hasAlphaWithinBounds: Boolean, disposalMethodOrdinal: Int, blendModeOrdinal: Int, frameRect: IRect)