#include <algorithm>
#include <cfloat>
#include <iostream>
#include <vector>
#include <jni.h>
#include "SkPath.h"
#include "SkPathOps.h"
#include "interop.hh"
#include "include/utils/SkParsePath.h"

static void deletePath(SkPath* path) {
    // std::cout << "Deleting [SkPath " << path << "]" << std::endl;
    delete path;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetFinalizer(JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePath));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nMake(JNIEnv* env, jobject jmodule) {
    SkPath* obj = new SkPath();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nMakeFromSVGString
  (JNIEnv* env, jobject jmodule, jstring d) {
    SkPath* obj = new SkPath();
    SkString s = skString(env, d);
    if (SkParsePath::FromSVGString(s.c_str(), obj))
        return reinterpret_cast<jlong>(obj);
    else {
        delete obj;
        return 0;
    }
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nEquals(JNIEnv* env, jobject jmodule, jlong aPtr, jlong bPtr) {
    SkPath* a = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(aPtr));
    SkPath* b = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(bPtr));
    return *a == *b;
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsInterpolatable(JNIEnv* env, jobject jmodule, jlong ptr, jlong comparePtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* compare = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(comparePtr));
    return instance->isInterpolatable(*compare);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nMakeLerp(JNIEnv* env, jobject jmodule, jlong ptr, jlong endingPtr, jfloat weight) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* ending = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(endingPtr));
    SkPath* out = new SkPath();
    if (instance->interpolate(*ending, weight, out)) {
        return reinterpret_cast<jlong>(out);
    } else {
        delete out;
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetFillMode(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getFillType());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nSetFillMode(JNIEnv* env, jobject jmodule, jlong ptr, jint fillMode) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->setFillType(static_cast<SkPathFillType>(fillMode));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsConvex(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isConvex();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsOval(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect bounds;
    if (instance->isOval(&bounds))
        return types::Rect::fromSkRect(env, bounds);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsRRect(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRRect rrect;
    if (instance->isRRect(&rrect))
        return types::RRect::fromSkRRect(env, rrect);
    else
        return nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nReset(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRewind(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rewind();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsEmpty(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsLastContourClosed(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isLastContourClosed();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsFinite(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isFinite();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsVolatile(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isVolatile();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nSetVolatile(JNIEnv* env, jobject jmodule, jlong ptr, jboolean isVolatile) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->setIsVolatile(isVolatile);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsLineDegenerate(JNIEnv* env, jobject jmodule, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jboolean exact) {
    return SkPath::IsLineDegenerate({x0, y0}, {x1, y1}, exact);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsQuadDegenerate(JNIEnv* env, jobject jmodule, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jboolean exact) {
    return SkPath::IsQuadDegenerate({x0, y0}, {x1, y1}, {x2, y2}, exact);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsCubicDegenerate(JNIEnv* env, jobject jmodule, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat x3, jfloat y3, jboolean exact) {
    return SkPath::IsCubicDegenerate({x0, y0}, {x1, y1}, {x2, y2}, {x3, y3}, exact);
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_gay_menkissing_skisca_Path_00024__1nMaybeGetAsLine(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPoint line[2];
    if (instance->isLine(line)) {
        jobjectArray res = env->NewObjectArray(2, types::Point::cls, nullptr);
        env->SetObjectArrayElement(res, 0, types::Point::fromSkPoint(env, line[0]));
        env->SetObjectArrayElement(res, 1, types::Point::fromSkPoint(env, line[1]));
        return res;
    } else
        return nullptr;
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetPointsCount(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->countPoints();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetPoint(JNIEnv* env, jobject jmodule, jlong ptr, jint index) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPoint p = instance->getPoint(index);
    return types::Point::fromSkPoint(env, p);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetPoints(JNIEnv* env, jobject jmodule, jlong ptr, jobjectArray pointsArray, jint max) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    std::vector<SkPoint> p(std::min<jint>(max, instance->countPoints()));
    int count = instance->getPoints(p.data(), max);
    for (int i = 0; i < max && i < count; ++ i)
        env->SetObjectArrayElement(pointsArray, i, types::Point::fromSkPoint(env, p[i]));
    return count;
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nCountVerbs(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->countVerbs();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetVerbs(JNIEnv* env, jobject jmodule, jlong ptr, jbyteArray verbsArray, jint max) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    jbyte* verbs = verbsArray == nullptr ? nullptr : env->GetByteArrayElements(verbsArray, 0);
    int count = instance->getVerbs(reinterpret_cast<uint8_t *>(verbs), max);
    if (verbsArray != nullptr)
        env->ReleaseByteArrayElements(verbsArray, verbs, 0);
    return count;
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nApproximateBytesUsed(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return (jint) instance->approximateBytesUsed();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nSwap(JNIEnv* env, jobject jmodule, jlong ptr, jlong otherPtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* other = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(otherPtr));
    instance->swap(*other);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetBounds(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->getBounds());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nUpdateBoundsCache(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->updateBoundsCache();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nComputeTightBounds(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->computeTightBounds());
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nConservativelyContainsRect(JNIEnv* env, jobject jmodule, jlong ptr, float l, float t, float r, float b) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect rect {l, t, r, b};
    return instance->conservativelyContainsRect(rect);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nIncReserve(JNIEnv* env, jobject jmodule, jlong ptr, int extraPtCount) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->incReserve(extraPtCount);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nMoveTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x, jfloat y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->moveTo(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRMoveTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx, jfloat dy) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rMoveTo(dx, dy);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nLineTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x, jfloat y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->lineTo(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRLineTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx, jfloat dy) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rLineTo(dx, dy);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nQuadTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->quadTo(x1, y1, x2, y2);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRQuadTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rQuadTo(dx1, dy1, dx2, dy2);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nConicTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat w) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->conicTo(x1, y1, x2, y2, w);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRConicTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2, jfloat w) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rConicTo(dx1, dy1, dx2, dy2, w);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nCubicTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat x3, jfloat y3) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->cubicTo(x1, y1, x2, y2, x3, y3);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nRCubicTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx1, jfloat dy1, jfloat dx2, jfloat dy2, jfloat dx3, jfloat dy3) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rCubicTo(dx1, dy1, dx2, dy2, dx3, dy3);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nArcTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloat startAngle, jfloat sweepAngle, jboolean forceMoveTo) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->arcTo({left, top, right, bottom}, startAngle, sweepAngle, forceMoveTo);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nTangentArcTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat radius) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->arcTo(x1, y1, x2, y2, radius);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nEllipticalArcTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat rx, jfloat ry, jfloat xAxisRotate, jint size, jint direction, jfloat x, float y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->arcTo(rx, ry, xAxisRotate, static_cast<SkPath::ArcSize>(size), static_cast<SkPathDirection>(direction), x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nREllipticalArcTo(JNIEnv* env, jobject jmodule, jlong ptr, jfloat rx, jfloat ry, jfloat xAxisRotate, jint size, jint direction, jfloat dx, float dy) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->rArcTo(rx, ry, xAxisRotate, static_cast<SkPath::ArcSize>(size), static_cast<SkPathDirection>(direction), dx, dy);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nClosePath(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->close();
}

extern "C" JNIEXPORT jobjectArray Java_gay_menkissing_skisca_Path_00024__1nConvertConicToQuads
  (JNIEnv* env, jobject jmodule, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jfloat x2, jfloat y2, jfloat w, jint pow2) {
    std::vector<SkPoint> pts(1 + 2 * (1 << pow2));
    int count = SkPath::ConvertConicToQuads({x0, y0}, {x1, y1}, {x2, y2}, w, pts.data(), pow2);
    jobjectArray res = env->NewObjectArray(count, types::Point::cls, nullptr);
    for (int i = 0; i < count; ++i) {
        env->SetObjectArrayElement(res, i, types::Point::fromSkPoint(env, pts[i]));
    }
    return res;
}

extern "C" JNIEXPORT jobject Java_gay_menkissing_skisca_Path_00024__1nIsRect
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRect rect;
    if (instance->isRect(&rect))
        return types::Rect::fromSkRect(env, rect);
    else
        return nullptr;
}

extern "C" JNIEXPORT void Java_gay_menkissing_skisca_Path_00024__1nAddRect
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jint dirInt, jint start) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addRect({l, t, r, b}, dir, start);
}

extern "C" JNIEXPORT void Java_gay_menkissing_skisca_Path_00024__1nAddOval
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jint dirInt, jint start) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addOval({l, t, r, b}, dir, start);
}

extern "C" JNIEXPORT void Java_gay_menkissing_skisca_Path_00024__1nAddCircle
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat x, jfloat y, jfloat r, jint dirInt) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addCircle(x, y, r, dir);
}

extern "C" JNIEXPORT void Java_gay_menkissing_skisca_Path_00024__1nAddArc
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jfloat startAngle, jfloat sweepAngle) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->addArc({l, t, r, b}, startAngle, sweepAngle);
}

extern "C" JNIEXPORT void Java_gay_menkissing_skisca_Path_00024__1nAddRRect
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat l, jfloat t, jfloat r, jfloat b, jfloatArray radii, jint dirInt, jint start) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkRRect rrect = types::RRect::toSkRRect(env, l, t, r, b, radii);
    SkPathDirection dir = static_cast<SkPathDirection>(dirInt);
    instance->addRRect(rrect, dir, start);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nAddPoly
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloatArray coords, jboolean close) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    jsize len = env->GetArrayLength(coords);
    jfloat* arr = env->GetFloatArrayElements(coords, 0);
    instance->addPoly(reinterpret_cast<SkPoint*>(arr), len / 2, close);
    env->ReleaseFloatArrayElements(coords, arr, 0);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nAddPath
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong srcPtr, jboolean extend) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, mode);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nAddPathOffset
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong srcPtr, jfloat dx, jfloat dy, jboolean extend) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, dx, dy, mode);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nAddPathTransform
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong srcPtr, jfloatArray matrixArr, jboolean extend) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    SkPath::AddPathMode mode = extend ? SkPath::AddPathMode::kExtend_AddPathMode : SkPath::AddPathMode::kAppend_AddPathMode;
    instance->addPath(*src, *matrix, mode);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nReverseAddPath
  (JNIEnv* env, jobject jmodule, jlong ptr, jlong srcPtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* src = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(srcPtr));
    instance->reverseAddPath(*src);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nOffset
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat dx, jfloat dy, jlong dstPtr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* dst = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(dstPtr));
    instance->offset(dx, dy, dst);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nTransform
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloatArray matrixArr, jlong dstPtr, jboolean pcBool) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPath* dst = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(dstPtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    SkApplyPerspectiveClip pc = pcBool ? SkApplyPerspectiveClip::kYes : SkApplyPerspectiveClip::kNo;
    instance->transform(*matrix, dst, pc);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetLastPt
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    SkPoint out;
    if (instance->getLastPt(&out))
        return types::Point::fromSkPoint(env, out);
    else
        return nullptr;
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nSetLastPt
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat x, jfloat y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->setLastPt(x, y);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetSegmentMasks
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->getSegmentMasks();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nContains
  (JNIEnv* env, jobject jmodule, jlong ptr, jfloat x, jfloat y) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->contains(x, y);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nDump
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->dump();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Path_00024__1nDumpHex
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    instance->dumpHex();
}

extern "C" JNIEXPORT jbyteArray JNICALL Java_gay_menkissing_skisca_Path_00024__1nSerializeToBytes
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    size_t count = instance->writeToMemory(nullptr);
    jbyteArray bytesArray = env->NewByteArray((jsize) count);
    jbyte* bytes = env->GetByteArrayElements(bytesArray, 0);
    instance->writeToMemory(bytes);
    env->ReleaseByteArrayElements(bytesArray, bytes, 0);
    return bytesArray;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nMakeCombining
  (JNIEnv* env, jobject jmodule, jlong aPtr, jlong bPtr, jint jop) {
    SkPath* a = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(aPtr));
    SkPath* b = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(bPtr));
    SkPathOp op = static_cast<SkPathOp>(jop);
    auto res = std::make_unique<SkPath>();
    if (Op(*a, *b, op, res.get()))
        return reinterpret_cast<jlong>(res.release());
    else
        return 0;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Path_00024__1nMakeFromBytes
  (JNIEnv* env, jobject jmodule, jbyteArray bytesArray) {
    SkPath* instance = new SkPath();
    int count = env->GetArrayLength(bytesArray);
    jbyte* bytes = env->GetByteArrayElements(bytesArray, 0);
    if (instance->readFromMemory(bytes, count)) {
        env->ReleaseByteArrayElements(bytesArray, bytes, 0);
        return reinterpret_cast<jlong>(instance);
    } else {
        env->ReleaseByteArrayElements(bytesArray, bytes, 0);
        delete instance;
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Path_00024__1nGetGenerationId
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->getGenerationID();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Path_00024__1nIsValid
  (JNIEnv* env, jobject jmodule, jlong ptr) {
    SkPath* instance = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(ptr));
    return instance->isValid();
}