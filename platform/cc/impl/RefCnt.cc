#include <jni.h>
#include "SkRefCnt.h"

class SkRefCntHack {
public:
    void* x;
    mutable std::atomic<int32_t> fRefCnt;
};

void unrefSkRefCnt(SkRefCnt* p) {
    p->unref();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_impl_RefCnt_00024__1nGetFinalizer(JNIEnv* env, jobject obj) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&unrefSkRefCnt));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_impl_RefCnt_00024__1nGetRefCount(JNIEnv* env, jobject obj, jlong ptr) {
    SkRefCnt* instance = reinterpret_cast<SkRefCnt*>(static_cast<uintptr_t>(ptr));
    return reinterpret_cast<SkRefCntHack*>(instance)->fRefCnt.load(std::memory_order_relaxed);
}
