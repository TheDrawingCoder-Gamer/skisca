package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

import java.io.*

object OutputWStream {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(out: OutputStream): Long

  Library.staticLoad()
}

class OutputWStream(@ApiStatus.Internal val _out: OutputStream) extends WStream(OutputWStream._nMake(_out), OutputWStream
  ._FinalizerHolder.PTR) {
  Stats.onNativeCall()
}