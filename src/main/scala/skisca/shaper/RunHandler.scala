package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import org.jetbrains.annotations.*

trait RunHandler {
  /**
   * Called when beginning a line.
   */
  def beginLine(): Unit

  /**
   * Called once for each run in a line. Can compute baselines and offsets.
   */
  def runInfo(info: RunInfo): Unit

  /**
   * Called after all {@link # runInfo} calls for a line.
   */
  def commitRunInfo(): Unit

  /**
   * Called for each run in a line after {@link # commitRunInfo}.
   *
   * @return an offset to add to every position
   */
  def runOffset(info: RunInfo): Point

  /**
   * <p>Called for each run in a line after {@link # runOffset}.</p>
   *
   * <p>WARN positions are reported from the start of the line, not run, only in Shaper.makeCoreText https://bugs.chromium.org/p/skia/issues/detail?id=10898</p>
   *
   * @param positions put glyphs[i] at positions[i]
   * @param clusters  clusters[i] is an utf-16 offset starting run which produced glyphs[i]
   */
  def commitRun(info: RunInfo, glyphs: Array[Short], positions: Array[Point], clusters: Array[Int]): Unit

  /**
   * Called when ending a line.
   */
  def commitLine(): Unit
}