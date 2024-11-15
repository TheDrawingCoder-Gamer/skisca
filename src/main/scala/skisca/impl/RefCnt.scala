package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

object RefCnt {
  Library.load()
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer()
  }

  @native def _nGetFinalizer(): Long

  @native def _nGetRefCount(ptr: Long): Int
}

abstract class RefCnt(ptr: Long, allowClose: Boolean = true) extends Managed(ptr, RefCnt._FinalizerHolder.PTR, allowClose) {
  def getRefCount: Int = {
    try {
      Stats.onNativeCall()
      RefCnt._nGetRefCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  override def toString: String = {
    val s = super.toString
    s.substring(0, s.length - 1) + ", refCount=" + getRefCount + ")"
  }
}