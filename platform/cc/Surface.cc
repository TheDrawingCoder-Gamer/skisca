#include <iostream>
#include <jni.h>
#include "GrDirectContext.h"
#include "SkSurface.h"
#include "ganesh/SkSurfaceGanesh.h"
#include "interop.hh"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWrapPixels
  (JNIEnv* env, jobject jmodule,
    jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr,
    jlong pixelsPtr, jlong rowBytes,
    jobject surfacePropsObj)
{
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> instance = SkSurfaces::WrapPixels(
      imageInfo,
      reinterpret_cast<void*>(static_cast<uintptr_t>(pixelsPtr)),
      rowBytes,
      surfaceProps.get());
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWrapPixelsPixmap
  (JNIEnv* env, jobject jmodule,
    jlong pixmapPtr, jobject surfacePropsObj)
{
    SkPixmap* pixmap = reinterpret_cast<SkPixmap*>(static_cast<uintptr_t>(pixmapPtr));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> instance = SkSurfaces::WrapPixels(*pixmap, surfaceProps.get());
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeRaster
  (JNIEnv* env, jobject jmodule,
    jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr,
    jlong rowBytes,
    jobject surfacePropsObj)
{
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> instance = SkSurfaces::Raster(
      imageInfo,
      rowBytes,
      surfaceProps.get());
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWrapBackendRenderTarget
  (JNIEnv* env, jobject jmodule, jlong pContext, jlong pBackendRenderTarget, jint surfaceOrigin, jint colorType, jlong colorSpacePtr, jobject surfacePropsObj) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(pContext));
    GrBackendRenderTarget* backendRenderTarget = reinterpret_cast<GrBackendRenderTarget*>(static_cast<uintptr_t>(pBackendRenderTarget));
    GrSurfaceOrigin grSurfaceOrigin = static_cast<GrSurfaceOrigin>(surfaceOrigin);
    SkColorType skColorType = static_cast<SkColorType>(colorType);
    sk_sp<SkColorSpace> colorSpace = sk_ref_sp<SkColorSpace>(reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr)));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> surface = SkSurfaces::WrapBackendRenderTarget(
        static_cast<GrRecordingContext*>(context),
        *backendRenderTarget,
        grSurfaceOrigin,
        skColorType,
        colorSpace,
        surfaceProps.get(),
        /* RenderTargetReleaseProc */ nullptr,
        /* ReleaseContext */ nullptr
    );
    return reinterpret_cast<jlong>(surface.release());
}

#ifdef SK_METAL
#include "ganesh/mtl/SkSurfaceMetal.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWrapMTKView
  (JNIEnv* env, jobject jmodule, jlong contextPtr, jlong mtkViewPtr, jint surfaceOrigin, jint sampleCount, jint colorType, jlong colorSpacePtr, jobject surfacePropsObj) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(contextPtr));
    GrMTLHandle* mtkView = reinterpret_cast<GrMTLHandle*>(static_cast<uintptr_t>(mtkViewPtr));
    GrSurfaceOrigin grSurfaceOrigin = static_cast<GrSurfaceOrigin>(surfaceOrigin);
    SkColorType skColorType = static_cast<SkColorType>(colorType);
    sk_sp<SkColorSpace> colorSpace = sk_ref_sp<SkColorSpace>(reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr)));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> surface = SkSurfaces::WrapMTKView(
        static_cast<GrRecordingContext*>(context),
        mtkView,
        grSurfaceOrigin,
        sampleCount,
        skColorType,
        colorSpace,
        surfaceProps.get());
    return reinterpret_cast<jlong>(surface.release());
}
#endif

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeRenderTarget
  (JNIEnv* env, jobject jmodule, jlong contextPtr, jboolean budgeted,
    jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr,
    jint sampleCount, jint surfaceOrigin,
    jobject surfacePropsObj,
    jboolean shouldCreateWithMips)
{
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(contextPtr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    std::unique_ptr<SkSurfaceProps> surfaceProps = skija::SurfaceProps::toSkSurfaceProps(env, surfacePropsObj);

    sk_sp<SkSurface> instance = SkSurfaces::RenderTarget(
      context, budgeted ? skgpu::Budgeted::kYes : skgpu::Budgeted::kNo,
      imageInfo,
      sampleCount, static_cast<GrSurfaceOrigin>(surfaceOrigin),
      surfaceProps.get(),
      shouldCreateWithMips);
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeNull
  (JNIEnv* env, jobject jmodule, jint width, jint height) {
  sk_sp<SkSurface> instance = SkSurfaces::Null(width, height);
  return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGetCanvas
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(surface->getCanvas());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGetWidth
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return surface->width();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return surface->height();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeImageSnapshot
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(surface->makeImageSnapshot().release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeImageSnapshotR
  (JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(surface->makeImageSnapshot({left, top, right, bottom}).release());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGenerationId
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return surface->generationID();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Surface_00024__1nReadPixelsToPixmap
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong pixmapPtr, jint srcX, jint srcY) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkPixmap* pixmap = reinterpret_cast<SkPixmap*>(static_cast<uintptr_t>(pixmapPtr));
    return surface->readPixels(*pixmap, srcX, srcY);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Surface_00024__1nReadPixels
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong bitmapPtr, jint srcX, jint srcY) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    return surface->readPixels(*bitmap, srcX, srcY);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWritePixelsFromPixmap
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong pixmapPtr, jint x, jint y) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkPixmap* pixmap = reinterpret_cast<SkPixmap*>(static_cast<uintptr_t>(pixmapPtr));
    surface->writePixels(*pixmap, x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nWritePixels
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong bitmapPtr, jint x, jint y) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    surface->writePixels(*bitmap, x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nFlushAndSubmit
  (JNIEnv* env, jobject jmodule, jlong ptr, jboolean syncCpu) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    surface->flushAndSubmit(syncCpu);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nFlush
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    surface->flush();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Surface_00024__1nUnique
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return surface->unique();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGetImageInfo
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    const SkImageInfo& info = surface->imageInfo();
    return env->NewObject(skija::ImageInfo::cls, skija::ImageInfo::ctor,
        info.width(),
        info.height(),
        static_cast<jint>(info.colorType()),
        static_cast<jint>(info.alphaType()),
        reinterpret_cast<jlong>(info.refColorSpace().release()));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeSurface
  (JNIEnv* env, jobject jmodule, jlong ptr, jint width, jint height) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkSurface> newSurface = surface->makeSurface(width, height);
    return reinterpret_cast<jlong>(newSurface.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nMakeSurfaceI
  (JNIEnv* env, jobject jmodule, jlong ptr, jint width, jint height, jint colorType, jint alphaType, jlong colorSpacePtr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    SkImageInfo imageInfo = SkImageInfo::Make(width,
                                              height,
                                              static_cast<SkColorType>(colorType),
                                              static_cast<SkAlphaType>(alphaType),
                                              sk_ref_sp<SkColorSpace>(colorSpace));
    sk_sp<SkSurface> newSurface = surface->makeSurface(imageInfo);
    return reinterpret_cast<jlong>(newSurface.release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nDraw
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong canvasPtr, jfloat x, jfloat y, jlong paintPtr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    surface->draw(canvas, x, y, paint);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Surface_00024__1nPeekPixels
  (JNIEnv *env, jobject jmodule, jlong ptr, jlong dstPixmapPtr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    SkPixmap* pixmap = reinterpret_cast<SkPixmap*>(static_cast<uintptr_t>(dstPixmapPtr));
    return static_cast<jboolean>(surface->peekPixels(pixmap));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Surface_00024__1nNotifyContentWillChange
  (JNIEnv* env, jobject jmodule, jlong ptr, jint mode) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    surface->notifyContentWillChange(static_cast<SkSurface::ContentChangeMode>(mode));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Surface_00024__1nGetRecordingContext
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkSurface* surface = reinterpret_cast<SkSurface*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(surface->recordingContext());
}