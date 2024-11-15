#include <jni.h>
#include "../interop.hh"
#include "SkResources.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_resources_DataURIResourceProviderProxy_00024__1nMake
  (JNIEnv* env, jobject, jlong resourceProviderPtr, jboolean predecode) {
    skresources::ResourceProvider* resourceProvider = reinterpret_cast<skresources::ResourceProvider*>(static_cast<uintptr_t>(resourceProviderPtr));
    auto instance = skresources::DataURIResourceProviderProxy::Make(sk_ref_sp(resourceProvider), predecode);
    return ptrToJlong(instance.release());
}