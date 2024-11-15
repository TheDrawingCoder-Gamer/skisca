package gay.menkissing.skisca.impl

import org.jetbrains.annotations.*

import java.time.*
import java.time.format.*
import java.util.*
import java.util.function.*

object Log {
  @ApiStatus.Internal var _level = 0

  def trace(s: String): Unit = {
    _log(1, "[TRACE]", s)
  }

  def debug(s: String): Unit = {
    _log(2, "[DEBUG]", s)
  }

  def info(s: String): Unit = {
    _log(3, "[INFO ]", s)
  }

  def warn(s: String): Unit = {
    _log(4, "[WARN ]", s)
  }

  def error(s: String): Unit = {
    _log(5, "[ERROR]", s)
  }

  def trace(s: Supplier[String]): Unit = {
    _log(1, "[TRACE]", s)
  }

  def debug(s: Supplier[String]): Unit = {
    _log(2, "[DEBUG]", s)
  }

  def info(s: Supplier[String]): Unit = {
    _log(3, "[INFO ]", s)
  }

  def warn(s: Supplier[String]): Unit = {
    _log(4, "[WARN ]", s)
  }

  def error(s: Supplier[String]): Unit = {
    _log(5, "[ERROR]", s)
  }

  def _time: String = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)

  def _log(level: Int, prefix: String, s: String): Unit = {
    if (level >= _level) System.out.println(_time + " " + prefix + " " + s)
  }

  def _log(level: Int, prefix: String, s: Supplier[String]): Unit = {
    if (level >= _level) System.out.println(_time + " " + prefix + " " + s.get)
  }

  
  val property: String = System.getProperty("skija.logLevel")
  if ("ALL" == property) {
    _level = 0
  } else if ("TRACE" == property) {
    _level = 1
  } else if ("DEBUG" == property) {
    _level = 2
  } else if ("INFO" == property) {
    _level = 3
  } else if (null == property || "WARN" == property) {
    _level = 4
  } else if ("ERROR" == property) {
    _level = 5
  } else if ("NONE" == property) {
    _level = 6
  } else {
    throw new IllegalArgumentException("Unknown log level: " + property)
  }
}