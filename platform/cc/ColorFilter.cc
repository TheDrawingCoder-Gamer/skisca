#include <iostream>
#include <jni.h>
#include "SkColorFilter.h"
#include "SkColorMatrixFilter.h"
#include "SkHighContrastFilter.h"
#include "SkLumaColorFilter.h"
#include "SkOverdrawColorFilter.h"
#include "interop.hh"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeComposed
  (JNIEnv* env, jobject, jlong outerPtr, jlong innerPtr) {
    SkColorFilter* outer = reinterpret_cast<SkColorFilter*>(static_cast<uintptr_t>(outerPtr));
    SkColorFilter* inner = reinterpret_cast<SkColorFilter*>(static_cast<uintptr_t>(innerPtr));
    SkColorFilter* ptr = SkColorFilters::Compose(sk_ref_sp(outer), sk_ref_sp(inner)).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeBlend
  (JNIEnv* env, jobject, jint colorInt, jint modeInt) {
    SkColor color = static_cast<SkColor>(colorInt);
    SkBlendMode mode = static_cast<SkBlendMode>(modeInt);
    SkColorFilter* ptr = SkColorFilters::Blend(color, mode).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeMatrix
  (JNIEnv* env, jobject, jfloatArray rowMajorArray) {
    jfloat* rowMajor = env->GetFloatArrayElements(rowMajorArray, 0);
    SkColorFilter* ptr = SkColorFilters::Matrix(rowMajor).release();
    env->ReleaseFloatArrayElements(rowMajorArray, rowMajor, 0);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeHSLAMatrix
  (JNIEnv* env, jobject, jfloatArray rowMajorArray) {
    jfloat* rowMajor = env->GetFloatArrayElements(rowMajorArray, 0);
    SkColorFilter* ptr = SkColorFilters::HSLAMatrix(rowMajor).release();
    env->ReleaseFloatArrayElements(rowMajorArray, rowMajor, 0);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nGetLinearToSRGBGamma
  (JNIEnv* env, jobject) {
    SkColorFilter* ptr = SkColorFilters::LinearToSRGBGamma().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nGetSRGBToLinearGamma
  (JNIEnv* env, jobject) {
    SkColorFilter* ptr = SkColorFilters::SRGBToLinearGamma().release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeLerp
  (JNIEnv* env, jobject, jfloat t, jlong dstPtr, jlong srcPtr) {
    SkColorFilter* dst = reinterpret_cast<SkColorFilter*>(static_cast<uintptr_t>(dstPtr));
    SkColorFilter* src = reinterpret_cast<SkColorFilter*>(static_cast<uintptr_t>(srcPtr));
    SkColorFilter* ptr = SkColorFilters::Lerp(t, sk_ref_sp(dst), sk_ref_sp(src)).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeLighting
  (JNIEnv* env, jobject, jint mul, jint add) {
    SkColorFilter* ptr = SkColorMatrixFilter::MakeLightingFilter(mul, add).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeHighContrast
  (JNIEnv* env, jobject, jboolean grayscale, jint inverionModeInt, jfloat contrast) {
    SkHighContrastConfig config(grayscale, static_cast<SkHighContrastConfig::InvertStyle>(inverionModeInt), contrast);
    SkColorFilter* ptr = SkHighContrastFilter::Make(config).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeTable
  (JNIEnv* env, jobject, jbyteArray tableArray) {
    jbyte* table = env->GetByteArrayElements(tableArray, 0);
    sk_sp<SkColorFilter> ptr = SkColorFilters::Table(reinterpret_cast<uint8_t*>(table));
    env->ReleaseByteArrayElements(tableArray, table, 0);
    return reinterpret_cast<jlong>(ptr.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeTableARGB
  (JNIEnv* env, jobject, jbyteArray arrayA, jbyteArray arrayR, jbyteArray arrayG, jbyteArray arrayB) {
    jbyte* a = arrayA ? env->GetByteArrayElements(arrayA, 0) : nullptr;
    jbyte* r = arrayR ? env->GetByteArrayElements(arrayR, 0) : nullptr;
    jbyte* g = arrayG ? env->GetByteArrayElements(arrayG, 0) : nullptr;
    jbyte* b = arrayB ? env->GetByteArrayElements(arrayB, 0) : nullptr;

    sk_sp<SkColorFilter> ptr = SkColorFilters::TableARGB(reinterpret_cast<uint8_t*>(a), reinterpret_cast<uint8_t*>(r), reinterpret_cast<uint8_t*>(g), reinterpret_cast<uint8_t*>(b));
    
    if (arrayA) env->ReleaseByteArrayElements(arrayA, a, 0);
    if (arrayR) env->ReleaseByteArrayElements(arrayR, r, 0);
    if (arrayG) env->ReleaseByteArrayElements(arrayG, g, 0);
    if (arrayB) env->ReleaseByteArrayElements(arrayB, b, 0);
    
    return reinterpret_cast<jlong>(ptr.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nMakeOverdraw
  (JNIEnv* env, jobject, jint c0, jint c1, jint c2, jint c3, jint c4, jint c5) {
    SkColor colors[6];
    colors[0] = c0;
    colors[1] = c1;
    colors[2] = c2;
    colors[3] = c3;
    colors[4] = c4;
    colors[5] = c5;
    SkColorFilter* ptr = SkOverdrawColorFilter::MakeWithSkColors(colors).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_ColorFilter_00024__1nGetLuma
  (JNIEnv* env, jobject) {
    SkColorFilter* ptr = SkLumaColorFilter::Make().release();
    return reinterpret_cast<jlong>(ptr);
}
