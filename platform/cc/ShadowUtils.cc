#include "interop.hh"
#include <jni.h>
#include "SkShadowUtils.h"
#include "SkPoint3.h"

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_ShadowUtils_00024__1nDrawShadow
  (JNIEnv* env, jobject, jlong canvasPtr, jlong pathPtr, jfloat zPlaneX, jfloat zPlaneY, jfloat zPlaneZ,
        jfloat lightPosX, jfloat lightPosY, jfloat lightPosZ, jfloat lightRadius, jint ambientColor, jint spotColor, jint flags) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkShadowUtils::DrawShadow(canvas, *path, {zPlaneX, zPlaneY, zPlaneZ}, {lightPosX, lightPosY, lightPosZ}, lightRadius, ambientColor, spotColor, flags);
}

extern "C" JNIEXPORT int JNICALL Java_gay_menkissing_skisca_ShadowUtils_00024__1nComputeTonalAmbientColor
  (JNIEnv* env, jobject, jint ambientColor, jint spotColor) {
    SkColor outAmbientColor, outSpotColor;
    SkShadowUtils::ComputeTonalColors(ambientColor, spotColor, &outAmbientColor, &outSpotColor);
    return outAmbientColor;
}

extern "C" JNIEXPORT int JNICALL Java_gay_menkissing_skisca_ShadowUtils_00024__1nComputeTonalSpotColor
  (JNIEnv* env, jobject, jint ambientColor, jint spotColor) {
    SkColor outAmbientColor, outSpotColor;
    SkShadowUtils::ComputeTonalColors(ambientColor, spotColor, &outAmbientColor, &outSpotColor);
    return outSpotColor;
}