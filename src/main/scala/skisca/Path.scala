package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

/**
 * <p>Path contain geometry. Path may be empty, or contain one or more verbs that
 * outline a figure. Path always starts with a move verb to a Cartesian coordinate,
 * and may be followed by additional verbs that add lines or curves.</p>
 *
 * <p>Adding a close verb makes the geometry into a continuous loop, a closed contour.
 * Path may contain any number of contours, each beginning with a move verb.</p>
 *
 * <p>Path contours may contain only a move verb, or may also contain lines,
 * quadratic beziers, conics, and cubic beziers. Path contours may be open or
 * closed.</p>
 *
 * <p>When used to draw a filled area, Path describes whether the fill is inside or
 * outside the geometry. Path also describes the winding rule used to fill
 * overlapping contours.</p>
 *
 * <p>Internally, Path lazily computes metrics likes bounds and convexity. Call
 * {@link # updateBoundsCache ( )} to make Path thread safe.</p>
 */
object Path {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  def apply(): Path = {
    new Path(_nMake())
  }
  @NotNull def makeFromSVGString(svg: String): Path = {
    val res = _nMakeFromSVGString(svg)
    if (res == 0) {
      throw new IllegalArgumentException("Failed to parse SVG Path string: " + svg)
    } else {
      new Path(res)
    }
  }

  /**
   * <p>Tests if line between Point pair is degenerate.</p>
   *
   * <p>Line with no length or that moves a very short distance is degenerate; it is
   * treated as a point.</p>
   *
   * <p>exact changes the equality test. If true, returns true only if p1 equals p2.
   * If false, returns true if p1 equals or nearly equals p2.</p>
   *
   * @param p1    line start point
   * @param p2    line end point
   * @param exact if false, allow nearly equals
   * @return true if line is degenerate; its length is effectively zero
   * @see <a href="https://fiddle.skia.org/c/@Path_IsLineDegenerate">https://fiddle.skia.org/c/@Path_IsLineDegenerate</a>
   */
  def isLineDegenerate(p1: Point, p2: Point, exact: Boolean): Boolean = {
    Stats.onNativeCall()
    _nIsLineDegenerate(p1._x, p1._y, p2._x, p2._y, exact)
  }

  /**
   * <p>Tests if quad is degenerate.</p>
   *
   * <p>Quad with no length or that moves a very short distance is degenerate; it is
   * treated as a point.</p>
   *
   * @param p1    quad start point
   * @param p2    quad control point
   * @param p3    quad end point
   * @param exact if true, returns true only if p1, p2, and p3 are equal;
   *              if false, returns true if p1, p2, and p3 are equal or nearly equal
   * @return true if quad is degenerate; its length is effectively zero
   */
  def isQuadDegenerate(p1: Point, p2: Point, p3: Point, exact: Boolean): Boolean = {
    Stats.onNativeCall()
    _nIsQuadDegenerate(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y, exact)
  }

  /**
   * <p>Tests if cubic is degenerate.</p>
   *
   * <p>Cubic with no length or that moves a very short distance is degenerate; it is
   * treated as a point.</p>
   *
   * @param p1    cubic start point
   * @param p2    cubic control point 1
   * @param p3    cubic control point 2
   * @param p4    cubic end point
   * @param exact if true, returns true only if p1, p2, p3, and p4 are equal;
   *              if false, returns true if p1, p2, p3, and p4 are equal or nearly equal
   * @return true if cubic is degenerate; its length is effectively zero
   */
  def isCubicDegenerate(p1: Point, p2: Point, p3: Point, p4: Point, exact: Boolean): Boolean = {
    Stats.onNativeCall()
    _nIsCubicDegenerate(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y, p4._x, p4._y, exact)
  }

  /**
   * <p>Approximates conic with quad array. Conic is constructed from start Point p0,
   * control Point p1, end Point p2, and weight w.</p>
   *
   * <p>Quad array is stored in pts; this storage is supplied by caller.</p>
   *
   * <p>Maximum quad count is 2 to the pow2.</p>
   *
   * <p>Every third point in array shares last Point of previous quad and first Point of
   * next quad. Maximum pts storage size is given by: {@code (1 + 2 * (1 << pow2)).</p>}</p>
   *
   * <p>Returns quad count used the approximation, which may be smaller
   * than the number requested.</p>
   *
   * <p>conic weight determines the amount of influence conic control point has on the curve.</p>
   *
   * <p>w less than one represents an elliptical section. w greater than one represents
   * a hyperbolic section. w equal to one represents a parabolic section.</p>
   *
   * <p>Two quad curves are sufficient to approximate an elliptical conic with a sweep
   * of up to 90 degrees; in this case, set pow2 to one.</p>
   *
   * @param p0   conic start Point
   * @param p1   conic control Point
   * @param p2   conic end Point
   * @param w    conic weight
   * @param pow2 quad count, as power of two, normally 0 to 5 (1 to 32 quad curves)
   * @return number of quad curves written to pts
   */
  def convertConicToQuads(p0: Point, p1: Point, p2: Point, w: Float, pow2: Int): Array[Point] = {
    Stats.onNativeCall()
    _nConvertConicToQuads(p0._x, p0._y, p1._x, p1._y, p2._x, p2._y, w, pow2)
  }

  /**
   * <p>Returns Path that is the result of applying the Op to the first path and the second path.
   * <p>The resulting path will be constructed from non-overlapping contours.
   * <p>The curve order is reduced where possible so that cubics may be turned
   * into quadratics, and quadratics maybe turned into lines.
   *
   * @param one The first operand (for difference, the minuend)
   * @param two The second operand (for difference, the subtrahend)
   * @param op  The operator to apply.
   * @return Path if operation was able to produce a result, null otherwise
   */
  @Nullable def makeCombining(@NotNull one: Path, @NotNull two: Path, op: PathOp): Path = {
    try {
      Stats.onNativeCall()
      val ptr = _nMakeCombining(Native.getPtr(one), Native.getPtr(two), op.ordinal)
      if (ptr == 0) {
        null
      } else {
        new Path(ptr)
      }
    } finally {
      ReferenceUtil.reachabilityFence(one)
      ReferenceUtil.reachabilityFence(two)
    }
  }

  /**
   * <p>Initializes Path from byte buffer. Returns null if the buffer is
   * data is inconsistent, or the length is too small.</p>
   *
   * <p>Reads {@link PathFillMode}, verb array, Point array, conic weight, and
   * additionally reads computed information like path convexity and bounds.</p>
   *
   * <p>Used only in concert with {@link serializeToBytes( )};
   * the format used for Path in memory is not guaranteed.</p>
   *
   * @param data storage for Path
   * @return reconstructed Path
   * @see <a href="https://fiddle.skia.org/c/@Path_readFromMemory">https://fiddle.skia.org/c/@Path_readFromMemory</a>
   */
  def makeFromBytes(data: Array[Byte]): Path = {
    Stats.onNativeCall()
    new Path(_nMakeFromBytes(data))
  }

  @native def _nGetFinalizer: Long

  @native def _nMake(): Long

  @native def _nMakeFromSVGString(s: String): Long

  @native def _nEquals(aPtr: Long, bPtr: Long): Boolean

  @native def _nIsInterpolatable(ptr: Long, comparePtr: Long): Boolean

  @native def _nMakeLerp(ptr: Long, endingPtr: Long, weight: Float): Long

  @native def _nGetFillMode(ptr: Long): Int

  @native def _nSetFillMode(ptr: Long, fillMode: Int): Unit

  @native def _nIsConvex(ptr: Long): Boolean

  @native def _nIsOval(ptr: Long): Rect

  @native def _nIsRRect(ptr: Long): RRect

  @native def _nReset(ptr: Long): Unit

  @native def _nRewind(ptr: Long): Unit

  @native def _nIsEmpty(ptr: Long): Boolean

  @native def _nIsLastContourClosed(ptr: Long): Boolean

  @native def _nIsFinite(ptr: Long): Boolean

  @native def _nIsVolatile(ptr: Long): Boolean

  @native def _nSetVolatile(ptr: Long, isVolatile: Boolean): Unit

  @native def _nIsLineDegenerate(x0: Float, y0: Float, x1: Float, y1: Float, exact: Boolean): Boolean

  @native def _nIsQuadDegenerate(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, exact: Boolean): Boolean

  @native def _nIsCubicDegenerate(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, exact: Boolean): Boolean

  @native def _nMaybeGetAsLine(ptr: Long): Array[Point]

  @native def _nGetPointsCount(ptr: Long): Int

  @native def _nGetPoint(ptr: Long, index: Int): Point

  @native def _nGetPoints(ptr: Long, points: Array[Point], max: Int): Int

  @native def _nCountVerbs(ptr: Long): Int

  @native def _nGetVerbs(ptr: Long, verbs: Array[Byte], max: Int): Int

  @native def _nApproximateBytesUsed(ptr: Long): Long

  @native def _nSwap(ptr: Long, otherPtr: Long): Unit

  @native def _nGetBounds(ptr: Long): Rect

  @native def _nUpdateBoundsCache(ptr: Long): Unit

  @native def _nComputeTightBounds(ptr: Long): Rect

  @native def _nConservativelyContainsRect(ptr: Long, l: Float, t: Float, r: Float, b: Float): Boolean

  @native def _nIncReserve(ptr: Long, extraPtCount: Int): Unit

  @native def _nMoveTo(ptr: Long, x: Float, y: Float): Unit

  @native def _nRMoveTo(ptr: Long, dx: Float, dy: Float): Unit

  @native def _nLineTo(ptr: Long, x: Float, y: Float): Unit

  @native def _nRLineTo(ptr: Long, dx: Float, dy: Float): Unit

  @native def _nQuadTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float): Unit

  @native def _nRQuadTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float): Unit

  @native def _nConicTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, w: Float): Unit

  @native def _nRConicTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float, w: Float): Unit

  @native def _nCubicTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float): Unit

  @native def _nRCubicTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float, dx3: Float, dy3: Float): Unit

  @native def _nArcTo(ptr: Long, left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float, forceMoveTo: Boolean): Unit

  @native def _nTangentArcTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, radius: Float): Unit

  @native def _nEllipticalArcTo(ptr: Long, rx: Float, ry: Float, xAxisRotate: Float, size: Int, direction: Int, x: Float, y: Float): Unit

  @native def _nREllipticalArcTo(ptr: Long, rx: Float, ry: Float, xAxisRotate: Float, size: Int, direction: Int, dx: Float, dy: Float): Unit

  @native def _nClosePath(ptr: Long): Unit

  @native def _nConvertConicToQuads(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, w: Float, pow2: Int): Array[Point]

  @native def _nIsRect(ptr: Long): Rect

  @native def _nAddRect(ptr: Long, l: Float, t: Float, r: Float, b: Float, dir: Int, start: Int): Unit

  @native def _nAddOval(ptr: Long, l: Float, t: Float, r: Float, b: Float, dir: Int, start: Int): Unit

  @native def _nAddCircle(ptr: Long, x: Float, y: Float, r: Float, dir: Int): Unit

  @native def _nAddArc(ptr: Long, l: Float, t: Float, r: Float, b: Float, startAngle: Float, sweepAngle: Float): Unit

  @native def _nAddRRect(ptr: Long, l: Float, t: Float, r: Float, b: Float, radii: Array[Float], dir: Int, start: Int): Unit

  @native def _nAddPoly(ptr: Long, coords: Array[Float], close: Boolean): Unit

  @native def _nAddPath(ptr: Long, srcPtr: Long, extend: Boolean): Unit

  @native def _nAddPathOffset(ptr: Long, srcPtr: Long, dx: Float, dy: Float, extend: Boolean): Unit

  @native def _nAddPathTransform(ptr: Long, srcPtr: Long, matrix: Array[Float], extend: Boolean): Unit

  @native def _nReverseAddPath(ptr: Long, srcPtr: Long): Unit

  @native def _nOffset(ptr: Long, dx: Float, dy: Float, dst: Long): Unit

  @native def _nTransform(ptr: Long, matrix: Array[Float], dst: Long, applyPerspectiveClip: Boolean): Unit

  @native def _nGetLastPt(ptr: Long): Point

  @native def _nSetLastPt(ptr: Long, x: Float, y: Float): Unit

  @native def _nGetSegmentMasks(ptr: Long): Int

  @native def _nContains(ptr: Long, x: Float, y: Float): Boolean

  @native def _nDump(ptr: Long): Unit

  @native def _nDumpHex(ptr: Long): Unit

  @native def _nSerializeToBytes(ptr: Long): Array[Byte]

  @native def _nMakeCombining(onePtr: Long, twoPtr: Long, op: Int): Long

  @native def _nMakeFromBytes(data: Array[Byte]): Long

  @native def _nGetGenerationId(ptr: Long): Int

  @native def _nIsValid(ptr: Long): Boolean

  try Library.staticLoad()
}

class Path @ApiStatus.Internal(ptr: Long) extends Managed(ptr, Path._FinalizerHolder.PTR) with Iterable[PathSegment] {
  /**
   * Constructs an empty Path. By default, Path has no verbs, no {@link Point}, and no weights.
   * FillMode is set to {@link PathFillMode# WINDING}.
   */
  def this() = {
    this(Path._nMake())
  }

  /**
   * Compares this path and o; Returns true if {@link PathFillMode}, verb array, Point array, and weights
   * are equivalent.
   *
   * @param other Path to compare
   * @return true if this and Path are equivalent
   */
  @ApiStatus.Internal override def _nativeEquals(other: Native): Boolean = {
    try {
      Path._nEquals(_ptr, Native.getPtr(other))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(other)
    }
  }

  /**
   * <p>Returns true if Path contain equal verbs and equal weights.
   * If Path contain one or more conics, the weights must match.</p>
   *
   * <p>{@link # conicTo ( float, float, float, float, float)} may add different verbs
   * depending on conic weight, so it is not trivial to interpolate a pair of Path
   * containing conics with different conic weight values.</p>
   *
   * @param compare Path to compare
   * @return true if Path verb array and weights are equivalent
   * @see <a href="https://fiddle.skia.org/c/@Path_isInterpolatable">https://fiddle.skia.org/c/@Path_isInterpolatable</a>
   */
  def isInterpolatable(compare: Path): Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsInterpolatable(_ptr, Native.getPtr(compare))
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(compare)
    }
  }

  /**
   * <p>Interpolates between Path with {@link Point} array of equal size.
   * Copy verb array and weights to out, and set out Point array to a weighted
   * average of this Point array and ending Point array, using the formula:
   *
   * <p>{@code (Path Point * weight) + ending Point * (1 - weight)}
   *
   * <p>weight is most useful when between zero (ending Point array) and
   * one (this Point_Array); will work with values outside of this
   * range.</p>
   *
   * <p>interpolate() returns null if Point array is not
   * the same size as ending Point array. Call {@link # isInterpolatable ( Path )} to check Path
   * compatibility prior to calling interpolate().</p>
   *
   * @param ending Point array averaged with this Point array
   * @param weight contribution of this Point array, and
   *               one minus contribution of ending Point array
   * @return interpolated Path if Path contain same number of Point, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_interpolate">https://fiddle.skia.org/c/@Path_interpolate</a>
   */
  def makeLerp(ending: Path, weight: Float): Path = {
    try {
      Stats.onNativeCall()
      val ptr = Path._nMakeLerp(_ptr, Native.getPtr(ending), weight)
      if (ptr == 0) throw new IllegalArgumentException("Point array is not the same size as ending Point array")
      new Path(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
      ReferenceUtil.reachabilityFence(ending)
    }
  }

  def getFillMode: PathFillMode = {
    try {
      Stats.onNativeCall()
      PathFillMode._values(Path._nGetFillMode(_ptr))
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def setFillMode(fillMode: PathFillMode): Path = {
    Stats.onNativeCall()
    Path._nSetFillMode(_ptr, fillMode.ordinal)
    this
  }

  /**
   * Returns true if the path is convex. If necessary, it will first compute the convexity.
   *
   * @return true or false
   */
  def isConvex: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsConvex(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns oval bounds if this path is recognized as an oval or circle.
   *
   * @return bounds is recognized as an oval or circle, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_isOval">https://fiddle.skia.org/c/@Path_isOval</a>
   */
  def isOval: Rect = {
    try {
      Stats.onNativeCall()
      Path._nIsOval(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns {@link RRect} if this path is recognized as an oval, circle or RRect.
   *
   * @return bounds is recognized as an oval, circle or RRect, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_isRRect">https://fiddle.skia.org/c/@Path_isRRect</a>
   */
  def isRRect: RRect = {
    try {
      Stats.onNativeCall()
      Path._nIsRRect(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Sets Path to its initial state.</p>
   *
   * <p>Removes verb array, Point array, and weights, and sets FillMode to {@link PathFillMode# WINDING}.
   * Internal storage associated with Path is released.</p>
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_reset">https://fiddle.skia.org/c/@Path_reset</a>
   */
  def reset: Path = {
    Stats.onNativeCall()
    Path._nReset(_ptr)
    this
  }

  /**
   * <p>Sets Path to its initial state, preserving internal storage.
   * Removes verb array, Point array, and weights, and sets FillMode to kWinding.
   * Internal storage associated with Path is retained.</p>
   *
   * <p>Use {@link # rewind ( )} instead of {@link # reset ( )} if Path storage will be reused and performance
   * is critical.</p>
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_rewind">https://fiddle.skia.org/c/@Path_rewind</a>
   */
  def rewind: Path = {
    Stats.onNativeCall()
    Path._nRewind(_ptr)
    this
  }

  /**
   * <p>Returns if Path is empty.</p>
   *
   * <p>Empty Path may have FillMode but has no {@link Point}, {@link PathVerb}, or conic weight.
   * {@link Path( )} constructs empty Path; {@link # reset ( )} and {@link # rewind ( )} make Path empty.</p>
   *
   * @return true if the path contains no Verb array
   */
  override def isEmpty: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsEmpty(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns if contour is closed.</p>
   *
   * <p>Contour is closed if Path Verb array was last modified by {@link # closePath ( )}. When stroked,
   * closed contour draws {@link PaintStrokeJoin} instead of {@link PaintStrokeCap} at first and last Point.</p>
   *
   * @return true if the last contour ends with a {@link PathVerb# CLOSE}
   * @see <a href="https://fiddle.skia.org/c/@Path_isLastContourClosed">https://fiddle.skia.org/c/@Path_isLastContourClosed</a>
   */
  def isLastContourClosed: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsLastContourClosed(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true for finite Point array values between negative Float.MIN_VALUE and
   * positive Float.MAX_VALUE. Returns false for any Point array value of
   * Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, or Float.NaN.
   *
   * @return true if all Point values are finite
   */
  def isFinite: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsFinite(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if the path is volatile; it will not be altered or discarded
   * by the caller after it is drawn. Path by default have volatile set false, allowing
   * {@link Surface} to attach a cache of data which speeds repeated drawing. If true, {@link Surface}
   * may not speed repeated drawing.
   *
   * @return true if caller will alter Path after drawing
   */
  def isVolatile: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsVolatile(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Specifies whether Path is volatile; whether it will be altered or discarded
   * by the caller after it is drawn. Path by default have volatile set false, allowing
   * SkBaseDevice to attach a cache of data which speeds repeated drawing.</p>
   *
   * <p>Mark temporary paths, discarded or modified after use, as volatile
   * to inform SkBaseDevice that the path need not be cached.</p>
   *
   * <p>Mark animating Path volatile to improve performance.
   * Mark unchanging Path non-volatile to improve repeated rendering.</p>
   *
   * <p>raster surface Path draws are affected by volatile for some shadows.
   * GPU surface Path draws are affected by volatile for some shadows and concave geometries.</p>
   *
   * @param isVolatile true if caller will alter Path after drawing
   * @return this
   */
  def setVolatile(isVolatile: Boolean): Path = {
    Stats.onNativeCall()
    Path._nSetVolatile(_ptr, isVolatile)
    this
  }

  /**
   * Returns array of two points if Path contains only one line;
   * Verb array has two entries: {@link PathVerb# MOVE}, {@link PathVerb# LINE}.
   * Returns null if Path is not one line.
   *
   * @return Point[2] if Path contains exactly one line, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_isLine">https://fiddle.skia.org/c/@Path_isLine</a>
   */
  def getAsLine: Array[Point] = {
    try {
      Stats.onNativeCall()
      Path._nMaybeGetAsLine(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the number of points in Path.
   * Point count is initially zero.
   *
   * @return Path Point array length
   * @see <a href="https://fiddle.skia.org/c/@Path_countPoints">https://fiddle.skia.org/c/@Path_countPoints</a>
   */
  def getPointsCount: Int = {
    try {
      Stats.onNativeCall()
      Path._nGetPointsCount(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns Point at index in Point array. Valid range for index is
   * 0 to countPoints() - 1.</p>
   *
   * <p>Returns (0, 0) if index is out of range.</p>
   *
   * @param index Point array element selector
   * @return Point array value or (0, 0)
   * @see <a href="https://fiddle.skia.org/c/@Path_getPoint">https://fiddle.skia.org/c/@Path_getPoint</a>
   */
  def getPoint(index: Int): Point = {
    try {
      Stats.onNativeCall()
      Path._nGetPoint(_ptr, index)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns all points in Path.</p>
   *
   * @return Path Point array length
   * @see <a href="https://fiddle.skia.org/c/@Path_getPoints">https://fiddle.skia.org/c/@Path_getPoints</a>
   */
  def getPoints: Array[Point] = {
    val res = new Array[Point](getPointsCount)
    getPoints(res, res.length)
    res
  }

  /**
   * <p>Returns number of points in Path. Up to max points are copied.</p>
   *
   * <p>points may be null; then, max must be zero.
   * If max is greater than number of points, excess points storage is unaltered.</p>
   *
   * @param points storage for Path Point array. May be null
   * @param max    maximum to copy; must be greater than or equal to zero
   * @return Path Point array length
   * @see <a href="https://fiddle.skia.org/c/@Path_getPoints">https://fiddle.skia.org/c/@Path_getPoints</a>
   */
  def getPoints(points: Array[Point], max: Int): Int = {
    try {
      assert(if (points == null) {
        max == 0
      } else {
        true
      })
      Stats.onNativeCall()
      Path._nGetPoints(_ptr, points, max)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the number of verbs: {@link PathVerb# MOVE}, {@link PathVerb# LINE}, {@link PathVerb# QUAD}, {@link PathVerb# CONIC},
   * {@link PathVerb# CUBIC}, and {@link PathVerb# CLOSE}; added to Path.
   *
   * @return length of verb array
   * @see <a href="https://fiddle.skia.org/c/@Path_countVerbs">https://fiddle.skia.org/c/@Path_countVerbs</a>
   */
  def getVerbsCount: Int = {
    try {
      Stats.onNativeCall()
      Path._nCountVerbs(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  def getVerbs: Array[PathVerb] = {
    val res = new Array[PathVerb](getVerbsCount)
    getVerbs(res, res.length)
    res
  }

  /**
   * Returns the number of verbs in the path. Up to max verbs are copied.
   *
   * @param verbs storage for verbs, may be null
   * @param max   maximum number to copy into verbs
   * @return the actual number of verbs in the path
   * @see <a href="https://fiddle.skia.org/c/@Path_getVerbs">https://fiddle.skia.org/c/@Path_getVerbs</a>
   */
  def getVerbs(verbs: Array[PathVerb], max: Int): Int = {
    try {
      assert(if (verbs == null) {
        max == 0
      } else {
        true
      })
      Stats.onNativeCall()
      val out = if (verbs == null) {
        null
      } else {
        new Array[Byte](max)
      }
      val count = Path._nGetVerbs(_ptr, out, max)
      if (verbs != null) {
        for (i <- 0 until Math.min(count, max)) {
          verbs(i) = PathVerb._values(out(i))
        }
      } 
      count
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns the approximate byte size of the Path in memory.
   *
   * @return approximate size
   */
  def getApproximateBytesUsed: Long = {
    try {
      Stats.onNativeCall()
      Path._nApproximateBytesUsed(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Exchanges the verb array, Point array, weights, and FillMode with other.
   * Cached state is also exchanged. swap() internally exchanges pointers, so
   * it is lightweight and does not allocate memory.</p>
   *
   * @param other Path exchanged by value
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_swap">https://fiddle.skia.org/c/@Path_swap</a>
   */
  def swap(other: Path): Path = {
    try {
      Stats.onNativeCall()
      Path._nSwap(_ptr, Native.getPtr(other))
      this
    } finally {
      ReferenceUtil.reachabilityFence(other)
    }
  }

  /**
   * <p>Returns minimum and maximum axes values of Point array.</p>
   *
   * <p>Returns (0, 0, 0, 0) if Path contains no points. Returned bounds width and height may
   * be larger or smaller than area affected when Path is drawn.</p>
   *
   * <p>Rect returned includes all Point added to Path, including Point associated with
   * {@link PathVerb# MOVE} that define empty contours.</p>
   *
   * @return bounds of all Point in Point array
   */
  def getBounds: Rect = {
    try {
      Stats.onNativeCall()
      Path._nGetBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Updates internal bounds so that subsequent calls to {@link # getBounds ( )} are instantaneous.
   * Unaltered copies of Path may also access cached bounds through {@link # getBounds ( )}.</p>
   *
   * <p>For now, identical to calling {@link # getBounds ( )} and ignoring the returned value.</p>
   *
   * <p>Call to prepare Path subsequently drawn from multiple threads,
   * to avoid a race condition where each draw separately computes the bounds.</p>
   *
   * @return this
   */
  def updateBoundsCache: Path = {
    Stats.onNativeCall()
    Path._nUpdateBoundsCache(_ptr)
    this
  }

  /**
   * <p>Returns minimum and maximum axes values of the lines and curves in Path.
   * Returns (0, 0, 0, 0) if Path contains no points.
   * Returned bounds width and height may be larger or smaller than area affected
   * when Path is drawn.</p>
   *
   * <p>Includes Point associated with {@link PathVerb# MOVE} that define empty
   * contours.</p>
   *
   * Behaves identically to {@link # getBounds ( )} when Path contains
   * only lines. If Path contains curves, computed bounds includes
   * the maximum extent of the quad, conic, or cubic; is slower than {@link # getBounds ( )};
   * and unlike {@link # getBounds ( )}, does not cache the result.
   *
   * @return tight bounds of curves in Path
   * @see <a href="https://fiddle.skia.org/c/@Path_computeTightBounds">https://fiddle.skia.org/c/@Path_computeTightBounds</a>
   */
  def computeTightBounds: Rect = {
    try {
      Stats.onNativeCall()
      Path._nComputeTightBounds(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns true if rect is contained by Path.
   * May return false when rect is contained by Path.</p>
   *
   * <p>For now, only returns true if Path has one contour and is convex.
   * rect may share points and edges with Path and be contained.
   * Returns true if rect is empty, that is, it has zero width or height; and
   * the Point or line described by rect is contained by Path.</p>
   *
   * @param rect Rect, line, or Point checked for containment
   * @return true if rect is contained
   * @see <a href="https://fiddle.skia.org/c/@Path_conservativelyContainsRect">https://fiddle.skia.org/c/@Path_conservativelyContainsRect</a>
   */
  def conservativelyContainsRect(rect: Rect): Boolean = {
    try {
      Stats.onNativeCall()
      Path._nConservativelyContainsRect(_ptr, rect._left, rect._top, rect._right, rect._bottom)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Grows Path verb array and Point array to contain extraPtCount additional Point.
   * May improve performance and use less memory by
   * reducing the number and size of allocations when creating Path.</p>
   *
   * @param extraPtCount number of additional Point to allocate
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_incReserve">https://fiddle.skia.org/c/@Path_incReserve</a>
   */
  def incReserve(extraPtCount: Int): Path = {
    Stats.onNativeCall()
    Path._nIncReserve(_ptr, extraPtCount)
    this
  }

  /**
   * Adds beginning of contour at Point (x, y).
   *
   * @param x x-axis value of contour start
   * @param y y-axis value of contour start
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_moveTo">https://fiddle.skia.org/c/@Path_moveTo</a>
   */
  def moveTo(x: Float, y: Float): Path = {
    Stats.onNativeCall()
    Path._nMoveTo(_ptr, x, y)
    this
  }

  /**
   * Adds beginning of contour at Point p.
   *
   * @param p contour start
   * @return this
   */
  def moveTo(p: Point): Path = {
    moveTo(p._x, p._y)
  }

  /**
   * <p>Adds beginning of contour relative to last point.</p>
   *
   * <p>If Path is empty, starts contour at (dx, dy).
   * Otherwise, start contour at last point offset by (dx, dy).
   * Function name stands for "relative move to".</p>
   *
   * @param dx offset from last point to contour start on x-axis
   * @param dy offset from last point to contour start on y-axis
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_rMoveTo">https://fiddle.skia.org/c/@Path_rMoveTo</a>
   */
  def rMoveTo(dx: Float, dy: Float): Path = {
    Stats.onNativeCall()
    Path._nRMoveTo(_ptr, dx, dy)
    this
  }

  /**
   * <p>Adds line from last point to (x, y). If Path is empty, or last Verb is
   * {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding line.</p>
   *
   * <p>lineTo() appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed.
   * lineTo() then appends {@link PathVerb# LINE} to verb array and (x, y) to Point array.</p>
   *
   * @param x end of added line on x-axis
   * @param y end of added line on y-axis
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_lineTo">https://fiddle.skia.org/c/@Path_lineTo</a>
   */
  def lineTo(x: Float, y: Float): Path = {
    Stats.onNativeCall()
    Path._nLineTo(_ptr, x, y)
    this
  }

  /**
   * <p>Adds line from last point to Point p. If Path is empty, or last {@link PathVerb} is
   * {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding line.</p>
   *
   * <p>lineTo() first appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed.
   * lineTo() then appends {@link PathVerb# LINE} to verb array and Point p to Point array.</p>
   *
   * @param p end Point of added line
   * @return reference to Path
   */
  def lineTo(p: Point): Path = {
    lineTo(p._x, p._y)
  }

  /**
   * <p>Adds line from last point to vector (dx, dy). If Path is empty, or last {@link PathVerb} is
   * {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding line.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed;
   * then appends {@link PathVerb# LINE} to verb array and line end to Point array.</p>
   *
   * <p>Line end is last point plus vector (dx, dy).</p>
   *
   * <p>Function name stands for "relative line to".</p>
   *
   * @param dx offset from last point to line end on x-axis
   * @param dy offset from last point to line end on y-axis
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_rLineTo">https://fiddle.skia.org/c/@Path_rLineTo</a>
   * @see <a href="https://fiddle.skia.org/c/@Quad_a">https://fiddle.skia.org/c/@Quad_a</a>
   * @see <a href="https://fiddle.skia.org/c/@Quad_b">https://fiddle.skia.org/c/@Quad_b</a>
   */
  def rLineTo(dx: Float, dy: Float): Path = {
    Stats.onNativeCall()
    Path._nRLineTo(_ptr, dx, dy)
    this
  }

  /**
   * Adds quad from last point towards (x1, y1), to (x2, y2).
   * If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to (0, 0)
   * before adding quad.
   *
   * Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed;
   * then appends {@link PathVerb# QUAD} to verb array; and (x1, y1), (x2, y2)
   * to Point array.
   *
   * @param x1 control Point of quad on x-axis
   * @param y1 control Point of quad on y-axis
   * @param x2 end Point of quad on x-axis
   * @param y2 end Point of quad on y-axis
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_quadTo">https://fiddle.skia.org/c/@Path_quadTo</a>
   */
  def quadTo(x1: Float, y1: Float, x2: Float, y2: Float): Path = {
    Stats.onNativeCall()
    Path._nQuadTo(_ptr, x1, y1, x2, y2)
    this
  }

  /**
   * <p>Adds quad from last point towards Point p1, to Point p2.</p>
   *
   * <p>If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to (0, 0)
   * before adding quad.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed;
   * then appends {@link PathVerb# QUAD} to verb array; and Point p1, p2
   * to Point array.</p>
   *
   * @param p1 control Point of added quad
   * @param p2 end Point of added quad
   * @return reference to Path
   */
  def quadTo(p1: Point, p2: Point): Path = {
    quadTo(p1._x, p1._y, p2._x, p2._y)
  }

  /**
   * <p>Adds quad from last point towards vector (dx1, dy1), to vector (dx2, dy2).
   * If Path is empty, or last {@link PathVerb}
   * is {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding quad.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array,
   * if needed; then appends {@link PathVerb# QUAD} to verb array; and appends quad
   * control and quad end to Point array.</p>
   *
   * <p>Quad control is last point plus vector (dx1, dy1).</p>
   *
   * <p>Quad end is last point plus vector (dx2, dy2).</p>
   *
   * <p>Function name stands for "relative quad to".</p>
   *
   * @param dx1 offset from last point to quad control on x-axis
   * @param dy1 offset from last point to quad control on y-axis
   * @param dx2 offset from last point to quad end on x-axis
   * @param dy2 offset from last point to quad end on y-axis
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Conic_Weight_a">https://fiddle.skia.org/c/@Conic_Weight_a</a>
   * @see <a href="https://fiddle.skia.org/c/@Conic_Weight_b">https://fiddle.skia.org/c/@Conic_Weight_b</a>
   * @see <a href="https://fiddle.skia.org/c/@Conic_Weight_c">https://fiddle.skia.org/c/@Conic_Weight_c</a>
   * @see <a href="https://fiddle.skia.org/c/@Path_rQuadTo">https://fiddle.skia.org/c/@Path_rQuadTo</a>
   */
  def rQuadTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float): Path = {
    Stats.onNativeCall()
    Path._nRQuadTo(_ptr, dx1, dy1, dx2, dy2)
    this
  }

  /**
   * <p>Adds conic from last point towards (x1, y1), to (x2, y2), weighted by w.</p>
   *
   * <p>If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to (0, 0)
   * before adding conic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed.</p>
   *
   * <p>If w is finite and not one, appends {@link PathVerb# CONIC} to verb array;
   * and (x1, y1), (x2, y2) to Point array; and w to conic weights.</p>
   *
   * <p>If w is one, appends {@link PathVerb# QUAD} to verb array, and
   * (x1, y1), (x2, y2) to Point array.</p>
   *
   * <p>If w is not finite, appends {@link PathVerb# LINE} twice to verb array, and
   * (x1, y1), (x2, y2) to Point array.</p>
   *
   * @param x1 control Point of conic on x-axis
   * @param y1 control Point of conic on y-axis
   * @param x2 end Point of conic on x-axis
   * @param y2 end Point of conic on y-axis
   * @param w  weight of added conic
   * @return reference to Path
   */
  def conicTo(x1: Float, y1: Float, x2: Float, y2: Float, w: Float): Path = {
    Stats.onNativeCall()
    Path._nConicTo(_ptr, x1, y1, x2, y2, w)
    this
  }

  /**
   * <p>Adds conic from last point towards Point p1, to Point p2, weighted by w.</p>
   *
   * <p>If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to (0, 0)
   * before adding conic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed.</p>
   *
   * <p>If w is finite and not one, appends {@link PathVerb# CONIC} to verb array;
   * and Point p1, p2 to Point array; and w to conic weights.</p>
   *
   * <p>If w is one, appends {@link PathVerb# QUAD} to verb array, and Point p1, p2
   * to Point array.</p>
   *
   * <p>If w is not finite, appends {@link PathVerb# LINE} twice to verb array, and
   * Point p1, p2 to Point array.</p>
   *
   * @param p1 control Point of added conic
   * @param p2 end Point of added conic
   * @param w  weight of added conic
   * @return reference to Path
   */
  def conicTo(p1: Point, p2: Point, w: Float): Path = {
    conicTo(p1._x, p1._y, p2._x, p2._y, w)
  }

  /**
   * <p>Adds conic from last point towards vector (dx1, dy1), to vector (dx2, dy2),
   * weighted by w. If Path is empty, or last {@link PathVerb}
   * is {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding conic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array,
   * if needed.</p>
   *
   * <p>If w is finite and not one, next appends {@link PathVerb# CONIC} to verb array,
   * and w is recorded as conic weight; otherwise, if w is one, appends
   * {@link PathVerb# QUAD} to verb array; or if w is not finite, appends {@link PathVerb# LINE}
   * twice to verb array.</p>
   *
   * <p>In all cases appends Point control and end to Point array.
   * control is last point plus vector (dx1, dy1).
   * end is last point plus vector (dx2, dy2).</p>
   *
   * <p>Function name stands for "relative conic to".</p>
   *
   * @param dx1 offset from last point to conic control on x-axis
   * @param dy1 offset from last point to conic control on y-axis
   * @param dx2 offset from last point to conic end on x-axis
   * @param dy2 offset from last point to conic end on y-axis
   * @param w   weight of added conic
   * @return reference to Path
   */
  def rConicTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float, w: Float): Path = {
    Stats.onNativeCall()
    Path._nRConicTo(_ptr, dx1, dy1, dx2, dy2, w)
    this
  }

  /**
   * <p>Adds cubic from last point towards (x1, y1), then towards (x2, y2), ending at
   * (x3, y3). If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to
   * (0, 0) before adding cubic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed;
   * then appends {@link PathVerb# CUBIC} to verb array; and (x1, y1), (x2, y2), (x3, y3)
   * to Point array.</p>
   *
   * @param x1 first control Point of cubic on x-axis
   * @param y1 first control Point of cubic on y-axis
   * @param x2 second control Point of cubic on x-axis
   * @param y2 second control Point of cubic on y-axis
   * @param x3 end Point of cubic on x-axis
   * @param y3 end Point of cubic on y-axis
   * @return reference to Path
   */
  def cubicTo(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float): Path = {
    Stats.onNativeCall()
    Path._nCubicTo(_ptr, x1, y1, x2, y2, x3, y3)
    this
  }

  /**
   * <p>Adds cubic from last point towards Point p1, then towards Point p2, ending at
   * Point p3. If Path is empty, or last {@link PathVerb} is {@link PathVerb# CLOSE}, last point is set to
   * (0, 0) before adding cubic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array, if needed;
   * then appends {@link PathVerb# CUBIC} to verb array; and Point p1, p2, p3
   * to Point array.</p>
   *
   * @param p1 first control Point of cubic
   * @param p2 second control Point of cubic
   * @param p3 end Point of cubic
   * @return reference to Path
   */
  def cubicTo(p1: Point, p2: Point, p3: Point): Path = {
    cubicTo(p1._x, p1._y, p2._x, p2._y, p3._x, p3._y)
  }

  /**
   * <p>Adds cubic from last point towards vector (dx1, dy1), then towards
   * vector (dx2, dy2), to vector (dx3, dy3).
   * If Path is empty, or last {@link PathVerb}
   * is {@link PathVerb# CLOSE}, last point is set to (0, 0) before adding cubic.</p>
   *
   * <p>Appends {@link PathVerb# MOVE} to verb array and (0, 0) to Point array,
   * if needed; then appends {@link PathVerb# CUBIC} to verb array; and appends cubic
   * control and cubic end to Point array.</p>
   *
   * <p>Cubic control is last point plus vector (dx1, dy1).</p>
   *
   * <p>Cubic end is last point plus vector (dx2, dy2).</p>
   *
   * <p>Function name stands for "relative cubic to".</p>
   *
   * @param dx1 offset from last point to first cubic control on x-axis
   * @param dy1 offset from last point to first cubic control on y-axis
   * @param dx2 offset from last point to second cubic control on x-axis
   * @param dy2 offset from last point to second cubic control on y-axis
   * @param dx3 offset from last point to cubic end on x-axis
   * @param dy3 offset from last point to cubic end on y-axis
   * @return reference to Path
   */
  def rCubicTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float, dx3: Float, dy3: Float): Path = {
    Stats.onNativeCall()
    Path._nRCubicTo(_ptr, dx1, dy1, dx2, dy2, dx3, dy3)
    this
  }

  /**
   * <p>Appends arc to Path. Arc added is part of ellipse
   * bounded by oval, from startAngle through sweepAngle. Both startAngle and
   * sweepAngle are measured in degrees, where zero degrees is aligned with the
   * positive x-axis, and positive sweeps extends arc clockwise.</p>
   *
   * <p>arcTo() adds line connecting Path last Point to initial arc Point if forceMoveTo
   * is false and Path is not empty. Otherwise, added contour begins with first point
   * of arc. Angles greater than -360 and less than 360 are treated modulo 360.</p>
   *
   * @param oval        bounds of ellipse containing arc
   * @param startAngle  starting angle of arc in degrees
   * @param sweepAngle  sweep, in degrees. Positive is clockwise; treated modulo 360
   * @param forceMoveTo true to start a new contour with arc
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_arcTo">https://fiddle.skia.org/c/@Path_arcTo</a>
   */
  def arcTo(oval: Rect, startAngle: Float, sweepAngle: Float, forceMoveTo: Boolean): Path = {
    Stats.onNativeCall()
    Path._nArcTo(_ptr, oval._left, oval._top, oval._right, oval._bottom, startAngle, sweepAngle, forceMoveTo)
    this
  }

  /**
   * <p>Appends arc to Path, after appending line if needed. Arc is implemented by conic
   * weighted to describe part of circle. Arc is contained by tangent from
   * last Path point to (x1, y1), and tangent from (x1, y1) to (x2, y2). Arc
   * is part of circle sized to radius, positioned so it touches both tangent lines.</p>
   *
   * <p>If last Path Point does not start Arc, tangentArcTo appends connecting Line to Path.
   * The length of Vector from (x1, y1) to (x2, y2) does not affect Arc.</p>
   *
   * <p>Arc sweep is always less than 180 degrees. If radius is zero, or if
   * tangents are nearly parallel, tangentArcTo appends Line from last Path Point to (x1, y1).</p>
   *
   * <p>tangentArcTo appends at most one Line and one conic.</p>
   *
   * <p>tangentArcTo implements the functionality of PostScript arct and HTML Canvas tangentArcTo.</p>
   *
   * @param x1     x-axis value common to pair of tangents
   * @param y1     y-axis value common to pair of tangents
   * @param x2     x-axis value end of second tangent
   * @param y2     y-axis value end of second tangent
   * @param radius distance from arc to circle center
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_arcTo_2_a">https://fiddle.skia.org/c/@Path_arcTo_2_a</a>
   * @see <a href="https://fiddle.skia.org/c/@Path_arcTo_2_b">https://fiddle.skia.org/c/@Path_arcTo_2_b</a>
   * @see <a href="https://fiddle.skia.org/c/@Path_arcTo_2_c">https://fiddle.skia.org/c/@Path_arcTo_2_c</a>
   */
  def tangentArcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float): Path = {
    Stats.onNativeCall()
    Path._nTangentArcTo(_ptr, x1, y1, x2, y2, radius)
    this
  }

  /**
   * <p>Appends arc to Path, after appending line if needed. Arc is implemented by conic
   * weighted to describe part of circle. Arc is contained by tangent from
   * last Path point to p1, and tangent from p1 to p2. Arc
   * is part of circle sized to radius, positioned so it touches both tangent lines.</p>
   *
   * <p>If last Path Point does not start arc, tangentArcTo() appends connecting line to Path.
   * The length of vector from p1 to p2 does not affect arc.</p>
   *
   * <p>Arc sweep is always less than 180 degrees. If radius is zero, or if
   * tangents are nearly parallel, tangentArcTo() appends line from last Path Point to p1.</p>
   *
   * <p>tangentArcTo() appends at most one line and one conic.</p>
   *
   * <p>tangentArcTo() implements the functionality of PostScript arct and HTML Canvas tangentArcTo.</p>
   *
   * @param p1     Point common to pair of tangents
   * @param p2     end of second tangent
   * @param radius distance from arc to circle center
   * @return reference to Path
   */
  def tangentArcTo(p1: Point, p2: Point, radius: Float): Path = {
    tangentArcTo(p1._x, p1._y, p2._x, p2._y, radius)
  }

  /** <p>Appends arc to Path. Arc is implemented by one or more conics weighted to
   * describe part of oval with radii (rx, ry) rotated by xAxisRotate degrees. Arc
   * curves from last Path Point to (x, y), choosing one of four possible routes:
   * clockwise or counterclockwise, and smaller or larger.</p>
   *
   * <p>Arc sweep is always less than 360 degrees. ellipticalArcTo() appends line to (x, y) if
   * either radii are zero, or if last Path Point equals (x, y). ellipticalArcTo() scales radii
   * (rx, ry) to fit last Path Point and (x, y) if both are greater than zero but
   * too small.</p>
   *
   * <p>ellipticalArcTo() appends up to four conic curves.</p>
   *
   * <p>ellipticalArcTo() implements the functionality of SVG arc, although SVG sweep-flag value
   * is opposite the integer value of sweep; SVG sweep-flag uses 1 for clockwise,
   * while {@link PathDirection# CLOCKWISE} cast to int is zero.</p>
   *
   * @param rx          radius on x-axis before x-axis rotation
   * @param ry          radius on y-axis before x-axis rotation
   * @param xAxisRotate x-axis rotation in degrees; positive values are clockwise
   * @param arc         chooses smaller or larger arc
   * @param direction   chooses clockwise or counterclockwise arc
   * @param x           end of arc
   * @param y           end of arc
   * @return reference to Path
   */
  def ellipticalArcTo(rx: Float, ry: Float, xAxisRotate: Float, arc: PathEllipseArc, direction: PathDirection, x: Float, y: Float): Path = {
    Stats.onNativeCall()
    Path._nEllipticalArcTo(_ptr, rx, ry, xAxisRotate, arc.ordinal, direction.ordinal, x, y)
    this
  }

  /**
   * <p>Appends arc to Path. Arc is implemented by one or more conic weighted to describe
   * part of oval with radii (r.fX, r.fY) rotated by xAxisRotate degrees. Arc curves
   * from last Path Point to (xy.fX, xy.fY), choosing one of four possible routes:
   * clockwise or counterclockwise, and smaller or larger.</p>
   *
   * <p>Arc sweep is always less than 360 degrees. ellipticalArcTo() appends line to xy if either
   * radii are zero, or if last Path Point equals (xy.fX, xy.fY). ellipticalArcTo() scales radii r to
   * fit last Path Point and xy if both are greater than zero but too small to describe
   * an arc.</p>
   *
   * <p>ellipticalArcTo() appends up to four conic curves.</p>
   *
   * <p>ellipticalArcTo() implements the functionality of SVG arc, although SVG sweep-flag value is
   * opposite the integer value of sweep; SVG sweep-flag uses 1 for clockwise, while
   * {@link PathDirection# CLOCKWISE} cast to int is zero.</p>
   *
   * @param r           radii on axes before x-axis rotation
   * @param xAxisRotate x-axis rotation in degrees; positive values are clockwise
   * @param arc         chooses smaller or larger arc
   * @param direction   chooses clockwise or counterclockwise arc
   * @param xy          end of arc
   * @return reference to Path
   */
  def ellipticalArcTo(r: Point, xAxisRotate: Float, arc: PathEllipseArc, direction: PathDirection, xy: Point): Path = {
    ellipticalArcTo(r
      ._x, r._y, xAxisRotate, arc, direction, xy._x, xy._y)
  }

  /**
   * <p>Appends arc to Path, relative to last Path Point. Arc is implemented by one or
   * more conic, weighted to describe part of oval with radii (rx, ry) rotated by
   * xAxisRotate degrees. Arc curves from last Path Point to relative end Point:
   * (dx, dy), choosing one of four possible routes: clockwise or
   * counterclockwise, and smaller or larger. If Path is empty, the start arc Point
   * is (0, 0).</p>
   *
   * <p>Arc sweep is always less than 360 degrees. rEllipticalArcTo() appends line to end Point
   * if either radii are zero, or if last Path Point equals end Point.
   * rEllipticalArcTo() scales radii (rx, ry) to fit last Path Point and end Point if both are
   * greater than zero but too small to describe an arc.</p>
   *
   * <p>rEllipticalArcTo() appends up to four conic curves.</p>
   *
   * <p>rEllipticalArcTo() implements the functionality of svg arc, although SVG "sweep-flag" value is
   * opposite the integer value of sweep; SVG "sweep-flag" uses 1 for clockwise, while
   * {@link PathDirection# CLOCKWISE} cast to int is zero.</p>
   *
   * @param rx          radius before x-axis rotation
   * @param ry          radius before x-axis rotation
   * @param xAxisRotate x-axis rotation in degrees; positive values are clockwise
   * @param arc         chooses smaller or larger arc
   * @param direction   chooses clockwise or counterclockwise arc
   * @param dx          x-axis offset end of arc from last Path Point
   * @param dy          y-axis offset end of arc from last Path Point
   * @return reference to Path
   */
  def rEllipticalArcTo(rx: Float, ry: Float, xAxisRotate: Float, arc: PathEllipseArc, direction: PathDirection, dx: Float, dy: Float): Path = {
    Stats.onNativeCall()
    Path._nREllipticalArcTo(_ptr, rx, ry, xAxisRotate, arc.ordinal, direction.ordinal, dx, dy)
    this
  }

  /**
   * <p>Appends {@link PathVerb# CLOSE} to Path. A closed contour connects the first and last Point
   * with line, forming a continuous loop. Open and closed contour draw the same
   * with {@link PaintMode# FILL}. With {@link PaintMode# STROKE}, open contour draws
   * {@link PaintStrokeCap} at contour start and end; closed contour draws
   * {@link PaintStrokeJoin} at contour start and end.</p>
   *
   * <p>closePath() has no effect if Path is empty or last Path {@link PathVerb} is {@link PathVerb# CLOSE}.</p>
   *
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_close">https://fiddle.skia.org/c/@Path_close</a>
   */
  def closePath: Path = {
    Stats.onNativeCall()
    Path._nClosePath(_ptr)
    this
  }

  /**
   * <p>Returns Rect if Path is equivalent to Rect when filled.</p>
   *
   * rect may be smaller than the Path bounds. Path bounds may include {@link PathVerb# MOVE} points
   * that do not alter the area drawn by the returned rect.
   *
   * @return bounds if Path contains Rect, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_isRect">https://fiddle.skia.org/c/@Path_isRect</a>
   */
  def isRect: Rect = {
    try {
      Stats.onNativeCall()
      Path._nIsRect(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Adds Rect to Path, appending {@link PathVerb# MOVE}, three {@link PathVerb# LINE}, and {@link PathVerb# CLOSE},
   * starting with top-left corner of Rect; followed by top-right, bottom-right,
   * and bottom-left.
   *
   * @param rect Rect to add as a closed contour
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRect">https://fiddle.skia.org/c/@Path_addRect</a>
   */
  def addRect(rect: Rect): Path = {
    addRect(rect, PathDirection.CLOCKWISE, 0)
  }

  /**
   * Adds Rect to Path, appending {@link PathVerb# MOVE}, three {@link PathVerb# LINE}, and {@link PathVerb# CLOSE},
   * starting with top-left corner of Rect; followed by top-right, bottom-right,
   * and bottom-left if dir is {@link PathDirection# CLOCKWISE}; or followed by bottom-left,
   * bottom-right, and top-right if dir is {@link PathDirection# COUNTER_CLOCKWISE}.
   *
   * @param rect Rect to add as a closed contour
   * @param dir  Direction to wind added contour
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRect">https://fiddle.skia.org/c/@Path_addRect</a>
   */
  def addRect(rect: Rect, dir: PathDirection): Path = {
    addRect(rect, dir, 0)
  }

  /**
   * Adds Rect to Path, appending {@link PathVerb# MOVE}, three {@link PathVerb# LINE}, and {@link PathVerb# CLOSE}.
   * If dir is {@link PathDirection# CLOCKWISE}, Rect corners are added clockwise; if dir is
   * {@link PathDirection# COUNTER_CLOCKWISE}, Rect corners are added counterclockwise.
   * start determines the first corner added.
   *
   * @param rect  Rect to add as a closed contour
   * @param dir   Direction to wind added contour
   * @param start initial corner of Rect to add. 0 for top left, 1 for top right, 2 for lower right, 3 for lower left
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRect_2">https://fiddle.skia.org/c/@Path_addRect_2</a>
   */
  def addRect(rect: Rect, dir: PathDirection, start: Int): Path = {
    Stats.onNativeCall()
    Path._nAddRect(_ptr, rect._left, rect._top, rect._right, rect._bottom, dir.ordinal, start)
    this
  }

  /**
   * <p>Adds oval to path, appending {@link PathVerb# MOVE}, four {@link PathVerb# CONIC}, and {@link PathVerb# CLOSE}.</p>
   *
   * <p>Oval is upright ellipse bounded by Rect oval with radii equal to half oval width
   * and half oval height. Oval begins at (oval.fRight, oval.centerY()) and continues
   * clockwise.</p>
   *
   * @param oval bounds of ellipse added
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addOval">https://fiddle.skia.org/c/@Path_addOval</a>
   */
  def addOval(oval: Rect): Path = {
    addOval(oval, PathDirection.CLOCKWISE, 1)
  }

  /**
   * <p>Adds oval to path, appending {@link PathVerb# MOVE}, four {@link PathVerb# CONIC}, and {@link PathVerb# CLOSE}.</p>
   *
   * <p>Oval is upright ellipse bounded by Rect oval with radii equal to half oval width
   * and half oval height. Oval begins at (oval.fRight, oval.centerY()) and continues
   * clockwise if dir is {@link PathDirection# CLOCKWISE}, counterclockwise if dir is {@link PathDirection# COUNTER_CLOCKWISE}.</p>
   *
   * @param oval bounds of ellipse added
   * @param dir  Direction to wind ellipse
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addOval">https://fiddle.skia.org/c/@Path_addOval</a>
   */
  def addOval(oval: Rect, dir: PathDirection): Path = {
    addOval(oval, dir, 1)
  }

  /**
   * Adds oval to Path, appending {@link PathVerb# MOVE}, four {@link PathVerb# CONIC}, and {@link PathVerb# CLOSE}.
   * Oval is upright ellipse bounded by Rect oval with radii equal to half oval width
   * and half oval height. Oval begins at start and continues
   * clockwise if dir is {@link PathDirection# CLOCKWISE}, counterclockwise if dir is {@link PathDirection# COUNTER_CLOCKWISE}.
   *
   * @param oval  bounds of ellipse added
   * @param dir   Direction to wind ellipse
   * @param start index of initial point of ellipse. 0 for top, 1 for right, 2 for bottom, 3 for left
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addOval_2">https://fiddle.skia.org/c/@Path_addOval_2</a>
   */
  def addOval(oval: Rect, dir: PathDirection, start: Int): Path = {
    Stats.onNativeCall()
    Path._nAddOval(_ptr, oval._left, oval._top, oval._right, oval._bottom, dir.ordinal, start)
    this
  }

  /**
   * <p>Adds circle centered at (x, y) of size radius to Path, appending {@link PathVerb# MOVE},
   * four {@link PathVerb# CONIC}, and {@link PathVerb# CLOSE}. Circle begins at: (x + radius, y)</p>
   *
   * <p>Has no effect if radius is zero or negative.</p>
   *
   * @param x      center of circle
   * @param y      center of circle
   * @param radius distance from center to edge
   * @return reference to Path
   */
  def addCircle(x: Float, y: Float, radius: Float): Path = {
    addCircle(x, y, radius, PathDirection.CLOCKWISE)
  }

  /**
   * <p>Adds circle centered at (x, y) of size radius to Path, appending {@link PathVerb# MOVE},
   * four {@link PathVerb# CONIC}, and {@link PathVerb# CLOSE}. Circle begins at: (x + radius, y), continuing
   * clockwise if dir is {@link PathDirection# CLOCKWISE}, and counterclockwise if dir is {@link PathDirection# COUNTER_CLOCKWISE}.</p>
   *
   * <p>Has no effect if radius is zero or negative.</p>
   *
   * @param x      center of circle
   * @param y      center of circle
   * @param radius distance from center to edge
   * @param dir    Direction to wind circle
   * @return reference to Path
   */
  def addCircle(x: Float, y: Float, radius: Float, dir: PathDirection): Path = {
    Stats.onNativeCall()
    Path._nAddCircle(_ptr, x, y, radius, dir.ordinal)
    this
  }

  /**
   * <p>Appends arc to Path, as the start of new contour. Arc added is part of ellipse
   * bounded by oval, from startAngle through sweepAngle. Both startAngle and
   * sweepAngle are measured in degrees, where zero degrees is aligned with the
   * positive x-axis, and positive sweeps extends arc clockwise.</p>
   *
   * <p>If sweepAngle &le; -360, or sweepAngle &ge; 360; and startAngle modulo 90 is nearly
   * zero, append oval instead of arc. Otherwise, sweepAngle values are treated
   * modulo 360, and arc may or may not draw depending on numeric rounding.</p>
   *
   * @param oval       bounds of ellipse containing arc
   * @param startAngle starting angle of arc in degrees
   * @param sweepAngle sweep, in degrees. Positive is clockwise; treated modulo 360
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addArc">https://fiddle.skia.org/c/@Path_addArc</a>
   */
  def addArc(oval: Rect, startAngle: Float, sweepAngle: Float): Path = {
    Stats.onNativeCall()
    Path._nAddArc(_ptr, oval._left, oval._top, oval._right, oval._bottom, startAngle, sweepAngle)
    this
  }

  /**
   * <p>Adds rrect to Path, creating a new closed contour. RRect starts at top-left of the lower-left corner and
   * winds clockwise.</p>
   *
   * <p>After appending, Path may be empty, or may contain: Rect, Oval, or RRect.</p>
   *
   * @param rrect bounds and radii of rounded rectangle
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRRect">https://fiddle.skia.org/c/@Path_addRRect</a>
   */
  def addRRect(rrect: RRect): Path = {
    addRRect(rrect, PathDirection.CLOCKWISE, 6)
  }

  /**
   * <p>Adds rrect to Path, creating a new closed contour. If
   * dir is {@link PathDirection# CLOCKWISE}, rrect starts at top-left of the lower-left corner and
   * winds clockwise. If dir is {@link PathDirection# COUNTER_CLOCKWISE}, rrect starts at the bottom-left
   * of the upper-left corner and winds counterclockwise.</p>
   *
   * <p>After appending, Path may be empty, or may contain: Rect, Oval, or RRect.</p>
   *
   * @param rrect bounds and radii of rounded rectangle
   * @param dir   Direction to wind RRect
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRRect">https://fiddle.skia.org/c/@Path_addRRect</a>
   */
  def addRRect(rrect: RRect, dir: PathDirection): Path = {
    addRRect(rrect, dir, if (dir eq PathDirection.CLOCKWISE) {
      6
    } else {
      7
    })
  }

  /**
   * <p>Adds rrect to Path, creating a new closed contour. If dir is {@link PathDirection# CLOCKWISE}, rrect
   * winds clockwise; if dir is {@link PathDirection# COUNTER_CLOCKWISE}, rrect winds counterclockwise.
   * start determines the first point of rrect to add.</p>
   *
   * @param rrect bounds and radii of rounded rectangle
   * @param dir   Direction to wind RRect
   * @param start index of initial point of RRect. 0 for top-right end of the arc at top left,
   *              1 for top-left end of the arc at top right, 2 for bottom-right end of top right arc, etc.
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addRRect_2">https://fiddle.skia.org/c/@Path_addRRect_2</a>
   */
  def addRRect(rrect: RRect, dir: PathDirection, start: Int): Path = {
    Stats.onNativeCall()
    Path._nAddRRect(_ptr, rrect._left, rrect._top, rrect._right, rrect._bottom, rrect._radii, dir.ordinal, start)
    this
  }

  /**
   * <p>Adds contour created from line array, adding (pts.length - 1) line segments.
   * Contour added starts at pts[0], then adds a line for every additional Point
   * in pts array. If close is true, appends {@link PathVerb# CLOSE} to Path, connecting
   * pts[pts.length - 1] and pts[0].</p>
   *
   * <p>If pts is empty, append {@link PathVerb# MOVE} to path.</p>
   *
   * @param pts   array of line sharing end and start Point
   * @param close true to add line connecting contour end and start
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addPoly">https://fiddle.skia.org/c/@Path_addPoly</a>
   */
  def addPoly(pts: Array[Point], close: Boolean): Path = {
    val flat = new Array[Float](pts.length * 2)
    for (i <- 0 until pts.length) {
      flat(i * 2) = pts(i)._x
      flat(i * 2 + 1) = pts(i)._y
    }
    addPoly(flat, close)
  }

  /**
   * <p>Adds contour created from line array, adding (pts.length / 2 - 1) line segments.
   * Contour added starts at (pts[0], pts[1]), then adds a line for every additional pair of floats
   * in pts array. If close is true, appends {@link PathVerb# CLOSE} to Path, connecting
   * (pts[count - 2], pts[count - 1]) and (pts[0], pts[1]).</p>
   *
   * <p>If pts is empty, append {@link PathVerb# MOVE} to path.</p>
   *
   * @param pts   flat array of line sharing end and start Point
   * @param close true to add line connecting contour end and start
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_addPoly">https://fiddle.skia.org/c/@Path_addPoly</a>
   */
  def addPoly(pts: Array[Float], close: Boolean): Path = {
    assert(pts.length % 2 == 0, "Expected even amount of pts, got " + pts.length)
    Stats.onNativeCall()
    Path._nAddPoly(_ptr, pts, close)
    this
  }

  /**
   * <p>Appends src to Path.</p>
   *
   * <p>src verb array, Point array, and conic weights are
   * added unaltered.</p>
   *
   * @param src Path verbs, Point, and conic weights to add
   * @return reference to Path
   */
  def addPath(src: Path): Path = {
    addPath(src, false)
  }

  /**
   * <p>Appends src to Path.</p>
   *
   * <p>If extend is false, src verb array, Point array, and conic weights are
   * added unaltered. If extend is true, add line before appending
   * verbs, Point, and conic weights.</p>
   *
   * @param src    Path verbs, Point, and conic weights to add
   * @param extend if should add a line before appending verbs
   * @return reference to Path
   */
  def addPath(src: Path, extend: Boolean): Path = {
    try {
      Stats.onNativeCall()
      Path._nAddPath(_ptr, Native.getPtr(src), extend)
      this
    } finally {
      ReferenceUtil.reachabilityFence(src)
    }
  }

  /**
   * <p>Appends src to Path, offset by (dx, dy).</p>
   *
   * <p>Src verb array, Point array, and conic weights are
   * added unaltered.</p>
   *
   * @param src Path verbs, Point, and conic weights to add
   * @param dx  offset added to src Point array x-axis coordinates
   * @param dy  offset added to src Point array y-axis coordinates
   * @return reference to Path
   */
  def addPath(src: Path, dx: Float, dy: Float): Path = {
    addPath(src, dx, dy, false)
  }

  /**
   * <p>Appends src to Path, offset by (dx, dy).</p>
   *
   * <p>If extend is false, src verb array, Point array, and conic weights are
   * added unaltered. If extend is true, add line before appending
   * verbs, Point, and conic weights.</p>
   *
   * @param src    Path verbs, Point, and conic weights to add
   * @param dx     offset added to src Point array x-axis coordinates
   * @param dy     offset added to src Point array y-axis coordinates
   * @param extend if should add a line before appending verbs
   * @return reference to Path
   */
  def addPath(src: Path, dx: Float, dy: Float, extend: Boolean): Path = {
    try {
      Stats.onNativeCall()
      Path._nAddPathOffset(_ptr, Native.getPtr(src), dx, dy, extend)
      this
    } finally {
      ReferenceUtil.reachabilityFence(src)
    }
  }

  /**
   * <p>Appends src to Path, transformed by matrix. Transformed curves may have different
   * verbs, Point, and conic weights.</p>
   *
   * <p>Src verb array, Point array, and conic weights are
   * added unaltered.</p>
   *
   * @param src    Path verbs, Point, and conic weights to add
   * @param matrix transform applied to src
   * @return reference to Path
   */
  def addPath(src: Path, matrix: Matrix33): Path = {
    addPath(src, matrix, false)
  }

  /**
   * <p>Appends src to Path, transformed by matrix. Transformed curves may have different
   * verbs, Point, and conic weights.</p>
   *
   * <p>If extend is false, src verb array, Point array, and conic weights are
   * added unaltered. If extend is true, add line before appending
   * verbs, Point, and conic weights.</p>
   *
   * @param src    Path verbs, Point, and conic weights to add
   * @param matrix transform applied to src
   * @param extend if should add a line before appending verbs
   * @return reference to Path
   */
  def addPath(src: Path, matrix: Matrix33, extend: Boolean): Path = {
    try {
      Stats.onNativeCall()
      Path._nAddPathTransform(_ptr, Native.getPtr(src), matrix.mat, extend)
      this
    } finally {
      ReferenceUtil.reachabilityFence(src)
    }
  }

  /**
   * Appends src to Path, from back to front.
   * Reversed src always appends a new contour to Path.
   *
   * @param src Path verbs, Point, and conic weights to add
   * @return reference to Path
   * @see <a href="https://fiddle.skia.org/c/@Path_reverseAddPath">https://fiddle.skia.org/c/@Path_reverseAddPath</a>
   */
  def reverseAddPath(src: Path): Path = {
    try {
      Stats.onNativeCall()
      Path._nReverseAddPath(_ptr, Native.getPtr(src))
      this
    } finally {
      ReferenceUtil.reachabilityFence(src)
    }
  }

  /**
   * Offsets Point array by (dx, dy). Path is replaced by offset data.
   *
   * @param dx offset added to Point array x-axis coordinates
   * @param dy offset added to Point array y-axis coordinates
   * @return this
   */
  def offset(dx: Float, dy: Float): Path = {
    offset(dx, dy, null)
  }

  /**
   * Offsets Point array by (dx, dy). Offset Path replaces dst.
   * If dst is null, Path is replaced by offset data.
   *
   * @param dx  offset added to Point array x-axis coordinates
   * @param dy  offset added to Point array y-axis coordinates
   * @param dst overwritten, translated copy of Path; may be null
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_offset">https://fiddle.skia.org/c/@Path_offset</a>
   */
  def offset(dx: Float, dy: Float, dst: Path): Path = {
    try {
      Stats.onNativeCall()
      Path._nOffset(_ptr, dx, dy, Native.getPtr(dst))
      this
    } finally {
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  /**
   * Transforms verb array, Point array, and weight by matrix.
   * transform may change verbs and increase their number.
   * Path is replaced by transformed data.
   *
   * @param matrix matrix to apply to Path
   * @return this
   */
  def transform(matrix: Matrix33): Path = {
    transform(matrix, null, true)
  }

  /**
   * Transforms verb array, Point array, and weight by matrix.
   * transform may change verbs and increase their number.
   * Path is replaced by transformed data.
   *
   * @param matrix               matrix to apply to Path
   * @param applyPerspectiveClip whether to apply perspective clipping
   * @return this
   */
  def transform(matrix: Matrix33, applyPerspectiveClip: Boolean): Path = {
    transform(matrix, null, applyPerspectiveClip)
  }

  /**
   * Transforms verb array, Point array, and weight by matrix.
   * transform may change verbs and increase their number.
   * Transformed Path replaces dst; if dst is null, original data
   * is replaced.
   *
   * @param matrix matrix to apply to Path
   * @param dst    overwritten, transformed copy of Path; may be null
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_transform">https://fiddle.skia.org/c/@Path_transform</a>
   */
  def transform(matrix: Matrix33, dst: Path): Path = {
    transform(matrix, dst, true)
  }

  /**
   * Transforms verb array, Point array, and weight by matrix.
   * transform may change verbs and increase their number.
   * Transformed Path replaces dst; if dst is null, original data
   * is replaced.
   *
   * @param matrix               matrix to apply to Path
   * @param dst                  overwritten, transformed copy of Path; may be null
   * @param applyPerspectiveClip whether to apply perspective clipping
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_transform">https://fiddle.skia.org/c/@Path_transform</a>
   */
  def transform(matrix: Matrix33, dst: Path, applyPerspectiveClip: Boolean): Path = {
    try {
      Stats.onNativeCall()
      Path._nTransform(_ptr, matrix.mat, Native.getPtr(dst), applyPerspectiveClip)
      this
    } finally {
      ReferenceUtil.reachabilityFence(dst)
    }
  }

  /**
   * Returns last point on Path in lastPt. Returns null if Point array is empty.
   *
   * @return point if Point array contains one or more Point, null otherwise
   * @see <a href="https://fiddle.skia.org/c/@Path_getLastPt">https://fiddle.skia.org/c/@Path_getLastPt</a>
   */
  def getLastPt: Point = {
    try {
      Stats.onNativeCall()
      Path._nGetLastPt(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Sets last point to (x, y). If Point array is empty, append {@link PathVerb# MOVE} to
   * verb array and append (x, y) to Point array.
   *
   * @param x set x-axis value of last point
   * @param y set y-axis value of last point
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_setLastPt">https://fiddle.skia.org/c/@Path_setLastPt</a>
   */
  def setLastPt(x: Float, y: Float): Path = {
    Stats.onNativeCall()
    Path._nSetLastPt(_ptr, x, y)
    this
  }

  /**
   * Sets the last point on the path. If Point array is empty, append {@link PathVerb# MOVE} to
   * verb array and append p to Point array.
   *
   * @param p set value of last point
   * @return this
   */
  def setLastPt(p: Point): Path = {
    setLastPt(p._x, p._y)
  }

  /**
   * <p>Returns a mask, where each set bit corresponds to a SegmentMask constant
   * if Path contains one or more verbs of that type.</p>
   *
   * <p>Returns zero if Path contains no lines, or curves: quads, conics, or cubics.</p>
   *
   * <p>getSegmentMasks() returns a cached result; it is very fast.</p>
   *
   * @return SegmentMask bits or zero
   * @see PathSegmentMask#LINE
   * @see PathSegmentMask#QUAD
   * @see PathSegmentMask#CONIC
   * @see PathSegmentMask#CUBIC
   */
  def getSegmentMasks: Int = {
    try {
      Stats.onNativeCall()
      Path._nGetSegmentMasks(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  override def iterator: PathSegmentIterator = iterator(false)

  def iterator(forceClose: Boolean): PathSegmentIterator = PathSegmentIterator.make(this, forceClose)

  /**
   * Returns true if the point (x, y) is contained by Path, taking into
   * account {@link PathFillMode}.
   *
   * @param x x-axis value of containment test
   * @param y y-axis value of containment test
   * @return true if Point is in Path
   * @see <a href="https://fiddle.skia.org/c/@Path_contains">https://fiddle.skia.org/c/@Path_contains</a>
   */
  def contains(x: Float, y: Float): Boolean = {
    try {
      Stats.onNativeCall()
      Path._nContains(_ptr, x, y)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns true if the point is contained by Path, taking into
   * account {@link PathFillMode}.
   *
   * @param p point of containment test
   * @return true if Point is in Path
   * @see <a href="https://fiddle.skia.org/c/@Path_contains">https://fiddle.skia.org/c/@Path_contains</a>
   */
  def contains(p: Point): Boolean = {
    contains(p._x, p._y)
  }

  /**
   * Writes text representation of Path to standard output. The representation may be
   * directly compiled as C++ code. Floating point values are written
   * with limited precision; it may not be possible to reconstruct original Path
   * from output.
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_dump_2">https://fiddle.skia.org/c/@Path_dump_2</a>
   */
  def dump: Path = {
    Stats.onNativeCall()
    Path._nDump(_ptr)
    this
  }

  /**
   * <p>Writes text representation of Path to standard output. The representation may be
   * directly compiled as C++ code. Floating point values are written
   * in hexadecimal to preserve their exact bit pattern. The output reconstructs the
   * original Path.</p>
   *
   * <p>Use instead of {@link dump( )} when submitting</p>
   *
   * @return this
   * @see <a href="https://fiddle.skia.org/c/@Path_dumpHex">https://fiddle.skia.org/c/@Path_dumpHex</a>
   */
  def dumpHex: Path = {
    Stats.onNativeCall()
    Path._nDumpHex(_ptr)
    this
  }

  /**
   * <p>Writes Path to byte buffer.</p>
   *
   * <p>Writes {@link PathFillMode}, verb array, Point array, conic weight, and
   * additionally writes computed information like path convexity and bounds.</p>
   *
   * <p>Use only be used in concert with {@link makeFromBytes( byte [ ] )};
   * the format used for Path in memory is not guaranteed.</p>
   *
   * @return serialized Path; length always a multiple of 4
   * @see <a href="https://fiddle.skia.org/c/@Path_writeToMemory">https://fiddle.skia.org/c/@Path_writeToMemory</a>
   */
  def serializeToBytes: Array[Byte] = {
    try {
      Stats.onNativeCall()
      Path._nSerializeToBytes(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * <p>Returns a non-zero, globally unique value. A different value is returned
   * if verb array, Point array, or conic weight changes.</p>
   *
   * <p>Setting {@link PathFillMode} does not change generation identifier.</p>
   *
   * <p>Each time the path is modified, a different generation identifier will be returned.
   * {@link PathFillMode} does affect generation identifier on Android framework.</p>
   *
   * @return non-zero, globally unique value
   * @see <a href="https://fiddle.skia.org/c/@Path_getGenerationID">https://fiddle.skia.org/c/@Path_getGenerationID</a>
   * @see <a href="https://bugs.chromium.org/p/skia/issues/detail?id=1762">https://bugs.chromium.org/p/skia/issues/detail?id=1762</a>
   */
  def getGenerationId: Int = {
    try {
      Stats.onNativeCall()
      Path._nGetGenerationId(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  /**
   * Returns if Path data is consistent. Corrupt Path data is detected if
   * internal values are out of range or internal storage does not match
   * array dimensions.
   *
   * @return true if Path data is consistent
   */
  def isValid: Boolean = {
    try {
      Stats.onNativeCall()
      Path._nIsValid(_ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}