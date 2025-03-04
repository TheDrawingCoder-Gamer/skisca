#include <iostream>
#include <jni.h>
#include "SkMaskFilter.h"
#include "SkShader.h"
#include "SkShaderMaskFilter.h"
#include "SkTableMaskFilter.h"
#include "interop.hh"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeBlur
  (JNIEnv* env, jobject jobject, jint blurStyleInt, jfloat sigma, jboolean respectCTM) {
    SkBlurStyle blurStyle = static_cast<SkBlurStyle>(blurStyleInt);
    SkMaskFilter* ptr = SkMaskFilter::MakeBlur(blurStyle, sigma, respectCTM).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeShader
  (JNIEnv* env, jobject jobject, jlong shaderPtr) {
    SkShader* shader = reinterpret_cast<SkShader*>(static_cast<uintptr_t>(shaderPtr));
    SkMaskFilter* ptr = SkShaderMaskFilter::Make(sk_ref_sp(shader)).release();
    return reinterpret_cast<jlong>(ptr);
}

// extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeEmboss
//   (JNIEnv* env, jobject jobject, jfloat sigma, jfloat x, jfloat y, jfloat z, jint pad, jint ambient, jint specular) {
//     SkEmbossMaskFilter::Light light{ {x, y, z}, pad, ambient, specular };
//     SkMaskFilter* ptr = SkEmbossMaskFilter::Make(sigma, light).release();
//     return reinterpret_cast<jlong>(ptr);
// }

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeTable
  (JNIEnv* env, jobject jobject, jbyteArray tableArray) {
    jbyte* table = env->GetByteArrayElements(tableArray, 0);
    SkMaskFilter* ptr = SkTableMaskFilter::Create(reinterpret_cast<uint8_t*>(table));
    env->ReleaseByteArrayElements(tableArray, table, 0);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeGamma
  (JNIEnv* env, jobject jobject, jfloat gamma) {
    SkMaskFilter* ptr = SkTableMaskFilter::CreateGamma(gamma);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_MaskFilter_00024__1nMakeClip
  (JNIEnv* env, jobject jobject, jbyte min, jbyte max) {
    SkMaskFilter* ptr = SkTableMaskFilter::CreateClip(static_cast<uint8_t>(min), static_cast<uint8_t>(max));
    return reinterpret_cast<jlong>(ptr);
}