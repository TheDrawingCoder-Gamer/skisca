#include <iostream>
#include <jni.h>
#include "interop.hh"
#include "SkDrawable.h"
#include "SkPictureRecorder.h"

static void deletePictureRecorder(SkPictureRecorder* pr) {
    // std::cout << "Deleting [SkPictureRecorder " << PictureRecorder << "]" << std::endl;
    delete pr;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nMake
  (JNIEnv* env, jobject) {
    SkPictureRecorder* instance = new SkPictureRecorder();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nGetFinalizer
  (JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePictureRecorder));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nBeginRecording
  (JNIEnv* env, jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom) {
    SkPictureRecorder* instance = reinterpret_cast<SkPictureRecorder*>(static_cast<uintptr_t>(ptr));
    SkCanvas* canvas = instance->beginRecording(SkRect::MakeLTRB(left, top, right, bottom), nullptr);
    return reinterpret_cast<jlong>(canvas);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nFinishRecordingAsPicture
  (JNIEnv* env, jobject, jlong ptr) {
    SkPictureRecorder* instance = reinterpret_cast<SkPictureRecorder*>(static_cast<uintptr_t>(ptr));
    SkPicture* picture = instance->finishRecordingAsPicture().release();
    return reinterpret_cast<jlong>(picture);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nFinishRecordingAsPictureWithCull
  (JNIEnv* env, jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom) {
    SkPictureRecorder* instance = reinterpret_cast<SkPictureRecorder*>(static_cast<uintptr_t>(ptr));
    SkPicture* picture = instance->finishRecordingAsPictureWithCull(SkRect::MakeLTRB(left, top, right, bottom)).release();
    return reinterpret_cast<jlong>(picture);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PictureRecorder_00024__1nFinishRecordingAsDrawable
  (JNIEnv* env, jobject, jlong ptr) {
    SkPictureRecorder* instance = reinterpret_cast<SkPictureRecorder*>(static_cast<uintptr_t>(ptr));
    SkDrawable* drawable = instance->finishRecordingAsDrawable().release();
    return reinterpret_cast<jlong>(drawable);
}