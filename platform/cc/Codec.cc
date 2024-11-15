#include <iostream>
#include <jni.h>
#include "SkBitmap.h"
#include "SkCodec.h"
#include "SkData.h"
#include "interop.hh"

static void deleteCodec(SkCodec* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetFinalizer(JNIEnv* env, jobject jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteCodec));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Codec_00024__1nMakeFromData
  (JNIEnv* env, jobject jobject, jlong dataPtr) {
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    std::unique_ptr<SkCodec> instance = SkCodec::MakeFromData(sk_ref_sp(data));
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetImageInfo
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return skija::ImageInfo::toJava(env, instance->getInfo());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetSize
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return packISize(instance->dimensions());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetEncodedOrigin
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getOrigin());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetEncodedImageFormat
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getEncodedFormat());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Codec_00024__1nReadPixels
  (JNIEnv* env, jobject jobject, jlong ptr, jlong bitmapPtr, jint frame, jint priorFrame) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    SkCodec::Options opts;
    opts.fFrameIndex = frame;
    opts.fPriorFrame = priorFrame;
    SkCodec::Result result = instance->getPixels(bitmap->info(), bitmap->getPixels(), bitmap->rowBytes(), &opts);
    return static_cast<jint>(result);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetFrameCount
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return instance->getFrameCount();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetFrameInfo
  (JNIEnv* env, jobject jobject, jlong ptr, jint frame) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    SkCodec::FrameInfo info;
    instance->getFrameInfo(frame, &info);
    return skija::AnimationFrameInfo::toJava(env, info);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetFramesInfo
  (JNIEnv* env, jobject jobj, jlong ptr, jint frame) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    std::vector<SkCodec::FrameInfo> frames = instance->getFrameInfo();
    jobjectArray res = env->NewObjectArray((jsize) frames.size(), skija::AnimationFrameInfo::cls, nullptr);
    if (java::lang::Throwable::exceptionThrown(env))
        return nullptr;
    for (int i = 0; i < frames.size(); ++i) {
        skija::AutoLocal<jobject> infoObj(env, skija::AnimationFrameInfo::toJava(env, frames[i]));
        env->SetObjectArrayElement(res, i, infoObj.get());
    }
    return res;
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Codec_00024__1nGetRepetitionCount
  (JNIEnv* env, jobject jobj, jlong ptr) {
    SkCodec* instance = reinterpret_cast<SkCodec*>(static_cast<uintptr_t>(ptr));
    return instance->getRepetitionCount();
}