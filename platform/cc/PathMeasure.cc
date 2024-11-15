#include <jni.h>
#include "SkPathMeasure.h"
#include "interop.hh"

static void deletePathMeasure(SkPathMeasure* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetFinalizer(JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deletePathMeasure));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nMake
  (JNIEnv* env, jobject) {
    return reinterpret_cast<jlong>(new SkPathMeasure());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nMakePath
  (JNIEnv* env, jobject, jlong pathPtr, jboolean forceClosed, jfloat resScale) {
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    return reinterpret_cast<jlong>(new SkPathMeasure(*path, forceClosed, resScale));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nSetPath
  (JNIEnv* env, jobject, jlong ptr, jlong pathPtr, jboolean forceClosed) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    instance->setPath(path, forceClosed);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetLength
  (JNIEnv* env, jobject, jlong ptr) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    return instance->getLength();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetPosition
  (JNIEnv* env, jobject, jlong ptr, jfloat distance) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkPoint position;
    if (instance->getPosTan(distance, &position, nullptr))
        return types::Point::fromSkPoint(env, position);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetTangent
  (JNIEnv* env, jobject, jlong ptr, jfloat distance) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkVector tangent;
    if (instance->getPosTan(distance, nullptr, &tangent))
        return types::Point::fromSkPoint(env, tangent);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetRSXform
  (JNIEnv* env, jobject, jlong ptr, jfloat distance) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkPoint position;
    SkVector tangent;
    if (instance->getPosTan(distance, &position, &tangent))
        return env->NewObject(skija::RSXform::cls, skija::RSXform::ctor, tangent.fX, tangent.fY, position.fX, position.fY);
    else
        return nullptr;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetMatrix
  (JNIEnv* env, jobject, jlong ptr, jfloat distance, jboolean getPosition, jboolean getTangent) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkMatrix matrix;
    int flags = 0;
    
    if (getPosition)
        flags |= SkPathMeasure::MatrixFlags::kGetPosition_MatrixFlag;
    if (getTangent)
        flags |= SkPathMeasure::MatrixFlags::kGetTangent_MatrixFlag;

    if (instance->getMatrix(distance, &matrix, static_cast<SkPathMeasure::MatrixFlags>(flags))) {
        std::vector<float> floats(9);
        matrix.get9(floats.data());
        return javaFloatArray(env, floats);
    } else
        return nullptr;
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nGetSegment
  (JNIEnv* env, jobject, jlong ptr, jfloat startD, jfloat endD, jlong dstPtr, jboolean startWithMoveTo) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    SkPath* dst = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(dstPtr));
    return instance->getSegment(startD, endD, dst, startWithMoveTo);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nIsClosed
  (JNIEnv* env, jobject, jlong ptr) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    return instance->isClosed();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_PathMeasure_00024__1nNextContour
  (JNIEnv* env, jobject, jlong ptr) {
    SkPathMeasure* instance = reinterpret_cast<SkPathMeasure*>(static_cast<uintptr_t>(ptr));
    return instance->nextContour();
}