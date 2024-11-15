package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

import java.nio.ByteBuffer

/**
 * Data holds an immutable data buffer.
 */
object Data {
  def makeFromBytes(bytes: Array[Byte]): Data = makeFromBytes(bytes, 0, bytes.length)

  def makeFromBytes(bytes: Array[Byte], offset: Long, length: Long): Data = {
    Stats.onNativeCall()
    new Data(_nMakeFromBytes(bytes, offset, length))
  }

  /**
   * Create a new dataref the file with the specified path.
   * If the file cannot be opened, this returns null.
   */
  def makeFromFileName(path: String): Data = {
    Stats.onNativeCall()
    new Data(_nMakeFromFileName(path))
  }

  /**
   * Returns a new empty dataref (or a reference to a shared empty dataref).
   * New or shared, the caller must see that {@link # close ( )} is eventually called.
   */
  def makeEmpty: Data = {
    Stats.onNativeCall()
    new Data(_nMakeEmpty)
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nGetFinalizer: Long

  @native def _nSize(ptr: Long): Long

  @native def _nBytes(ptr: Long, offset: Long, length: Long): Array[Byte]

  @native def _nEquals(ptr: Long, otherPtr: Long): Boolean

  @native def _nToByteBuffer(ptr: Long): ByteBuffer

  @native def _nMakeFromBytes(bytes: Array[Byte], offset: Long, length: Long): Long

  @native def _nMakeFromFileName(path: String): Long

  @native def _nMakeSubset(ptr: Long, offset: Long, length: Long): Long

  @native def _nMakeEmpty: Long

  try Library.staticLoad()
}

class Data @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Data._FinalizerHolder.PTR) {
  def getSize: Long = {
    try {
      Stats.onNativeCall()
      Data._nSize(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getBytes: Array[Byte] = getBytes(0, getSize)

  def getBytes(offset: Long, length: Long): Array[Byte] = {
    try {
      Stats.onNativeCall()
      Data._nBytes(_ptr, offset, length)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if these two objects have the same length and contents,
   * effectively returning 0 == memcmp(...)
   */
  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Stats.onNativeCall()
      Data._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  /**
   * Create a new dataref using a subset of the data in the specified
   * src dataref.
   */
  def makeSubset(offset: Long, length: Long): Data = {
    try {
      Stats.onNativeCall()
      new Data(Data._nMakeSubset(_ptr, offset, length))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def makeCopy: Data = {
    try {
      Stats.onNativeCall()
      new Data(Data._nMakeSubset(_ptr, 0, getSize))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def toByteBuffer: ByteBuffer = {
    try {
      Stats.onNativeCall()
      Data._nToByteBuffer(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}