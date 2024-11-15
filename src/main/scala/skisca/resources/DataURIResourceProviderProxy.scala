package gay.menkissing.skisca.resources

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object DataURIResourceProviderProxy {
  @NotNull
  @Contract("_ -> new") def make(@NotNull resourceProvider: ResourceProvider): DataURIResourceProviderProxy = {
    make(resourceProvider, false)
  }

  @NotNull
  @Contract("_, _ -> new") def make(@NotNull resourceProvider: ResourceProvider, predecode: Boolean): DataURIResourceProviderProxy = {
    assert(resourceProvider != null, "Can’t DataURIResourceProviderProxy::make with resourceProvider == null")
    Stats.onNativeCall()
    new DataURIResourceProviderProxy(_nMake(Native.getPtr(resourceProvider), predecode))
  }

  @ApiStatus.Internal
  @native def _nMake(resourceProviderPtr: Long, predecode: Boolean): Long

  Library.staticLoad()
}

class DataURIResourceProviderProxy @ApiStatus.Internal(ptr: Long) extends ResourceProvider(ptr) {
}