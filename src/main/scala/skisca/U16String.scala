package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

/**
 * Java mirror of std::vector&lt;jchar&gt; (UTF-16)
 */
object U16String {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  def apply(s: String): U16String = {
    val str = new U16String(U16String._nMake(s))
    Stats.onNativeCall()
    str
  }
  
  @ApiStatus.Internal
  @native def _nMake(s: String): Long

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nToString(ptr: Long): String

  try Library.staticLoad()
}

class U16String @ApiStatus.Internal(ptr: Long) extends Managed(ptr, U16String._FinalizerHolder.PTR) {
  

  override def toString: String = {
    try {
      Stats.onNativeCall()
      U16String._nToString(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}