#include <iostream>
#include <jni.h>
#include <string>
#include "ParagraphBuilder.h"
#include "../interop.hh"

using namespace std;
using namespace skia::textlayout;

static void deleteParagraphBuilder(ParagraphBuilder* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nMake
  (JNIEnv* env, jobject jmodule, jlong paragraphStylePtr, jlong fontCollectionPtr) {
    ParagraphStyle* paragraphStyle = reinterpret_cast<ParagraphStyle*>(static_cast<uintptr_t>(paragraphStylePtr));
    FontCollection* fontCollection = reinterpret_cast<FontCollection*>(static_cast<uintptr_t>(fontCollectionPtr));
    ParagraphBuilder* instance = ParagraphBuilder::make(*paragraphStyle, sk_ref_sp(fontCollection)).release();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteParagraphBuilder));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nPushStyle
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong textStylePtr) {
    ParagraphBuilder* instance = reinterpret_cast<ParagraphBuilder*>(static_cast<uintptr_t>(ptr));
    TextStyle* textStyle = reinterpret_cast<TextStyle*>(static_cast<uintptr_t>(textStylePtr));
    instance->pushStyle(*textStyle);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nPopStyle
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong textStylePtr) {
    ParagraphBuilder* instance = reinterpret_cast<ParagraphBuilder*>(static_cast<uintptr_t>(ptr));
    instance->pop();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nAddText
  (JNIEnv* env, jobject jmodule, jlong ptr, jstring textString) {
    ParagraphBuilder* instance = reinterpret_cast<ParagraphBuilder*>(static_cast<uintptr_t>(ptr));
    SkString text = skString(env, textString);
    instance->addText(text.c_str(), text.size());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nAddPlaceholder
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat width, jfloat height, jint alignment, jint baselinePosition, jfloat baseline) {
    ParagraphBuilder* instance = reinterpret_cast<ParagraphBuilder*>(static_cast<uintptr_t>(ptr));
    instance->addPlaceholder({width, height, static_cast<PlaceholderAlignment>(alignment), static_cast<TextBaseline>(baselinePosition), baseline});
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphBuilder_00024__1nBuild
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphBuilder* instance = reinterpret_cast<ParagraphBuilder*>(static_cast<uintptr_t>(ptr));
    Paragraph* paragraph = instance->Build().release();
    return reinterpret_cast<jlong>(paragraph);
}