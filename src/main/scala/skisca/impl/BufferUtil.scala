package gay.menkissing.skisca.impl

import java.nio.ByteBuffer

object BufferUtil {
  def getByteBufferFromPointer(ptr: Long, size: Int): ByteBuffer = {
    val result = _nGetByteBufferFromPointer(ptr, size)
    if (result == null) throw new IllegalArgumentException("JNI direct buffer access not support by current JVM!")
    result
  }

  def getPointerFromByteBuffer(buffer: ByteBuffer): Long = {
    val result = _nGetPointerFromByteBuffer(buffer)
    if (result == 0) throw new IllegalArgumentException("The given buffer " + buffer + "is not a direct buffer or current JVM doesn't support JNI direct buffer access!")
    result
  }

  @native def _nGetByteBufferFromPointer(ptr: Long, size: Int): ByteBuffer

  @native def _nGetPointerFromByteBuffer(buffer: ByteBuffer): Long

  Library.staticLoad()
}