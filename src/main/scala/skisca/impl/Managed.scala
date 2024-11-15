package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

object Managed {
  class CleanerThunk(var _className: String, var _ptr: Long, var _finalizerPtr: Long) extends Runnable {
    override def run(): Unit = {
      Log.trace(() => "Cleaning " + _className + " " + java.lang.Long.toString(_ptr, 16))
      Stats.onDeallocated(_className)
      Stats.onNativeCall()
      _nInvokeFinalizer(_finalizerPtr, _ptr)
    }
  }

  @native def _nInvokeFinalizer(finalizer: Long, ptr: Long): Unit
}

abstract class Managed(ptr: Long, finalizer: Long, managed: Boolean = true) extends Native(ptr) with AutoCloseable {
  @ApiStatus.Internal var _cleanable: Cleanable = null
  if (managed) {
    assert(ptr != 0, "Managed ptr is 0")
    assert(finalizer != 0, "Managed finalizer is 0")
    val className = getClass.getSimpleName
    Stats.onAllocated(className)
    this._cleanable = Cleanable.register(this, new Managed.CleanerThunk(className, ptr, finalizer))
  }


  override def close(): Unit = {
    if (0 == _ptr) {
      throw new RuntimeException("Object already closed: " + getClass + ", _ptr=" + _ptr)
    } else if (null == _cleanable) {
      throw new RuntimeException("Object is not managed in JVM, can't close(): " + getClass + ", _ptr=" + _ptr)
    } else {
      _cleanable.clean()
      _cleanable = null
      _ptr = 0
    }
  }

  def isClosed: Boolean = _ptr == 0
}