package gay.menkissing.skisca.impl

object ReferenceUtil {
  /**
   * @see java.lang.ref.Reference#reachabilityFence(Object)
   */
  def reachabilityFence(ref: Any): Unit = {
    // Forward to Reference.reachabilityFence(Object) to ensure that this method
    // will continue to work even if the JVM behavior changes in the future.
    java.lang.ref.Reference.reachabilityFence(ref)
  }
}