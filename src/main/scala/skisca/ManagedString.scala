package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object ManagedString {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nMake(s: String): Long

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nToString(ptr: Long): String

  @ApiStatus.Internal
  @native def _nInsert(ptr: Long, offset: Int, s: String): Unit

  @ApiStatus.Internal
  @native def _nAppend(ptr: Long, s: String): Unit

  @ApiStatus.Internal
  @native def _nRemoveSuffix(ptr: Long, from: Int): Unit

  @ApiStatus.Internal
  @native def _nRemove(ptr: Long, from: Int, length: Int): Unit

  try Library.staticLoad()
}

class ManagedString @ApiStatus.Internal(ptr: Long) extends Managed(ptr, ManagedString._FinalizerHolder.PTR) {
  def this(s: String) = {
    this(ManagedString._nMake(s))
    Stats.onNativeCall()
  }

  override def toString: String = {
    try {
      Stats.onNativeCall()
      ManagedString._nToString(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("-> this") def insert(offset: Int, @NotNull s: String): ManagedString = {
    Stats.onNativeCall()
    ManagedString._nInsert(_ptr, offset, s)
    this
  }

  @NotNull
  @Contract("-> this") def append(@NotNull s: String): ManagedString = {
    Stats.onNativeCall()
    ManagedString._nAppend(_ptr, s)
    this
  }

  @NotNull
  @Contract("-> this") def remove(from: Int): ManagedString = {
    Stats.onNativeCall()
    ManagedString._nRemoveSuffix(_ptr, from)
    this
  }

  @NotNull
  @Contract("-> this") def remove(from: Int, length: Int): ManagedString = {
    Stats.onNativeCall()
    ManagedString._nRemove(_ptr, from, length)
    this
  }
}