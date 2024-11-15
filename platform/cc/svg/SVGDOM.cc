#include <jni.h>
#include "../interop.hh"
#include "SkCanvas.h"
#include "SkData.h"
#include "SkStream.h"
#include "SkSVGDOM.h"
#include "SkSVGSVG.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_svg_SVGDOM_00024__1nMakeFromData
  (JNIEnv* env, jobject, jlong dataPtr) {
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    SkMemoryStream stream(sk_ref_sp(data));
    sk_sp<SkSVGDOM> instance = SkSVGDOM::MakeFromStream(stream);
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_svg_SVGDOM_00024__1nGetRoot
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGDOM* instance = reinterpret_cast<SkSVGDOM*>(static_cast<uintptr_t>(ptr));
    SkSVGSVG* root = instance->getRoot();
    root->ref();
    return reinterpret_cast<jlong>(root);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGDOM_00024__1nGetContainerSize
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGDOM* instance = reinterpret_cast<SkSVGDOM*>(static_cast<uintptr_t>(ptr));
    const SkSize& size = instance->containerSize();
    return types::Point::make(env, size.fWidth, size.fHeight);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGDOM_00024__1nSetContainerSize
  (JNIEnv* env, jobject, jlong ptr, jfloat width, jfloat height) {
    SkSVGDOM* instance = reinterpret_cast<SkSVGDOM*>(static_cast<uintptr_t>(ptr));
    instance->setContainerSize(SkSize{width, height});
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGDOM_00024__1nRender
  (JNIEnv* env, jobject, jlong ptr, jlong canvasPtr) {
    SkSVGDOM* instance = reinterpret_cast<SkSVGDOM*>(static_cast<uintptr_t>(ptr));
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    instance->render(canvas);
}
