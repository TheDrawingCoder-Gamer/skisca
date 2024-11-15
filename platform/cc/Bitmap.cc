#include <jni.h>
#include "SkBitmap.h"
#include "SkPixelRef.h"
#include "SkSamplingOptions.h"
#include "SkShader.h"
#include "interop.hh"

static void deleteBitmap(SkBitmap* instance) {
    // std::cout << "Deleting [SkBitmap " << instance << "]" << std::endl;
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetFinalizer(JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteBitmap));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nMake
  (JNIEnv* env, jobject) {
    return reinterpret_cast<jlong>(new SkBitmap());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nMakeClone
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(new SkBitmap(*instance));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nSwap
  (JNIEnv* env, jobject, jlong ptr, jlong otherPtr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkBitmap* other = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(otherPtr));
    instance->swap(*other);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetImageInfo
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return skija::ImageInfo::toJava(env, instance->info());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetRowBytesAsPixels
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->rowBytesAsPixels();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nIsNull
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->isNull();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetRowBytes
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->rowBytes();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nSetAlphaType
  (JNIEnv* env, jobject, jlong ptr, jint alphaType) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->setAlphaType(static_cast<SkAlphaType>(alphaType));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nComputeByteSize
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->computeByteSize();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nIsImmutable
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->isImmutable();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nSetImmutable
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->setImmutable();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nReset
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nComputeIsOpaque
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return SkBitmap::ComputeIsOpaque(*instance);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nSetImageInfo
  (JNIEnv* env, jobject, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr, jlong rowBytes) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    return instance->setInfo(imageInfo, rowBytes);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nAllocPixelsFlags
  (JNIEnv* env, jobject, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr, jint flags) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    return instance->tryAllocPixelsFlags(imageInfo, flags);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nAllocPixelsRowBytes
  (JNIEnv* env, jobject, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr, jlong rowBytes) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    return instance->tryAllocPixels(imageInfo, rowBytes);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nInstallPixels
  (JNIEnv* env, jobject, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr, jbyteArray pixelsArr, jlong rowBytes) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));

    jsize len = env->GetArrayLength(pixelsArr);
    jbyte* pixels = new jbyte[len];
    env->GetByteArrayRegion(pixelsArr, 0, len, pixels);
    return instance->installPixels(imageInfo, pixels, rowBytes, deleteJBytes, nullptr);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nAllocPixels
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->tryAllocPixels();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetPixelRef
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkPixelRef* pixelRef = instance->pixelRef();
    pixelRef->ref();
    return reinterpret_cast<jlong>(pixelRef);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetPixelRefOrigin
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkIPoint origin = instance->pixelRefOrigin();
    return packIPoint(origin);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nSetPixelRef
  (JNIEnv* env, jobject, jlong ptr, jlong pixelRefPtr, jint dx, jint dy) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkPixelRef* pixelRef = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(pixelRefPtr));
    instance->setPixelRef(sk_ref_sp<SkPixelRef>(pixelRef), dx, dy);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nIsReadyToDraw
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->readyToDraw();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetGenerationId
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->getGenerationID();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nNotifyPixelsChanged
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->notifyPixelsChanged();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nErase4f
  (JNIEnv* env, jobject, jlong ptr, jfloat r, jfloat g, jfloat b, jfloat a) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->eraseColor({r, g, b, a});
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nEraseRect4f
  (JNIEnv* env, jobject, jlong ptr, jfloat r, jfloat g, jfloat b, jfloat a, jint left, jint top, jint right, jint bottom) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->erase({r, g, b, a}, {left, top, right, bottom});
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nErase
  (JNIEnv* env, jobject, jlong ptr, jint color) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->eraseColor(color);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nEraseRect
  (JNIEnv* env, jobject, jlong ptr, jint color, jint left, jint top, jint right, jint bottom) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    instance->erase(color, {left, top, right, bottom});
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetColor
  (JNIEnv* env, jobject, jlong ptr, jint x, jint y) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->getColor(x, y);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetColor4f
  (JNIEnv* env, jobject, jlong ptr, jint x, jint y) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColor4f color = instance->getColor4f(x, y);
    return env->NewObject(skija::Color4f::cls, skija::Color4f::ctor, color.fR, color.fG, color.fB, color.fA);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nGetAlphaf
  (JNIEnv* env, jobject, jlong ptr, jint x, jint y) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    return instance->getAlphaf(x, y);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nExtractSubset
  (JNIEnv* env, jobject, jlong ptr, jlong dstPtr, jint left, jint top, jint right, jint bottom) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkBitmap* dst = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(dstPtr));
    return instance->extractSubset(dst, {left, top, right, bottom});
}

extern "C" JNIEXPORT jbyteArray JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nReadPixels
  (JNIEnv* env, jobject, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr, jlong rowBytes, jint srcX, jint srcY) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    std::vector<jbyte> pixels(std::min(height, instance->height() - srcY) * rowBytes);
    if (instance->readPixels(imageInfo, pixels.data(), rowBytes, srcX, srcY))
        return javaByteArray(env, pixels);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nExtractAlpha
  (JNIEnv* env, jobject, jlong ptr, jlong dstPtr, jlong paintPtr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkBitmap* dst = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(dstPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    SkIPoint offset;
    if (instance->extractAlpha(dst, paint, &offset))
        return types::IPoint::fromSkIPoint(env, offset);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nPeekPixels
  (JNIEnv* env, jobject, jlong ptr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    SkPixmap pixmap;
    if (instance->peekPixels(&pixmap))
        return env->NewDirectByteBuffer(pixmap.writable_addr(), pixmap.rowBytes() * pixmap.height());
    else
        return nullptr;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Bitmap_00024__1nMakeShader
  (JNIEnv* env, jobject, jlong ptr, jint tmx, jint tmy, jlong samplingMode, jfloatArray localMatrixArr) {
    SkBitmap* instance = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkMatrix> localMatrix = skMatrix(env, localMatrixArr);
    sk_sp<SkShader> shader = instance->makeShader(static_cast<SkTileMode>(tmx), static_cast<SkTileMode>(tmy), skija::SamplingMode::unpack(samplingMode), localMatrix.get());
    return reinterpret_cast<jlong>(shader.release());
}
