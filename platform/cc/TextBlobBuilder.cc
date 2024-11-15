#include <iostream>
#include <jni.h>
#include "SkTextBlob.h"
#include "interop.hh"

static void deleteTextBlobBuilder(SkTextBlobBuilder* ptr) {
    delete ptr;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteTextBlobBuilder));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nMake
  (JNIEnv* env, jobject jmodule) {
    return reinterpret_cast<jlong>(new SkTextBlobBuilder());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nBuild
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkTextBlobBuilder* instance = reinterpret_cast<SkTextBlobBuilder*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->make().release());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nAppendRun
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong fontPtr, jshortArray glyphsArr, jfloat x, jfloat y, jobject boundsObj) {
    SkTextBlobBuilder* instance = reinterpret_cast<SkTextBlobBuilder*>(static_cast<uintptr_t>(ptr));
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    jsize len = env->GetArrayLength(glyphsArr);
    std::unique_ptr<SkRect> bounds = types::Rect::toSkRect(env, boundsObj);
    SkTextBlobBuilder::RunBuffer run = instance->allocRun(*font, len, x, y, bounds.get());
    env->GetShortArrayRegion(glyphsArr, 0, len, reinterpret_cast<jshort*>(run.glyphs));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nAppendRunPosH
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong fontPtr, jshortArray glyphsArr, jfloatArray xsArr, jfloat y, jobject boundsObj) {
    SkTextBlobBuilder* instance = reinterpret_cast<SkTextBlobBuilder*>(static_cast<uintptr_t>(ptr));
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    jsize len = env->GetArrayLength(glyphsArr);
    std::unique_ptr<SkRect> bounds = types::Rect::toSkRect(env, boundsObj);
    SkTextBlobBuilder::RunBuffer run = instance->allocRunPosH(*font, len, y, bounds.get());
    env->GetShortArrayRegion(glyphsArr, 0, len, reinterpret_cast<jshort*>(run.glyphs));
    env->GetFloatArrayRegion(xsArr, 0, len, reinterpret_cast<jfloat*>(run.pos));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nAppendRunPos
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong fontPtr, jshortArray glyphsArr, jfloatArray posArr, jobject boundsObj) {
    SkTextBlobBuilder* instance = reinterpret_cast<SkTextBlobBuilder*>(static_cast<uintptr_t>(ptr));
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    jsize len = env->GetArrayLength(glyphsArr);
    std::unique_ptr<SkRect> bounds = types::Rect::toSkRect(env, boundsObj);
    SkTextBlobBuilder::RunBuffer run = instance->allocRunPos(*font, len, bounds.get());
    env->GetShortArrayRegion(glyphsArr, 0, len, reinterpret_cast<jshort*>(run.glyphs));
    env->GetFloatArrayRegion(posArr, 0, len * 2, reinterpret_cast<jfloat*>(run.pos));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_TextBlobBuilder_00024__1nAppendRunRSXform
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong fontPtr, jshortArray glyphsArr, jfloatArray xformArr) {
    SkTextBlobBuilder* instance = reinterpret_cast<SkTextBlobBuilder*>(static_cast<uintptr_t>(ptr));
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));
    jsize len = env->GetArrayLength(glyphsArr);
    SkTextBlobBuilder::RunBuffer run = instance->allocRunRSXform(*font, len);
    env->GetShortArrayRegion(glyphsArr, 0, len, reinterpret_cast<jshort*>(run.glyphs));
    env->GetFloatArrayRegion(xformArr, 0, len * 4, reinterpret_cast<jfloat*>(run.pos));
}