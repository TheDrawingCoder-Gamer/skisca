package gay.menkissing.skisca

import gay.menkissing.skisca.impl.*

abstract class WStream(ptr: Long, finalizer: Long, managed: Boolean = true) extends Managed(ptr, finalizer, managed)