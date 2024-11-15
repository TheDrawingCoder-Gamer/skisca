#include <iostream>
#include <jni.h>
#include "GrDirectContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nMakeGL
  (JNIEnv* env, jobject) {
    return reinterpret_cast<jlong>(GrDirectContext::MakeGL().release());
}

#ifdef SK_METAL
#include "mtl/GrMtlBackendContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nMakeMetal
  (JNIEnv* env, jobject, long devicePtr, long queuePtr) {
    GrMtlBackendContext backendContext = {};
    GrMTLHandle device = reinterpret_cast<GrMTLHandle>(static_cast<uintptr_t>(devicePtr));
    GrMTLHandle queue = reinterpret_cast<GrMTLHandle>(static_cast<uintptr_t>(queuePtr));
    backendContext.fDevice.retain(device);
    backendContext.fQueue.retain(queue);
    sk_sp<GrDirectContext> instance = GrDirectContext::MakeMetal(backendContext);
    return reinterpret_cast<jlong>(instance.release());
}
#endif

#ifdef SK_DIRECT3D
#include "d3d/GrD3DBackendContext.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nMakeDirect3D
  (JNIEnv* env, jobject, jlong adapterPtr, jlong devicePtr, jlong queuePtr) {
    GrD3DBackendContext backendContext = {};
    IDXGIAdapter1* adapter = reinterpret_cast<IDXGIAdapter1*>(static_cast<uintptr_t>(adapterPtr));
    ID3D12Device* device = reinterpret_cast<ID3D12Device*>(static_cast<uintptr_t>(devicePtr));
    ID3D12CommandQueue* queue = reinterpret_cast<ID3D12CommandQueue*>(static_cast<uintptr_t>(queuePtr));
    backendContext.fAdapter.retain(adapter);
    backendContext.fDevice.retain(device);
    backendContext.fQueue.retain(queue);
    sk_sp<GrDirectContext> instance = GrDirectContext::MakeDirect3D(backendContext);
    return reinterpret_cast<jlong>(instance.release());
}
#endif //SK_DIRECT3D 

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nFlush
  (JNIEnv* env, jobject, jlong ptr) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->flush(GrFlushInfo());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nSubmit
  (JNIEnv* env, jobject, jlong ptr, jboolean syncCpu) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->submit(syncCpu);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nReset
  (JNIEnv* env, jobject, jlong ptr, jint flags) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->resetContext((uint32_t) flags);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_DirectContext_00024__1nAbandon
  (JNIEnv* env, jobject, jlong ptr, jint flags) {
    GrDirectContext* context = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ptr));
    context->abandonContext();
}
