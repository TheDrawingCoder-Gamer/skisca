package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object DirectContext {
  @NotNull
  @Contract("-> new") def makeGL: DirectContext = {
    Stats.onNativeCall()
    new DirectContext(_nMakeGL)
  }

  @NotNull
  @Contract("-> this") def makeMetal(devicePtr: Long, queuePtr: Long): DirectContext = {
    Stats.onNativeCall()
    new DirectContext(_nMakeMetal(devicePtr, queuePtr))
  }

  /**
   * <p>Creates Direct3D direct rendering context from D3D12 native objects.</p>
   * <p>For more information refer to skia GrDirectContext class.</p>
   *
   * @param adapterPtr pointer to IDXGIAdapter1 object; must be not zero
   * @param devicePtr  pointer to ID3D12Device objetc, which is created with
   *                   provided adapter in adapterPtr; must be not zero
   * @param queuePtr   Pointer to ID3D12CommandQueue object, which
   *                   is created with provided device in devicePtr with
   *                   type D3D12_COMMAND_LIST_TYPE_DIRECT; must be not zero
   */
  @NotNull
  @Contract("-> this") def makeDirect3D(adapterPtr: Long, devicePtr: Long, queuePtr: Long): DirectContext = {
    Stats.onNativeCall()
    new DirectContext(_nMakeDirect3D(adapterPtr, devicePtr, queuePtr))
  }

  @ApiStatus.Internal
  @native def _nMakeGL: Long

  @ApiStatus.Internal
  @native def _nMakeMetal(devicePtr: Long, queuePtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeDirect3D(adapterPtr: Long, devicePtr: Long, queuePtr: Long): Long

  @ApiStatus.Internal
  @native def _nFlush(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nSubmit(ptr: Long, syncCpu: Boolean): Long

  @ApiStatus.Internal
  @native def _nReset(ptr: Long, flags: Int): Unit

  @ApiStatus.Internal
  @native def _nAbandon(ptr: Long): Unit

  try Library.staticLoad()
}

class DirectContext @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr, true) {
  @NotNull
  @Contract("-> this") def flush: DirectContext = {
    Stats.onNativeCall()
    DirectContext._nFlush(_ptr)
    this
  }

  @NotNull
  @Contract("-> this") def resetAll: DirectContext = {
    Stats.onNativeCall()
    DirectContext._nReset(_ptr, -1)
    this
  }

  @NotNull
  @Contract("-> this") def resetGLAll: DirectContext = {
    Stats.onNativeCall()
    DirectContext._nReset(_ptr, 0xffff)
    this
  }

  @NotNull
  @Contract("_ -> this") def resetGL(states: GLBackendState*): DirectContext = {
    Stats.onNativeCall()
    var flags = 0
    for (state <- states) {
      flags |= state._bit
    }
    DirectContext._nReset(_ptr, flags)
    this
  }

  /**
   * <p>Submit outstanding work to the gpu from all previously un-submitted flushes.</p>
   * <p>If the syncCpu flag is true this function will return once the gpu has finished with all submitted work.</p>
   * <p>For more information refer to skia GrDirectContext::submit(bool syncCpu) method.</p>
   *
   * @param syncCpu flag to sync cpu and gpu work submission
   */
  def submit(syncCpu: Boolean): Unit = {
    Stats.onNativeCall()
    DirectContext._nSubmit(_ptr, syncCpu)
  }

  /**
   * <p>Abandons all GPU resources and assumes the underlying backend 3D API context is no longer
   * usable. Call this if you have lost the associated GPU context, and thus internal texture,
   * buffer, etc. references/IDs are now invalid. Calling this ensures that the destructors of the
   * context and any of its created resource objects will not make backend 3D API calls. Content
   * rendered but not previously flushed may be lost. After this function is called all subsequent
   * calls on the context will fail or be no-ops.</p>
   *
   * <p>The typical use case for this function is that the underlying 3D context was lost and further
   * API calls may crash.</p>
   *
   * <p>For Vulkan, even if the device becomes lost, the VkQueue, VkDevice, or VkInstance used to
   * create the context must be kept alive even after abandoning the context. Those objects must
   * live for the lifetime of the context object itself. The reason for this is so that
   * we can continue to delete any outstanding GrBackendTextures/RenderTargets which must be
   * cleaned up even in a device lost state.</p>
   */
  def abandon(): Unit = {
    try {
      Stats.onNativeCall()
      DirectContext._nAbandon(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}