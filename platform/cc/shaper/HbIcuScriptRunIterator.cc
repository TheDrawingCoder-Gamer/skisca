#include <jni.h>
#include "../interop.hh"
#include "SkShaper.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_shaper_HbIcuScriptRunIterator_00024__1nMake
  (JNIEnv* env, jobject jclass, jlong textPtr) {
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));
    std::unique_ptr<SkShaper::ScriptRunIterator> instance(SkShaper::MakeHbIcuScriptRunIterator(text->c_str(), text->size()));
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_shaper_HbIcuScriptRunIterator_00024__1nGetCurrentScriptTag
  (JNIEnv* env, jobject jclass, jlong ptr) {
    SkShaper::ScriptRunIterator* instance = reinterpret_cast<SkShaper::ScriptRunIterator*>(static_cast<uintptr_t>(ptr));
    return instance->currentScript();
}
