#include <iostream>
#include <jni.h>
#include "../interop.hh"
#include "TypefaceFontProvider.h"
#include "SkTypeface.h"

using namespace skia::textlayout;

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_paragraph_TypefaceFontProvider_00024__1nMake
  (JNIEnv* env, jobject jmodule) {
    TypefaceFontProvider* instance = new TypefaceFontProvider();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_paragraph_TypefaceFontProvider_00024__1nRegisterTypeface
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong typefacePtr, jstring aliasStr) {
    TypefaceFontProvider* instance = reinterpret_cast<TypefaceFontProvider*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    if (aliasStr == nullptr)
        instance->registerTypeface(sk_ref_sp(typeface));
    else
        instance->registerTypeface(sk_ref_sp(typeface), skString(env, aliasStr));
}
