package gay.menkissing.skisca

import io.github.humbleui.types.*

import java.util.Objects


object PathSegment {
  def makeMove(x0: Float, y0: Float, closedContour: Boolean): PathSegment = {
    PathSegment
      .Move(Point(x0, y0), closedContour)
  }

  def makeClose(x0: Float, y0: Float, closedContour: Boolean): PathSegment = {
    PathSegment
      .Close(Point(x0, y0), closedContour)
  }

  def makeLine(x0: Float, y0: Float, x1: Float, y1: Float, closeLine: Boolean, closedContour: Boolean): PathSegment = {
    PathSegment.Line(Point(x0, y0), Point(x1, y1), closeLine, closedContour)
  }
  def makeQuad(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, closedContour: Boolean): PathSegment = {
    PathSegment.Quad(Point(x0, y0), Point(x1, y1), Point(x2, y2), closedContour)
  }

  def makeConic(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, conicWeight: Float, closedContour: Boolean): PathSegment = {
    PathSegment.Conic(Point(x0, y0), Point(x1, y1), Point(x2, y2), conicWeight, closedContour)
  }
  def makeCubic(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, closedContour: Boolean): PathSegment = {
    PathSegment.Cubic(Point(x0, y0), Point(x1, y1), Point(x2, y2), Point(x3, y3), closedContour)
  }
}

enum PathSegment(val verb: PathVerb) {
  case Move(p0: Point, closedContour: Boolean) extends PathSegment(PathVerb.MOVE)
  case Line(p0: Point, p1: Point, closeLine: Boolean, closedContour: Boolean) extends PathSegment(PathVerb.LINE)
  case Quad(p0: Point, p1: Point, p2: Point, closedContour: Boolean) extends PathSegment(PathVerb.QUAD)
  case Conic(p0: Point, p1: Point, p2: Point, conicWeight: Float, closedContour: Boolean) extends PathSegment(PathVerb.CONIC)
  case Cubic(p0: Point, p1: Point, p2: Point, p3: Point, closedContour: Boolean) extends PathSegment(PathVerb.CUBIC)
  case Close(p0: Point, closedContour: Boolean) extends PathSegment(PathVerb.CLOSE)
  case Done extends PathSegment(PathVerb.DONE)
}