package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

import java.util.*

@ApiStatus.Internal object PathSegmentIterator {
  def make(path: Path, forceClose: Boolean): PathSegmentIterator = {
    try {
      val ptr = _nMake(Native.getPtr(path), forceClose)
      val i = new PathSegmentIterator(path, ptr)
      i._nextSegment = _nNext(ptr)
      i
    } finally {
      ReferenceUtil.reachabilityFence(path)
    }
  }

  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @native def _nMake(pathPtr: Long, forceClose: Boolean): Long

  @native def _nGetFinalizer: Long

  @native def _nNext(ptr: Long): PathSegment

  try Library.staticLoad()
}

@ApiStatus.Internal class PathSegmentIterator @ApiStatus.Internal(val _path: Path, ptr: Long) extends Managed(ptr, PathSegmentIterator
  ._nGetFinalizer), collection.Iterator[PathSegment], java.util.Iterator[PathSegment] {
  Stats.onNativeCall()
  var _nextSegment: PathSegment = null

  override def next: PathSegment = {
    try {
      if (_nextSegment.verb eq PathVerb.DONE) throw new NoSuchElementException
      val res = _nextSegment
      _nextSegment = PathSegmentIterator._nNext(_ptr)
      res
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  override def hasNext: Boolean = _nextSegment.verb ne PathVerb.DONE
}