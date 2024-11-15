#include <jni.h>
#include "SkData.h"
#include "SkImage.h"
#include "SkJpegEncoder.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_EncoderJPEG_00024__1nEncode
  (JNIEnv* env, jobject, jlong ctxPtr, jlong imagePtr, jint quality, jint alphaMode, jint downsampleMode) {
    GrDirectContext* ctx = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ctxPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkJpegEncoder::Options opts {quality, (SkJpegEncoder::Downsample) downsampleMode, (SkJpegEncoder::AlphaOption) alphaMode};
    sk_sp<SkData> data = SkJpegEncoder::Encode(ctx, image, opts);
    return reinterpret_cast<jlong>(data.release());
}
