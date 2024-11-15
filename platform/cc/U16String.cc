#include <string>
#include <jni.h>
#include "interop.hh"
#include "SkString.h"

static void deleteU16String(std::vector<jchar>* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_U16String_00024__1nGetFinalizer
  (JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteU16String));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_U16String_00024__1nMake
  (JNIEnv* env, jobject jmodule, jstring str) {
    jsize len = env->GetStringLength(str);
    std::vector<jchar>* instance = new std::vector<jchar>(len);
    env->GetStringRegion(str, 0, len, instance->data());
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_U16String_00024__1nToString
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    std::vector<jchar>* instance = reinterpret_cast<std::vector<jchar>*>(static_cast<uintptr_t>(ptr));
    return env->NewString(instance->data(), instance->size());
}
