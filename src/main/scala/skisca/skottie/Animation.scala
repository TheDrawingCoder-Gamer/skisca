package gay.menkissing.skisca.skottie

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import gay.menkissing.skisca.sksg.InvalidationController
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object Animation {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def makeFromString(@NotNull data: String): Animation = {
    assert(data != null, "Can’t Animation::makeFromString with data == null")
    Stats.onNativeCall()
    val ptr = _nMakeFromString(data)
    if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from string=\"" + data.toString + "\"")
    new Animation(ptr)
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def makeFromFile(@NotNull path: String): Animation = {
    assert(path != null, "Can’t Animation::makeFromFile with path == null")
    Stats.onNativeCall()
    val ptr = _nMakeFromFile(path)
    if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from path=\"" + path + "\"")
    new Animation(ptr)
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def makeFromData(@NotNull data: Data): Animation = {
    assert(data != null, "Can’t Animation::makeFromData with data == null")
    Stats.onNativeCall()
    val ptr = _nMakeFromData(Native.getPtr(data))
    if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from data.")
    new Animation(ptr)
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMakeFromString(data: String): Long

  @ApiStatus.Internal
  @native def _nMakeFromFile(path: String): Long

  @ApiStatus.Internal
  @native def _nMakeFromData(dataPtr: Long): Long

  @ApiStatus.Internal
  @native def _nRender(ptr: Long, canvasPtr: Long, left: Float, top: Float, right: Float, bottom: Float, flags: Int): Unit

  @ApiStatus.Internal
  @native def _nSeek(ptr: Long, t: Float, icPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nSeekFrame(ptr: Long, t: Float, icPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nSeekFrameTime(ptr: Long, t: Float, icPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nGetDuration(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetFPS(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetInPoint(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetOutPoint(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetVersion(ptr: Long): String

  @ApiStatus.Internal
  @native def _nGetSize(ptr: Long): Point

  Library.staticLoad()
}

class Animation @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Animation._FinalizerHolder.PTR, true) {
  /**
   * <p>Draws the current animation frame</p>
   *
   * <p>It is undefined behavior to call render() on a newly created Animation
   * before specifying an initial frame via one of the seek() variants.</p>
   *
   * @param canvas destination canvas
   * @return this
   */
  @NotNull
  @Contract("!null -> this; null -> fail") def render(@NotNull canvas: Canvas): Animation = {
    render(canvas, Rect
      .makeXYWH(0, 0, getWidth, getHeight))
  }

  /**
   * <p>Draws the current animation frame</p>
   *
   * <p>It is undefined behavior to call render() on a newly created Animation
   * before specifying an initial frame via one of the seek() variants.</p>
   *
   * @param canvas destination canvas
   * @param offset destination offset
   * @return this
   */
  @NotNull
  @Contract("_, _, _ -> this") def render(@NotNull canvas: Canvas, @NotNull offset: Point): Animation = {
    assert(offset != null, "Can’t Animation::render with offset == null")
    render(canvas, offset._x, offset._y)
  }

  /**
   * <p>Draws the current animation frame</p>
   *
   * <p>It is undefined behavior to call render() on a newly created Animation
   * before specifying an initial frame via one of the seek() variants.</p>
   *
   * @param canvas destination canvas
   * @param left   destination offset left
   * @param top    destination offset top
   * @return this
   */
  @NotNull
  @Contract("_, _, _ -> this") def render(@NotNull canvas: Canvas, left: Float, top: Float): Animation = {
    render(canvas, Rect
      .makeXYWH(left, top, getWidth, getHeight))
  }

  /**
   * <p>Draws the current animation frame</p>
   *
   * <p>It is undefined behavior to call render() on a newly created Animation
   * before specifying an initial frame via one of the seek() variants.</p>
   *
   * @param canvas      destination canvas
   * @param dst         destination rect
   * @param renderFlags render flags
   * @return this
   */
  @NotNull
  @Contract("_, _, _ -> this") def render(@NotNull canvas: Canvas, @NotNull dst: Rect, renderFlags: RenderFlag*): Animation = {
    try {
      assert(canvas != null, "Can’t Animation::render with canvas == null")
      assert(dst != null, "Can’t Animation::render with dst == null")
      Stats.onNativeCall()
      var flags = 0
      for (flag <- renderFlags) {
        flags = flags | flag.flag
      }
      Animation._nRender(_ptr, Native.getPtr(canvas), dst._left, dst._top, dst._right, dst._bottom, flags)
      this
    } finally {
      ReferenceUtil.reachabilityFence(canvas)
    }
  }

  /**
   * <p>Updates the animation state for |t|.</p>
   *
   * @param t normalized [0..1] frame selector (0 → first frame, 1 → final frame)
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def seek(t: Float): Animation = {
    seek(t, null)
  }

  /**
   * <p>Updates the animation state for |t|.</p>
   *
   * @param t  normalized [0..1] frame selector (0 → first frame, 1 → final frame)
   * @param ic invalidation controller (dirty region tracking)
   * @return this
   */
  @NotNull
  @Contract("_, _ -> this") def seek(t: Float, @Nullable ic: InvalidationController): Animation = {
    try {
      Stats.onNativeCall()
      Animation._nSeek(_ptr, t, Native.getPtr(ic))
      this
    } finally {
      ReferenceUtil.reachabilityFence(ic)
    }
  }

  /**
   * <p>Update the animation state to match |t|, specified as a frame index i.e.
   * relative to [[getDuration( )]] * [[getFPS( )]].</p>
   *
   * <p>Fractional values are allowed and meaningful - e.g.
   * 0.0 → first frame 1.0 → second frame 0.5 → halfway between first and second frame</p>
   *
   * @param t frame index
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def seekFrame(t: Float): Animation = {
    seekFrame(t, null)
  }

  /**
   * <p>Update the animation state to match |t|, specified as a frame index i.e.
   * relative to {@link getDuration( )} * {@link getFPS( )}.</p>
   *
   * <p>Fractional values are allowed and meaningful - e.g.
   * 0.0 → first frame 1.0 → second frame 0.5 → halfway between first and second frame</p>
   *
   * @param t  frame index
   * @param ic invalidation controller (dirty region tracking)
   * @return this
   */
  @NotNull
  @Contract("_, _ -> this") def seekFrame(t: Float, @Nullable ic: InvalidationController): Animation = {
    try {
      Stats.onNativeCall()
      Animation._nSeekFrame(_ptr, t, Native.getPtr(ic))
      this
    } finally {
      ReferenceUtil.reachabilityFence(ic)
    }
  }

  /**
   * <p>Update the animation state to match t, specifed in frame time i.e.
   * relative to {@link getDuration( )}.</p>
   *
   * @param t frame time
   * @return this
   */
  @NotNull
  @Contract("_ -> this") def seekFrameTime(t: Float): Animation = {
    seekFrameTime(t, null)
  }

  /**
   * <p>Update the animation state to match t, specifed in frame time i.e.
   * relative to {@link getDuration( )}.</p>
   *
   * @param t  frame time
   * @param ic invalidation controller (dirty region tracking)
   * @return this
   */
  @NotNull
  @Contract("_, _ -> this") def seekFrameTime(t: Float, @Nullable ic: InvalidationController): Animation = {
    try {
      Stats.onNativeCall()
      Animation._nSeekFrameTime(_ptr, t, Native.getPtr(ic))
      this
    } finally {
      ReferenceUtil.reachabilityFence(ic)
    }
  }

  /**
   * @return the animation duration in seconds
   */
  def getDuration: Float = {
    try {
      Stats.onNativeCall()
      Animation._nGetDuration(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return the animation frame rate (frames / second)
   */
  def getFPS: Float = {
    try {
      Stats.onNativeCall()
      Animation._nGetFPS(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return Animation in point, in frame index units
   */
  def getInPoint: Float = {
    try {
      Stats.onNativeCall()
      Animation._nGetInPoint(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * @return Animation out point, in frame index units
   */
  def getOutPoint: Float = {
    try {
      Stats.onNativeCall()
      Animation._nGetOutPoint(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull def getVersion: String = {
    try {
      Stats.onNativeCall()
      Animation._nGetVersion(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @ApiStatus.Internal
  @Nullable var _size: Point = null

  @NotNull def getSize: Point = {
    if (_size == null) _size = Animation._nGetSize(_ptr)
    _size
  }

  def getWidth: Float = getSize._x

  def getHeight: Float = getSize._y
}