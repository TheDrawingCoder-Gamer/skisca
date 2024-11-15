package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

import java.util.function.*

object Picture {
  /**
   * Recreates Picture that was serialized into data. Returns constructed Picture
   * if successful; otherwise, returns null. Fails if data does not permit
   * constructing valid Picture.
   */
  @Nullable def makeFromData(data: Data): Picture = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeFromData(Native.getPtr(data))
      if (ptr == 0) {
        null
      } else {
        new Picture(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(data)
    }
  }

  /**
   * <p>Returns a placeholder Picture. Result does not draw, and contains only
   * cull Rect, a hint of its bounds. Result is immutable; it cannot be changed
   * later. Result identifier is unique.</p>
   *
   * <p>Returned placeholder can be intercepted during playback to insert other
   * commands into Canvas draw stream.</p>
   *
   * @param cull placeholder dimensions
   * @return placeholder with unique identifier
   * @see <a href="https://fiddle.skia.org/c/@Picture_MakePlaceholder">https://fiddle.skia.org/c/@Picture_MakePlaceholder</a>
   */
  def makePlaceholder(@NotNull cull: Rect): Picture = {
    Stats.onNativeCall()
    new Picture(_nMakePlaceholder(cull._left, cull._top, cull._right, cull._bottom))
  }

  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long): Long

  @ApiStatus.Internal
  @native def _nPlayback(ptr: Long, canvasPtr: Long, @Nullable abort: BooleanSupplier): Unit

  @ApiStatus.Internal
  @native def _nGetCullRect(ptr: Long): Rect

  @ApiStatus.Internal
  @native def _nGetUniqueId(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nSerializeToData(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nMakePlaceholder(left: Float, top: Float, right: Float, bottom: Float): Long

  @ApiStatus.Internal
  @native def _nGetApproximateOpCount(ptr: Long): Int

  @ApiStatus.Internal
  @native def _nGetApproximateBytesUsed(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nMakeShader(ptr: Long, tmx: Int, tmy: Int, filterMode: Int, localMatrix: Array[Float], tileRect: Rect): Long

  try Library.staticLoad()
}

class Picture @ApiStatus.Internal(ptr: Long) extends RefCnt(ptr) {
  /**
   * <p>Replays the drawing commands on the specified canvas. In the case that the
   * commands are recorded, each command in the Picture is sent separately to canvas.</p>
   *
   * <p>To add a single command to draw Picture to recording canvas, call
   * {@link Canvas# drawPicture} instead.</p>
   *
   * @param canvas receiver of drawing commands
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Picture_playback">https://fiddle.skia.org/c/@Picture_playback</a>
   */
  def playback(canvas: Canvas): Picture = {
    playback(canvas, null)
  }

  /**
   * <p>Replays the drawing commands on the specified canvas. In the case that the
   * commands are recorded, each command in the Picture is sent separately to canvas.</p>
   *
   * <p>To add a single command to draw Picture to recording canvas, call
   * {@link Canvas# drawPicture} instead.</p>
   *
   * @param canvas receiver of drawing commands
   * @param abort  return true to interrupt the playback
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Picture_playback">https://fiddle.skia.org/c/@Picture_playback</a>
   */
  def playback(canvas: Canvas, @Nullable abort: BooleanSupplier): Picture = {
    try {
      Stats.onNativeCall()
      Picture._nPlayback(_ptr, Native.getPtr(canvas), abort)
      this
    } finally {
      ReferenceUtil.reachabilityFence(canvas)
    }
  }

  /**
   * <p>Returns cull Rect for this picture, passed in when Picture was created.
   * Returned Rect does not specify clipping Rect for Picture; cull is hint
   * of Picture bounds.</p>
   *
   * <p>Picture is free to discard recorded drawing commands that fall outside cull.</p>
   *
   * @return bounds passed when Picture was created
   * @see <a href="https://fiddle.skia.org/c/@Picture_cullRect">https://fiddle.skia.org/c/@Picture_cullRect</a>
   */
  def getCullRect: Rect = {
    try {
      Stats.onNativeCall()
      Picture._nGetCullRect(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns a non-zero value unique among Picture in Skia process.
   *
   * @return identifier for Picture
   */
  def getUniqueId: Int = {
    try {
      Stats.onNativeCall()
      Picture._nGetUniqueId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return storage containing Data describing Picture.
   * @see <a href="https://fiddle.skia.org/c/@Picture_serialize">https://fiddle.skia.org/c/@Picture_serialize</a>
   */
  def serializeToData: Data = {
    try {
      Stats.onNativeCall()
      new Data(Picture._nSerializeToData(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the approximate number of operations in SkPicture. Returned value
   * may be greater or less than the number of SkCanvas calls
   * recorded: some calls may be recorded as more than one operation, other
   * calls may be optimized away.
   *
   * @return approximate operation count
   * @see <a href="https://fiddle.skia.org/c/@Picture_approximateOpCount">https://fiddle.skia.org/c/@Picture_approximateOpCount</a>
   */
  def getApproximateOpCount: Int = {
    try {
      Stats.onNativeCall()
      Picture._nGetApproximateOpCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the approximate byte size of Picture. Does not include large objects
   * referenced by Picture.
   *
   * @return approximate size
   * @see <a href="https://fiddle.skia.org/c/@Picture_approximateBytesUsed">https://fiddle.skia.org/c/@Picture_approximateBytesUsed</a>
   */
  def getApproximateBytesUsed: Long = {
    try {
      Stats.onNativeCall()
      Picture._nGetApproximateBytesUsed(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Return a new shader that will draw with this picture. The tile rect is considered
   * equal to the picture bounds.
   *
   * @param tmx  The tiling mode to use when sampling in the x-direction.
   * @param tmy  The tiling mode to use when sampling in the y-direction.
   * @param mode How to filter the tiles
   * @return Returns a new shader object. Note: this function never returns null.
   */
  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode, @NotNull mode: FilterMode): Shader = {
    makeShader(tmx, tmy, mode, null, null)
  }

  /**
   * Return a new shader that will draw with this picture. The tile rect is considered
   * equal to the picture bounds.
   *
   * @param tmx         The tiling mode to use when sampling in the x-direction.
   * @param tmy         The tiling mode to use when sampling in the y-direction.
   * @param mode        How to filter the tiles
   * @param localMatrix Optional matrix used when sampling
   * @return Returns a new shader object. Note: this function never returns null.
   */
  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode, @NotNull mode: FilterMode, @Nullable localMatrix: Matrix33): Shader = {
    makeShader(tmx, tmy, mode, localMatrix, null)
  }

  /**
   * Return a new shader that will draw with this picture.
   *
   * @param tmx         The tiling mode to use when sampling in the x-direction.
   * @param tmy         The tiling mode to use when sampling in the y-direction.
   * @param mode        How to filter the tiles
   * @param localMatrix Optional matrix used when sampling
   * @param tileRect    The tile rectangle in picture coordinates: this represents the subset
   *                    (or superset) of the picture used when building a tile. It is not
   *                    affected by localMatrix and does not imply scaling (only translation
   *                    and cropping). If null, the tile rect is considered equal to the picture
   *                    bounds.
   * @return Returns a new shader object. Note: this function never returns null.
   */
  @NotNull def makeShader(@NotNull tmx: FilterTileMode, @NotNull tmy: FilterTileMode, @NotNull mode: FilterMode, @Nullable localMatrix: Matrix33, @Nullable tileRect: Rect): Shader = {
    try {
      Stats.onNativeCall()
      val arr = if (localMatrix == null) {
        null
      } else {
        localMatrix.mat
      }
      new Shader(Picture._nMakeShader(_ptr, tmx.ordinal, tmy.ordinal, mode.ordinal, arr, tileRect))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}