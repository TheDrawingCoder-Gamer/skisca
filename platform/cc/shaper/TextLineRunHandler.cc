#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "TextLineRunHandler.hh"

static void deleteTextLineRunHandler(TextLineRunHandler* instance) {
    // std::cout << "Deleting [TextLineRunHandler " << instance << "]" << std::endl;
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextLineRunHandler_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteTextLineRunHandler));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextLineRunHandler_00024__1nMake
  (JNIEnv* env, jobject jmodule, jlong textPtr) {
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));
    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(*text);
    if (!graphemeIter) return 0;
    auto instance = new TextLineRunHandler(*text, graphemeIter);
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_TextLineRunHandler_00024__1nMakeLine
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    TextLineRunHandler* instance = reinterpret_cast<TextLineRunHandler*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<jlong>(instance->makeLine().release());
}
