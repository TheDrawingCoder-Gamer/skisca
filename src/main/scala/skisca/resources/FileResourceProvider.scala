package gay.menkissing.skisca.resources

import gay.menkissing.skisca.impl.*
import org.jetbrains.annotations.*

object FileResourceProvider {
  @NotNull
  @Contract("_ -> new") def make(@NotNull baseDir: String): FileResourceProvider = {
    make(baseDir, false)
  }

  @NotNull
  @Contract("_, _ -> new") def make(@NotNull baseDir: String, predecode: Boolean): FileResourceProvider = {
    assert(baseDir != null, "Can’t FileResourceProvider::make with baseDir == null")
    Stats.onNativeCall()
    new FileResourceProvider(_nMake(baseDir, predecode))
  }

  @ApiStatus.Internal
  @native def _nMake(baseDir: String, predecode: Boolean): Long

  Library.staticLoad()
}

class FileResourceProvider @ApiStatus.Internal(ptr: Long) extends ResourceProvider(ptr) {
}