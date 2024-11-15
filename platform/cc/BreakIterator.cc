#include <jni.h>
#include "interop.hh"
#include "unicode/ubrk.h"

static void deleteBreakIterator(UBreakIterator* instance) {
  ubrk_close(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nGetFinalizer(JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteBreakIterator));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nMake
  (JNIEnv* env, jobject, jint type, jstring localeStr) {
    UErrorCode status = U_ZERO_ERROR;
    UBreakIterator* instance;
    if (localeStr == nullptr)
      instance = ubrk_open(static_cast<UBreakIteratorType>(type), uloc_getDefault(), nullptr, 0, &status);
    else {
      SkString locale = skString(env, localeStr);
      instance = ubrk_open(static_cast<UBreakIteratorType>(type), locale.c_str(), nullptr, 0, &status);
    }
    
    if (U_FAILURE(status)) {
      env->ThrowNew(java::lang::RuntimeException::cls, u_errorName(status));
      return 0;
    } else
      return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nClone
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    UErrorCode status = U_ZERO_ERROR;
    UBreakIterator* clone = ubrk_safeClone(instance, nullptr, 0, &status);
    if (U_FAILURE(status)) {
      env->ThrowNew(java::lang::RuntimeException::cls, u_errorName(status));
      return 0;
    } else
    return reinterpret_cast<jlong>(clone);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nCurrent
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_current(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nNext
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_next(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nPrevious
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_previous(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nFirst
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_first(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nLast
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_last(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nPreceding
  (JNIEnv* env, jobject, jlong ptr, jint offset) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_preceding(instance, offset);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nFollowing
  (JNIEnv* env, jobject, jlong ptr, jint offset) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_following(instance, offset);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nIsBoundary
  (JNIEnv* env, jobject, jlong ptr, jint offset) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_isBoundary(instance, offset);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nGetRuleStatus
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    return ubrk_getRuleStatus(instance);
}

extern "C" JNIEXPORT jintArray JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nGetRuleStatuses
  (JNIEnv* env, jobject, jlong ptr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    UErrorCode status = U_ZERO_ERROR;
    int32_t len = ubrk_getRuleStatusVec(instance, nullptr, 0, &status);
    if (U_FAILURE(status))
      env->ThrowNew(java::lang::RuntimeException::cls, u_errorName(status));
    std::vector<jint> vec(len);
    ubrk_getRuleStatusVec(instance, reinterpret_cast<int32_t*>(vec.data()), len, &status);
    if (U_FAILURE(status))
      env->ThrowNew(java::lang::RuntimeException::cls, u_errorName(status));
    return javaIntArray(env, vec);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_BreakIterator_00024__1nSetText
  (JNIEnv* env, jobject, jlong ptr, jlong textPtr) {
    UBreakIterator* instance = reinterpret_cast<UBreakIterator*>(static_cast<uintptr_t>(ptr));
    std::vector<jchar>* text = reinterpret_cast<std::vector<jchar>*>(static_cast<uintptr_t>(textPtr));
    UErrorCode status = U_ZERO_ERROR;
    ubrk_setText(instance, reinterpret_cast<UChar *>(text->data()), (int32_t) text->size(), &status);
    if (U_FAILURE(status))
      env->ThrowNew(java::lang::RuntimeException::cls, u_errorName(status));
}