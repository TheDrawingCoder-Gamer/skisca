#include <jni.h>
#include "SkData.h"
#include "SkImage.h"
#include "SkPngEncoder.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_EncoderPNG_00024__1nEncode
  (JNIEnv* env, jobject, jlong ctxPtr, jlong imagePtr, jint flagsInt, jint zlibLevel) {
    GrDirectContext* ctx = reinterpret_cast<GrDirectContext*>(static_cast<uintptr_t>(ctxPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkPngEncoder::FilterFlag flags = static_cast<SkPngEncoder::FilterFlag>(flagsInt);
    SkPngEncoder::Options opts {flags, zlibLevel};
    sk_sp<SkData> data = SkPngEncoder::Encode(ctx, image, opts);
    return reinterpret_cast<jlong>(data.release());
}
