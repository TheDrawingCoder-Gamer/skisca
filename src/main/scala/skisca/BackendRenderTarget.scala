package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object BackendRenderTarget {
  @NotNull
  @Contract("_, _, _, _, _, _ -> new") def makeGL(width: Int, height: Int, sampleCnt: Int, stencilBits: Int, fbId: Int, fbFormat: Int): BackendRenderTarget = {
    Stats.onNativeCall()
    new BackendRenderTarget(_nMakeGL(width, height, sampleCnt, stencilBits, fbId, fbFormat))
  }

  @NotNull
  @Contract("_, _, _ -> new") def makeMetal(width: Int, height: Int, texturePtr: Long): BackendRenderTarget = {
    Stats.onNativeCall()
    new BackendRenderTarget(_nMakeMetal(width, height, texturePtr))
  }

  /**
   * <p>Creates Direct3D backend render target object from D3D12 texture resource.</p>
   * <p>For more information refer to skia GrBackendRenderTarget class.</p>
   *
   * @param width      width of the render target in pixels
   * @param height     height of the render target in pixels
   * @param texturePtr pointer to ID3D12Resource texture resource object; must be not zero
   * @param format     pixel data DXGI_FORMAT fromat of the texturePtr resource
   * @param sampleCnt  samples count for texture resource
   * @param levelCnt   sampling quality level for texture resource
   */
  @NotNull
  @Contract("_, _, _, _, _, _ -> new") def makeDirect3D(width: Int, height: Int, texturePtr: Long, format: Int, sampleCnt: Int, levelCnt: Int): BackendRenderTarget = {
    Stats.onNativeCall()
    new BackendRenderTarget(_nMakeDirect3D(width, height, texturePtr, format, sampleCnt, levelCnt))
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMakeGL(width: Int, height: Int, sampleCnt: Int, stencilBits: Int, fbId: Int, fbFormat: Int): Long

  @ApiStatus.Internal
  @native def _nMakeMetal(width: Int, height: Int, texturePtr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeDirect3D(width: Int, height: Int, texturePtr: Long, format: Int, sampleCnt: Int, levelCnt: Int): Long

  try Library.staticLoad()
}

class BackendRenderTarget @ApiStatus.Internal(ptr: Long) extends Managed(ptr, BackendRenderTarget._FinalizerHolder
                                                                                                 .PTR) {
}