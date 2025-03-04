#include <iostream>
#include <jni.h>
#include "SkBlendMode.h"
#include "SkColorFilter.h"
#include "SkImageFilter.h"
#include "SkMaskFilter.h"
#include "SkPaint.h"
#include "SkPathEffect.h"
#include "SkPathUtils.h"
#include "SkShader.h"
#include "interop.hh"

static void deletePaint(SkPaint* paint) {
    // std::cout << "Deleting [SkPaint " << paint << "]" << std::endl;
    delete paint;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetFinalizer
  (JNIEnv* env, jobject jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePaint));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nMake
  (JNIEnv* env, jobject jobject) {
    SkPaint* obj = new SkPaint();
    obj->setAntiAlias(true);
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nMakeClone
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkPaint* obj = new SkPaint(*instance);
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Paint_00024__1nEquals
  (JNIEnv* env, jobject jobject, jlong aPtr, jlong bPtr) {
    SkPaint* a = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(aPtr));
    SkPaint* b = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(bPtr));
    return *a == *b;
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nReset
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Paint_00024__1nIsAntiAlias
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->isAntiAlias();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetAntiAlias
  (JNIEnv* env, jobject jobject, jlong ptr, jboolean value) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setAntiAlias(value);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Paint_00024__1nIsDither
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->isDither();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetDither
  (JNIEnv* env, jobject jobject, jlong ptr, jboolean value) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setDither(value);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetColor
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->getColor();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetColor
  (JNIEnv* env, jobject jobject, jlong ptr, jint color) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setColor(color);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetColor4f
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkColor4f color = instance->getColor4f();
    return env->NewObject(skija::Color4f::cls, skija::Color4f::ctor, color.fR, color.fG, color.fB, color.fA);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetColor4f
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat r, jfloat g, jfloat b, jfloat a, jlong colorSpacePtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkColorSpace* colorSpace = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(colorSpacePtr));
    instance->setColor4f({r, g, b, a}, colorSpace);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetMode
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return static_cast<jlong>(instance->getStyle());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetMode
  (JNIEnv* env, jobject jobject, jlong ptr, jint mode) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setStyle(static_cast<SkPaint::Style>(mode));
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetStrokeWidth
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeWidth();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetStrokeWidth
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat width) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeWidth(width);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetStrokeMiter
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->getStrokeMiter();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetStrokeMiter
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat miter) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeMiter(miter);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetStrokeCap
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return static_cast<jlong>(instance->getStrokeCap());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetStrokeCap
  (JNIEnv* env, jobject jobject, jlong ptr, jint cap) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeCap(static_cast<SkPaint::Cap>(cap));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetStrokeJoin
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return static_cast<jlong>(instance->getStrokeJoin());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetStrokeJoin
  (JNIEnv* env, jobject jobject, jlong ptr, jint join) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setStrokeJoin(static_cast<SkPaint::Join>(join));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetFillPath
  (JNIEnv* env, jobject jobject, jlong ptr, jlong srcPtr, jfloat resScale) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath* dst = new SkPath();
    if (skpathutils::FillPathWithPaint(*src, *instance, dst, nullptr, resScale)) {
      return reinterpret_cast<jlong>(dst);
    } else {
      return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetFillPathCull
  (JNIEnv* env, jobject jobject, jlong ptr, jlong srcPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloat resScale) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath* dst = new SkPath();
    SkRect cull {left, top, right, bottom};
    if (skpathutils::FillPathWithPaint(*src, *instance, dst, &cull, resScale)) {
      return reinterpret_cast<jlong>(dst);
    } else {
      return 0;
    }
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetMaskFilter
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->refMaskFilter().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetMaskFilter
  (JNIEnv* env, jobject jobject, jlong ptr, jlong filterPtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkMaskFilter* filter = reinterpret_cast<SkMaskFilter*>(static_cast<uintptr_t>(filterPtr));
    instance->setMaskFilter(sk_ref_sp<SkMaskFilter>(filter));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetImageFilter
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->refImageFilter().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetImageFilter
  (JNIEnv* env, jobject jobject, jlong ptr, jlong filterPtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkImageFilter* filter = reinterpret_cast<SkImageFilter*>(static_cast<uintptr_t>(filterPtr));
    instance->setImageFilter(sk_ref_sp<SkImageFilter>(filter));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetBlendMode
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return static_cast<jlong>(instance->getBlendMode_or(SkBlendMode::kSrcOver));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetBlendMode
  (JNIEnv* env, jobject jobject, jlong ptr, jint mode) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    instance->setBlendMode(static_cast<SkBlendMode>(mode));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetPathEffect
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->refPathEffect().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetPathEffect
  (JNIEnv* env, jobject jobject, jlong ptr, jlong pathEffectPtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkPathEffect* pathEffect = reinterpret_cast<SkPathEffect*>(static_cast<uintptr_t>(pathEffectPtr));
    instance->setPathEffect(sk_ref_sp(pathEffect));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetShader
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->refShader().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetShader
  (JNIEnv* env, jobject jobject, jlong ptr, jlong shaderPtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkShader* shader = reinterpret_cast<SkShader*>(static_cast<uintptr_t>(shaderPtr));
    instance->setShader(sk_ref_sp<SkShader>(shader));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Paint_00024__1nGetColorFilter
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->refColorFilter().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Paint_00024__1nSetColorFilter
  (JNIEnv* env, jobject jobject, jlong ptr, jlong colorFilterPtr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    SkColorFilter* colorFilter = reinterpret_cast<SkColorFilter*>(static_cast<uintptr_t>(colorFilterPtr));
    instance->setColorFilter(sk_ref_sp<SkColorFilter>(colorFilter));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Paint_00024__1nHasNothingToDraw
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkPaint* instance = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(ptr));
    return instance->nothingToDraw();
}
