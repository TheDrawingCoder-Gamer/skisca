#include <jni.h>
#include "../interop.hh"
#include "SkResources.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_resources_CachingResourceProvider_00024__1nMake
  (JNIEnv* env, jobject jmodule, jlong resourceProviderPtr) {
    skresources::ResourceProvider* resourceProvider = reinterpret_cast<skresources::ResourceProvider*>(static_cast<uintptr_t>(resourceProviderPtr));
    auto instance = skresources::CachingResourceProvider::Make(sk_ref_sp(resourceProvider));
    return ptrToJlong(instance.release());
}