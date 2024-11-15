#include <jni.h>
#include "../interop.hh"
#include "SkShaper.h"

static void deleteTextBlobBuilderRunHandler(SkTextBlobBuilderRunHandler* instance) {
    // std::cout << "Deleting [SkTextBlobBuilderRunHandler " << instance << "]" << std::endl;
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextBlobBuilderRunHandler_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteTextBlobBuilderRunHandler));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextBlobBuilderRunHandler_00024__1nMake
  (JNIEnv* env, jobject jmodule, jlong textPtr, jfloat offsetX, jfloat offsetY) {
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));
    auto instance = new SkTextBlobBuilderRunHandler(text->c_str(), {offsetX, offsetY});
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextBlobBuilderRunHandler_00024__1nMakeBlob
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkTextBlobBuilderRunHandler* instance = reinterpret_cast<SkTextBlobBuilderRunHandler*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->makeBlob().release());
}
