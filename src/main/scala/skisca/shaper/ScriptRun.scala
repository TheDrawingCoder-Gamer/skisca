package gay.menkissing.skisca.shaper

import gay.menkissing.skisca.*
import org.jetbrains.annotations.*

object ScriptRun {
  def apply(end: Int, script: String) = new ScriptRun(end, FourByteTag.fromString(script))
}

case class ScriptRun(end: Int, scriptTag: Int) {

  /** Should be iso15924 codes. */
  def script: String = {
    FourByteTag.toString(scriptTag)
  }
}