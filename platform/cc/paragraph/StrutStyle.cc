#include <iostream>
#include <jni.h>
#include <vector>
#include "../interop.hh"
#include "interop.hh"
#include "ParagraphStyle.h"

using namespace std;
using namespace skia::textlayout;

static void deleteStrutStyle(StrutStyle* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteStrutStyle));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nMake
  (JNIEnv* env, jobject jmodule) {
    StrutStyle* instance = new StrutStyle();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nEquals
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong otherPtr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    StrutStyle* other = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(otherPtr));
    return *instance == *other;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetFontFamilies
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return javaStringArray(env, instance->getFontFamilies());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetFontFamilies
  (JNIEnv* env, jobject jmodule, jlong ptr, jobjectArray familiesArr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setFontFamilies(skStringVector(env, familiesArr));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetFontStyle
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return skija::FontStyle::toJava(instance->getFontStyle());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetFontStyle
  (JNIEnv* env, jobject jmodule, jlong ptr, jint style) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setFontStyle(skija::FontStyle::fromJava(style));
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetFontSize
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getFontSize();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetFontSize
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat size) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setFontSize(size);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getHeight();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat height) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setHeight(height);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nGetLeading
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getLeading();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetLeading
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat leading) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setLeading(leading);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nIsEnabled
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getStrutEnabled();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetEnabled
  (JNIEnv* env, jobject jmodule, jlong ptr, jboolean value) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setStrutEnabled(value);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nIsHeightForced
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getForceStrutHeight();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetHeightForced
  (JNIEnv* env, jobject jmodule, jlong ptr, jboolean value) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setForceStrutHeight(value);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nIsHeightOverridden
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getHeightOverride();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_StrutStyle_00024__1nSetHeightOverridden
  (JNIEnv* env, jobject jmodule, jlong ptr, jboolean value) {
    StrutStyle* instance = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(ptr));
    instance->setHeightOverride(value);
}
