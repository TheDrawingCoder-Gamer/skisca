#include <jni.h>
#include "SkPixelRef.h"
#include "interop.hh"

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nGetWidth
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    return instance->width();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nGetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    return instance->height();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nGetRowBytes
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    return instance->rowBytes();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nGetGenerationId
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    return instance->getGenerationID();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nNotifyPixelsChanged
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    instance->notifyPixelsChanged();
}
extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nIsImmutable
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    return instance->isImmutable();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_PixelRef_00024__1nSetImmutable
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPixelRef* instance = reinterpret_cast<SkPixelRef*>(static_cast<uintptr_t>(ptr));
    instance->setImmutable();
}