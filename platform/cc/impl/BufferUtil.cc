#include <jni.h>
#include "../interop.hh"

namespace {
    jclass klass_IllegalArgumentException = nullptr;
    jmethodID method_Ctor = nullptr;
}

extern "C" {
    JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_impl_BufferUtil_00024__1nGetByteBufferFromPointer
      (JNIEnv *env, jobject, jlong ptr, jint size) {
        return env->NewDirectByteBuffer(jlongToPtr<void*>(ptr), size);
    }

    JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_impl_BufferUtil_00024__1nGetPointerFromByteBuffer
      (JNIEnv *env, jobject, jobject buffer) {
        return ptrToJlong(env->GetDirectBufferAddress(buffer));
    }
}