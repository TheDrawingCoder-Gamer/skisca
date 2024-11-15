package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

object PathMeasure {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake: Long

  @ApiStatus.Internal
  @native def _nMakePath(pathPtr: Long, forceClosed: Boolean, resScale: Float): Long

  @ApiStatus.Internal
  @native def _nSetPath(ptr: Long, pathPtr: Long, forceClosed: Boolean): Unit

  @ApiStatus.Internal
  @native def _nGetLength(ptr: Long): Float

  @ApiStatus.Internal
  @native def _nGetPosition(ptr: Long, distance: Float): Point

  @ApiStatus.Internal
  @native def _nGetTangent(ptr: Long, distance: Float): Point

  @ApiStatus.Internal
  @native def _nGetRSXform(ptr: Long, distance: Float): RSXform

  @ApiStatus.Internal
  @native def _nGetMatrix(ptr: Long, distance: Float, getPosition: Boolean, getTangent: Boolean): Array[Float]

  @ApiStatus.Internal
  @native def _nGetSegment(ptr: Long, startD: Float, endD: Float, dstPtr: Long, startWithMoveTo: Boolean): Boolean

  @ApiStatus.Internal
  @native def _nIsClosed(ptr: Long): Boolean

  @ApiStatus.Internal
  @native def _nNextContour(ptr: Long): Boolean

  try Library.staticLoad()
}

class PathMeasure @ApiStatus.Internal(ptr: Long) extends Managed(ptr, PathMeasure._FinalizerHolder.PTR, true) {
  def this() = {
    this(PathMeasure._nMake)
    Stats.onNativeCall()
  }

  /**
   * <p>Initialize the pathmeasure with the specified path. The parts of the path that are needed
   * are copied, so the client is free to modify/delete the path after this call.</p>
   *
   * <p>resScale controls the precision of the measure. values &gt; 1 increase the
   * precision (and possible slow down the computation).</p>
   */
  def this(path: Path, forceClosed: Boolean, resScale: Float) = {
    this(PathMeasure._nMakePath(Native.getPtr(path), forceClosed, resScale))
    Stats.onNativeCall()
    ReferenceUtil.reachabilityFence(path)
  }

  /**
   * Initialize the pathmeasure with the specified path. The parts of the path that are needed
   * are copied, so the client is free to modify/delete the path after this call.
   */
  def this(path: Path) = {
    this(path, false, 1f)
  }

  /**
   * Initialize the pathmeasure with the specified path. The parts of the path that are needed
   * are copied, so the client is free to modify/delete the path after this call.
   */
  def this(path: Path, forceClosed: Boolean) = {
    this(path, forceClosed, 1f)
  }

  /**
   * Reset the pathmeasure with the specified path. The parts of the path that are needed
   * are copied, so the client is free to modify/delete the path after this call.
   */
  def setPath(@Nullable path: Path, forceClosed: Boolean): PathMeasure = {
    try {
      Stats.onNativeCall()
      PathMeasure._nSetPath(_ptr, Native.getPtr(path), forceClosed)
      this
    } finally {
      ReferenceUtil.reachabilityFence(path)
    }
  }

  /**
   * Return the total length of the current contour, or 0 if no path
   * is associated (e.g. resetPath(null))
   */
  def getLength: Float = {
    try {
      Stats.onNativeCall()
      PathMeasure._nGetLength(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Pins distance to 0 &lt;= distance &lt;= getLength(), and then computes
   * the corresponding position.
   *
   * @return null if there is no path, or a zero-length path was specified.
   */
  @Nullable def getPosition(distance: Float): Point = {
    try {
      Stats.onNativeCall()
      PathMeasure._nGetPosition(_ptr, distance)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Pins distance to 0 &lt;= distance &lt;= getLength(), and then computes
   * the corresponding tangent.
   *
   * @return null if there is no path, or a zero-length path was specified.
   */
  @Nullable def getTangent(distance: Float): Point = {
    try {
      Stats.onNativeCall()
      PathMeasure._nGetTangent(_ptr, distance)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Pins distance to 0 &lt;= distance &lt;= getLength(), and then computes
   * the corresponding RSXform.
   *
   * @return null if there is no path, or a zero-length path was specified.
   */
  @Nullable def getRSXform(distance: Float): RSXform = {
    try {
      Stats.onNativeCall()
      PathMeasure._nGetRSXform(_ptr, distance)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Pins distance to 0 &lt;= distance &lt;= getLength(), and then computes
   * the corresponding matrix (by calling getPosition/getTangent).
   *
   * @return null if there is no path, or a zero-length path was specified.
   */
  @Nullable def getMatrix(distance: Float, getPosition: Boolean, getTangent: Boolean): Matrix33 = {
    try {
      Stats.onNativeCall()
      val mat = PathMeasure._nGetMatrix(_ptr, distance, getPosition, getTangent)
      if (mat == null) {
        null
      } else {
        new Matrix33(mat)
      }
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Given a start and stop distance, return in dst the intervening segment(s).
   * If the segment is zero-length, return false, else return true.
   * startD and stopD are pinned to legal values (0..getLength()). If startD &gt; stopD
   * then return false (and leave dst untouched).
   * Begin the segment with a moveTo if startWithMoveTo is true
   */
  @Nullable def getSegment(startD: Float, endD: Float, @NotNull dst: Path, startWithMoveTo: Boolean): Boolean = {
    try {
      Stats.onNativeCall()
      PathMeasure._nGetSegment(_ptr, startD, endD, Native.getPtr(dst), startWithMoveTo)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  /**
   * @return true if the current contour is closed.
   */
  override def isClosed: Boolean = {
    try {
      Stats.onNativeCall()
      PathMeasure._nIsClosed(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Move to the next contour in the path. Return true if one exists, or false if
   * we're done with the path.
   */
  def nextContour: Boolean = {
    try {
      Stats.onNativeCall()
      PathMeasure._nNextContour(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}