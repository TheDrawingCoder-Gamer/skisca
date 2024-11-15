package gay.menkissing.skisca.skottie

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import gay.menkissing.skisca.resources.*
import org.jetbrains.annotations.*

object AnimationBuilder {
  @ApiStatus.Internal object _FinalizerHolder {
    val PTR: Long = _nGetFinalizer
  }

  @ApiStatus.Internal def _flagsToInt(builderFlags: AnimationBuilderFlag*): Int = {
    var flags = 0
    for (flag <- builderFlags) {
      flags = flags | flag.flag
    }
    flags
  }

  @ApiStatus.Internal
  @native def _nGetFinalizer: Long

  @ApiStatus.Internal
  @native def _nMake(flags: Int): Long

  @ApiStatus.Internal
  @native def _nSetFontManager(ptr: Long, fontMgrPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nSetLogger(ptr: Long, loggerPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nSetResourceProvider(ptr: Long, resourceProviderPtr: Long): Unit

  @ApiStatus.Internal
  @native def _nBuildFromString(ptr: Long, data: String): Long

  @ApiStatus.Internal
  @native def _nBuildFromFile(ptr: Long, path: String): Long

  @ApiStatus.Internal
  @native def _nBuildFromData(ptr: Long, dataPtr: Long): Long

  Library.staticLoad()
}

class AnimationBuilder @ApiStatus.Internal(ptr: Long) extends Managed(ptr, AnimationBuilder._FinalizerHolder
                                                                                           .PTR, true) {
  def this(builderFlags: AnimationBuilderFlag*) = {
    this(AnimationBuilder._nMake(AnimationBuilder._flagsToInt(builderFlags*)))
    Stats.onNativeCall()
  }

  def this() = {
    this(new Array[AnimationBuilderFlag](0)*)
  }

  /**
   * <p>Specify a font manager for loading animation fonts.</p>
   */
  @NotNull
  @Contract("_ -> this") def setFontManager(@Nullable fontMgr: FontMgr): AnimationBuilder = {
    try {
      Stats.onNativeCall()
      AnimationBuilder._nSetFontManager(_ptr, Native.getPtr(fontMgr))
      this
    } finally {
      ReferenceUtil.reachabilityFence(fontMgr)
    }
  }

  /**
   * <p>Register a [[Logger]] with this builder.</p>
   */
  @NotNull
  @Contract("_ -> this") def setLogger(@Nullable logger: Logger): AnimationBuilder = {
    try {
      Stats.onNativeCall()
      AnimationBuilder._nSetLogger(_ptr, Native.getPtr(logger))
      this
    } finally {
      ReferenceUtil.reachabilityFence(logger)
    }
  }

  @NotNull
  @Contract("_ -> this") def setResourceProvider(@Nullable resourceProvider: ResourceProvider): AnimationBuilder = {
    try {
      Stats.onNativeCall()
      AnimationBuilder._nSetResourceProvider(_ptr, Native.getPtr(resourceProvider))
      this
    } finally {
      ReferenceUtil.reachabilityFence(resourceProvider)
    }
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def buildFromString(@NotNull data: String): Animation = {
    try {
      assert(data != null, "Can’t buildFromString with data == null")
      Stats.onNativeCall()
      val ptr = AnimationBuilder._nBuildFromString(_ptr, data)
      if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from string: \"" + data + "\"")
      new Animation(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def buildFromFile(@NotNull path: String): Animation = {
    try {
      assert(path != null, "Can’t buildFromFile with path == null")
      Stats.onNativeCall()
      val ptr = AnimationBuilder._nBuildFromFile(_ptr, path)
      if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from path: " + path)
      new Animation(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }

  @NotNull
  @Contract("!null -> new; null -> fail") def buildFromData(@NotNull data: Data): Animation = {
    try {
      assert(data != null, "Can’t buildFromData with data == null")
      Stats.onNativeCall()
      val ptr = AnimationBuilder._nBuildFromData(_ptr, Native.getPtr(data))
      if (ptr == 0) throw new IllegalArgumentException("Failed to create Animation from data")
      new Animation(ptr)
    } finally {
      ReferenceUtil.reachabilityFence(this)
    }
  }
}