#include <iostream>
#include <jni.h>
#include "ParagraphCache.h"
#include "ParagraphStyle.h"

using namespace skia::textlayout;

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nAbandon
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    instance->abandon();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nReset
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nUpdateParagraph
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong paragraphPtr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    ParagraphImpl* paragraph = reinterpret_cast<ParagraphImpl*>(static_cast<uintptr_t>(paragraphPtr));
    return instance->updateParagraph(paragraph);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nFindParagraph
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong paragraphPtr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    ParagraphImpl* paragraph = reinterpret_cast<ParagraphImpl*>(static_cast<uintptr_t>(paragraphPtr));
    return instance->findParagraph(paragraph);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nPrintStatistics
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong paragraphPtr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    instance->printStatistics();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nSetEnabled
  (JNIEnv* env, jobject jmodule, jlong ptr, jboolean value) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    instance->turnOn(value);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_paragraph_ParagraphCache_00024__1nGetCount
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    ParagraphCache* instance = reinterpret_cast<ParagraphCache*>(static_cast<uintptr_t>(ptr));
    return instance->count();
}
