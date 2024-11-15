package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

object Native {
  def getPtr(n: Native): Long = {
    if (n == null) {
      0
    } else {
      n._ptr
    }
  }
}

abstract class Native(var _ptr: Long) {
  if (_ptr == 0) throw new RuntimeException("Can't wrap nullptr")

  override def toString: String = getClass.getSimpleName + "(_ptr=0x" + java.lang.Long.toString(_ptr, 16) + ")"

  override def equals(other: Any): Boolean = {
    try {
      if (null == other) return false
      if (!getClass.isInstance(other)) return false
      val nOther = other.asInstanceOf[Native]
      if (_ptr == nOther._ptr) return true
      _nativeEquals(nOther)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  @ApiStatus.Internal def _nativeEquals(other: Native) = false

  // FIXME two different pointers might point to equal objects
  override def hashCode: Int = {
    java.lang.Long.hashCode(_ptr)
  }
}