#include <iostream>
#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "ParagraphStyle.h"

using namespace std;
using namespace skia::textlayout;

static void deleteParagraphStyle(ParagraphStyle* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteParagraphStyle));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nMake
  (JNIEnv* env, jobject jmodule) {
    ParagraphStyle* instance = new ParagraphStyle();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nEquals
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong otherPtr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    ParagraphStyle* other = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(otherPtr));
    return *instance == *other;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetStrutStyle
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    StrutStyle* res = new StrutStyle();
    *res = instance->getStrutStyle();
    return reinterpret_cast<jlong>(res);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetStrutStyle
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong stylePtr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    StrutStyle* style = reinterpret_cast<StrutStyle*>(static_cast<uintptr_t>(stylePtr));
    instance->setStrutStyle(*style);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetTextStyle
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    const TextStyle& style = instance->getTextStyle();
    TextStyle* res = new TextStyle(style);
    return reinterpret_cast<jlong>(res);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetTextStyle
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong textStylePtr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    TextStyle* textStyle = reinterpret_cast<TextStyle*>(static_cast<uintptr_t>(textStylePtr));
    instance->setTextStyle(*textStyle);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetDirection
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getTextDirection());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetDirection
  (JNIEnv* env, jobject jmodule, jlong ptr, jint textDirection) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setTextDirection(static_cast<TextDirection>(textDirection));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetAlignment
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getTextAlign());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetAlignment
  (JNIEnv* env, jobject jmodule, jlong ptr, jint textAlign) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setTextAlign(static_cast<TextAlign>(textAlign));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetMaxLinesCount
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getMaxLines());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetMaxLinesCount
  (JNIEnv* env, jobject jmodule, jlong ptr, jint count) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setMaxLines(count);
}

extern "C" JNIEXPORT jstring JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetEllipsis
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return instance->ellipsized() ? javaString(env, instance->getEllipsis()) : nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetEllipsis
  (JNIEnv* env, jobject jmodule, jlong ptr, jstring ellipsis) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setEllipsis(skString(env, ellipsis));
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return instance->getHeight();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetHeight
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat height) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setHeight(height);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetHeightMode
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getTextHeightBehavior());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nSetHeightMode
  (JNIEnv* env, jobject jmodule, jlong ptr, jint heightMode) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->setTextHeightBehavior(static_cast<TextHeightBehavior>(heightMode));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nGetEffectiveAlignment
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->effective_align());
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nIsHintingEnabled
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    return instance->hintingIsOn();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphStyle_00024__1nDisableHinting
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphStyle* instance = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(ptr));
    instance->turnHintingOff();
}