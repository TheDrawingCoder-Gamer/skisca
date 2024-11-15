package gay.menkissing.skisca.impl

import java.util
import java.util.concurrent.ConcurrentHashMap

object Stats {
  var enabled = false
  var nativeCalls = 0
  var allocated = new ConcurrentHashMap[String, Integer]

  def onNativeCall(): Unit = {
    if (enabled) nativeCalls += 1
  }

  def onAllocated(className: String): Unit = {
    if (enabled) allocated.merge(className, 1, Integer.sum)
  }

  def onDeallocated(className: String): Unit = {
    if (enabled) allocated.merge(className, -1, Integer.sum)
  }
}