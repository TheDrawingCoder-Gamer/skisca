#include <iostream>
#include <jni.h>
#include <cstdint>

typedef void (*FreeFunction)(void*);

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_impl_Managed_00024__1nInvokeFinalizer
  (JNIEnv* env, jobject jobject, jlong finalizerPtr, jlong ptr) {
    void* instance = reinterpret_cast<void*>(static_cast<uintptr_t>(ptr));
    FreeFunction finalizer = reinterpret_cast<FreeFunction>(static_cast<uintptr_t>(finalizerPtr));
    finalizer(instance);
}