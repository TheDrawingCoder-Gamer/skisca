#include <jni.h>
#include "../interop.hh"
#include "interop.hh"
#include "SkSVGSVG.h"
#include "SkSVGRenderContext.h"

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetTag
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->tag());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetX
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    return skija::svg::SVGLength::toJava(env, instance->getX());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetY
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    return skija::svg::SVGLength::toJava(env, instance->getY());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetWidth
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    return skija::svg::SVGLength::toJava(env, instance->getWidth());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetHeight
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    return skija::svg::SVGLength::toJava(env, instance->getHeight());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetPreserveAspectRatio
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    return skija::svg::SVGPreserveAspectRatio::toJava(env, instance->getPreserveAspectRatio());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetViewBox
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkTLazy<SkSVGViewBoxType> viewBox = instance->getViewBox();
    return viewBox.isValid() ? types::Rect::fromSkRect(env, *viewBox.get()) : nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nGetIntrinsicSize
  (JNIEnv* env, jobject, jlong ptr, float width, float height, float dpi) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkSVGLengthContext lc({width, height}, dpi);
    SkSize size = instance->intrinsicSize(lc);
    return types::Point::fromSkPoint(env, {size.width(), size.height()});
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetX
  (JNIEnv* env, jobject, jlong ptr, float value, int unit) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkSVGLength lenght(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setX(lenght);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetY
  (JNIEnv* env, jobject, jlong ptr, float value, int unit) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkSVGLength lenght(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setY(lenght);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetWidth
  (JNIEnv* env, jobject, jlong ptr, float value, int unit) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkSVGLength lenght(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setWidth(lenght);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetHeight
  (JNIEnv* env, jobject, jlong ptr, float value, int unit) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    SkSVGLength lenght(value, static_cast<SkSVGLength::Unit>(unit));
    instance->setHeight(lenght);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetPreserveAspectRatio
  (JNIEnv* env, jobject, jlong ptr, jint align, jint scale) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    instance->setPreserveAspectRatio(SkSVGPreserveAspectRatio { static_cast<SkSVGPreserveAspectRatio::Align>(align),
                                                                static_cast<SkSVGPreserveAspectRatio::Scale>(scale) });
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_svg_SVGSVG_00024__1nSetViewBox
  (JNIEnv* env, jobject, jlong ptr, float l, float t, float r, float b) {
    SkSVGSVG* instance = reinterpret_cast<SkSVGSVG*>(static_cast<uintptr_t>(ptr));
    instance->setViewBox(SkRect::MakeLTRB(l, t, r, b));
}
