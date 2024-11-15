package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object PictureRecorder {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  /**
   * <p>Signal that the caller is done recording. This invalidates the canvas returned by
   * {@link # beginRecording ( Rect )}/{@link # getRecordingCanvas ( )}.</p>
   *
   * <p>Unlike {@link # finishRecordingAsPicture ( )}, which returns an immutable picture,
   * the returned drawable may contain live references to other drawables (if they were added to
   * the recording canvas) and therefore this drawable will reflect the current state of those
   * nested drawables anytime it is drawn or a new picture is snapped from it (by calling
   * {@link Drawable# makePictureSnapshot ( )}).</p>
   */
  // public Drawable finishRecordingAsPicture(@NotNull Rect cull) {
  //     Stats.onNativeCall();
  //     return new Drawable(_nFinishRecordingAsDrawable(_ptr, 0));
  // }
  // TODO
  @ApiStatus.Internal
  @native def _nMake: Long

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nBeginRecording(ptr: Long, left: Float, top: Float, right: Float, bottom: Float): Long

  @ApiStatus.Internal
  @native def _nFinishRecordingAsPicture(ptr: Long): Long

  @ApiStatus.Internal
  @native def _nFinishRecordingAsPictureWithCull(ptr: Long, left: Float, top: Float, right: Float, bottom: Float): Long

  @ApiStatus.Internal
  @native def _nFinishRecordingAsDrawable(ptr: Long): Long

  try Library.staticLoad()
}

class PictureRecorder @ApiStatus.Internal(ptr: Long) extends Managed(ptr, PictureRecorder._FinalizerHolder.PTR, true) {
  @ApiStatus.Internal var _canvas: Canvas = null

  def this() = {
    this(PictureRecorder._nMake)
    Stats.onNativeCall()
  }

  /**
   * Returns the canvas that records the drawing commands.
   *
   * @param bounds the cull rect used when recording this picture. Any drawing the falls outside
   *               of this rect is undefined, and may be drawn or it may not.
   * @return the canvas.
   */
  def beginRecording(bounds: Rect): Canvas = {
    assert(_canvas == null, "Recording already in progress")
    try {
      Stats.onNativeCall()
      _canvas = new Canvas(PictureRecorder
        ._nBeginRecording(_ptr, bounds._left, bounds._top, bounds._right, bounds._bottom), false, this)
      _canvas
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the recording canvas if one is active, or null if recording is not active.
   */
  @Nullable def getRecordingCanvas: Canvas = {
    _canvas
  }

  /**
   * <p>Signal that the caller is done recording. This invalidates the canvas returned by
   * {@link # beginRecording ( Rect )}/{@link # getRecordingCanvas ( )}.</p>
   *
   * <p>The returned picture is immutable. If during recording drawables were added to the canvas,
   * these will have been "drawn" into a recording canvas, so that this resulting picture will
   * reflect their current state, but will not contain a live reference to the drawables
   * themselves.</p>
   */
  def finishRecordingAsPicture: Picture = {
    try {
      assert(_canvas != null, "Recording not started")
      _canvas.invalidate()
      _canvas = null
      Stats.onNativeCall()
      new Picture(PictureRecorder._nFinishRecordingAsPicture(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Signal that the caller is done recording, and update the cull rect to use for bounding
   * box hierarchy (BBH) generation. The behavior is the same as calling
   * {@link # finishRecordingAsPicture ( )}, except that this method updates the cull rect
   * initially passed into {@link # beginRecording ( Rect )}.
   *
   * @param cull the new culling rectangle to use as the overall bound for BBH generation
   *             and subsequent culling operations.
   * @return the picture containing the recorded content.
   */
  def finishRecordingAsPicture(@NotNull cull: Rect): Picture = {
    try {
      assert(_canvas != null, "Recording not started")
      _canvas.invalidate()
      _canvas = null
      Stats.onNativeCall()
      new Picture(PictureRecorder
        ._nFinishRecordingAsPictureWithCull(_ptr, cull._left, cull._top, cull._right, cull._bottom))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  override def close(): Unit = {
    if (_canvas != null) {
      _canvas.invalidate()
      _canvas = null
    }
    super.close()
  }
}