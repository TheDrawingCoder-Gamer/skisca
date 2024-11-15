package gay.menkissing.skisca.impl

import java.lang.ref.Cleaner

object Cleanable {
  private val cleaner = Cleaner.create

  def register(obj: AnyRef, action: Runnable) = new Cleanable(cleaner.register(obj, action))
}

final class Cleanable private(private val cleanable: Cleaner.Cleanable) {
  def clean(): Unit = {
    this.cleanable.clean()
  }
}