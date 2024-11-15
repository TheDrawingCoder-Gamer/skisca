package gay.menkissing.skisca.resources

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object CachingResourceProvider {
  @NotNull
  @Contract("_ -> new") def make(@NotNull resourceProvider: ResourceProvider): CachingResourceProvider = {
    assert(resourceProvider != null, "Can’t CachingResourceProvider::make with resourceProvider == null")
    Stats.onNativeCall()
    new CachingResourceProvider(_nMake(Native.getPtr(resourceProvider)))
  }

  @ApiStatus.Internal
  @native def _nMake(resourceProviderPtr: Long): Long

  Library.staticLoad()
}

class CachingResourceProvider @ApiStatus.Internal(ptr: Long) extends ResourceProvider(ptr) {
}