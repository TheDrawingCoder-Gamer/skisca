package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

object ShapingOptions {
  val DEFAULT = new ShapingOptions(null, List(), true, true, true)
}

case class ShapingOptions(fontMgr: FontMgr, features: List[FontFeature], leftToRight: Boolean, approximateSpaces: Boolean, approximatePunctuation: Boolean) {
  @NotNull def withFeatures(features: List[FontFeature]): ShapingOptions = copy(features = features)

  @NotNull def withFeatures(@Nullable featuresString: String): ShapingOptions = {
    if (featuresString == null) {
      withFeatures(List())
    } else {
      withFeatures(FontFeature.parse(featuresString).toList)
    }
  }

  def featuresArray: Array[FontFeature] = features.toArray
}