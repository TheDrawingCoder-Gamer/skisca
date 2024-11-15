package gay.menkissing.skisca.skottie

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

/**
 * <p>A Logger subclass can be used to receive
 * [[gay.menkissing.skisca.skottie.AnimationBuilder]] parsing errors and warnings.</p>
 */
object Logger {
  @ApiStatus.Internal
  @native def _nMake(): Long

  @ApiStatus.Internal
  @native final def _nInit(self: Logger, ptr: Long): Unit
  Library.staticLoad()
}

abstract class Logger extends RefCnt(Logger._nMake(), true) {
  Stats.onNativeCall()
  Stats.onNativeCall()
  Logger._nInit(this, _ptr)

  @ApiStatus.OverrideOnly def log(level: LogLevel, message: String, @Nullable json: String): Unit


}