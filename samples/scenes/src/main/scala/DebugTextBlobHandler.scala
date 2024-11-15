package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.shaper.*
import io.github.humbleui.types.*

import java.util.*
import java.util.stream.*
import scala.collection.mutable as mut

class DebugTextBlobHandler extends RunHandler with AutoCloseable {
  final var _builder: TextBlobBuilder = null
  var _maxRunAscent: Float = 0
  var _maxRunDescent: Float = 0
  var _maxRunLeading: Float = 0
  var _xPos: Float = 0
  var _yPos: Float = 0
  var _runs: mut.ListBuffer[DebugTextRun] = null
  _builder = TextBlobBuilder()


  def withRuns: DebugTextBlobHandler = {
    _runs = new mut.ListBuffer[DebugTextRun]
    this
  }

  override def close(): Unit = {
    if (_runs != null) {
      for (info <- _runs) {
        info.font.close()
      }
    }
    _builder.close()
  }

  override def beginLine(): Unit = {
    _xPos = 0
    _maxRunAscent = 0
    _maxRunDescent = 0
    _maxRunLeading = 0
  }

  override def runInfo(info: RunInfo): Unit = {
    val font = new Font(info.fontPtr, false)
    val metrics = font.getMetrics
    _maxRunAscent = Math.min(_maxRunAscent, metrics.ascent)
    _maxRunDescent = Math.max(_maxRunDescent, metrics.descent)
    _maxRunLeading = Math.max(_maxRunLeading, metrics.leading)
  }

  override def commitRunInfo(): Unit = {
    _yPos = _yPos - _maxRunAscent
  }

  override def runOffset(info: RunInfo) = new Point(_xPos, _yPos)

  override def commitRun(info: RunInfo, glyphs: Array[Short], positions: Array[Point], clusters: Array[Int]): Unit = {
    // System.out.println("advance=" + info._advanceX
    //                    + " glyphCount=" + info._glyphCount
    //                    + " utf8Range=" + info._rangeBegin + ".." + info.getRangeEnd()
    //                    + " positions=" + Arrays.stream(positions).map(Point::getX).collect(Collectors.toList()));
    val font = new Font(info.fontPtr, false)
    _builder.appendRunPos(font, glyphs, positions)
    if (_runs != null) {
      _runs.append(DebugTextRun(info, info.getFont, Rect
        .makeXYWH(_xPos, _yPos - (-_maxRunAscent), info.getAdvance
                                                       .getX, (-_maxRunAscent) + _maxRunDescent), glyphs, positions, clusters))
    }
    _xPos = _xPos + info.getAdvance.getX
  }

  override def commitLine(): Unit = {
    _yPos = _yPos +  _maxRunDescent + _maxRunLeading
  }

  def makeBlob: TextBlob = _builder.build
}