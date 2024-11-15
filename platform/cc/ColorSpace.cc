#include <iostream>
#include <jni.h>
#include "SkColorSpace.h"
#include "interop.hh"

static void unrefColorSpace(SkColorSpace* ptr) {
    // std::cout << "Deleting [SkColorSpace " << ptr << "]" << std::endl;
    ptr->unref();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nGetFinalizer(JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&unrefColorSpace));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nMakeSRGB(JNIEnv* env, jobject) {
    SkColorSpace* ptr = SkColorSpace::MakeSRGB().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nMakeSRGBLinear(JNIEnv* env, jobject) {
    SkColorSpace* ptr = SkColorSpace::MakeSRGBLinear().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nMakeDisplayP3(JNIEnv* env, jobject) {
    SkColorSpace* ptr = SkColorSpace::MakeRGB(SkNamedTransferFn::kSRGB, SkNamedGamut::kDisplayP3).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nConvert
  (JNIEnv* env, jobject, jlong fromPtr, jlong toPtr, float r, float g, float b, float a) {
    SkColorSpace* from = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(fromPtr));
    SkColorSpace* to = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(toPtr));

    skcms_TransferFunction fromFn;
    from->transferFn(&fromFn);

    skcms_TransferFunction toFn;
    to->invTransferFn(&toFn);

    float r1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, r));
    float g1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, g));
    float b1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, b));
    float a1 = skcms_TransferFunction_eval(&toFn, skcms_TransferFunction_eval(&fromFn, a));
    return javaFloatArray(env, {r1, g1, b1, a1});
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nIsGammaCloseToSRGB
  (JNIEnv* env, jobject, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->gammaCloseToSRGB();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nIsGammaLinear
  (JNIEnv* env, jobject, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->gammaIsLinear();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorSpace_00024__1nIsSRGB
  (JNIEnv* env, jobject, jlong ptr) {
    SkColorSpace* instance = reinterpret_cast<SkColorSpace*>(static_cast<uintptr_t>(ptr));
    return instance->isSRGB();
}
