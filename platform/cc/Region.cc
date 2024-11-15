#include <iostream>
#include <jni.h>
#include "SkRegion.h"
#include "interop.hh"

static void deleteRegion(SkRegion* region) {
    // std::cout << "Deleting [SkRegion " << Region << "]" << std::endl;
    delete region;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Region_00024__1nMake(JNIEnv* env, jobject jmodule) {
    SkRegion* obj = new SkRegion();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Region_00024__1nGetFinalizer(JNIEnv* env, jobject jmodule) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteRegion));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSet(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* other = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->set(*other);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nIsEmpty(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nIsRect(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isRect();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nIsComplex(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isComplex();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Region_00024__1nGetBounds(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return types::IRect::fromSkIRect(env, instance->getBounds());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Region_00024__1nComputeRegionComplexity(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->computeRegionComplexity();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nGetBoundaryPath(JNIEnv* env, jobject jmodule, jlong ptr, jlong pathPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    return instance->getBoundaryPath(path);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSetEmpty(JNIEnv* env, jobject jmodule, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->setEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSetRect(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->setRect({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSetRects(JNIEnv* env, jobject jmodule, jlong ptr, jintArray coords) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    int len = env->GetArrayLength(coords);
    std::vector<SkIRect> rects(len / 4);
    jint* arr = env->GetIntArrayElements(coords, 0);
    for (int i = 0; i < len; i += 4)
        rects[i / 4] = {arr[i], arr[i+1], arr[i+2], arr[i+3]};
    env->ReleaseIntArrayElements(coords, arr, 0);
    return instance->setRects(rects.data(), len / 4);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSetRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->setRegion(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nSetPath(JNIEnv* env, jobject jmodule, jlong ptr, jlong pathPtr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->setPath(*path, *region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nIntersectsIRect(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->intersects({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nIntersectsRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->intersects(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nContainsIPoint(JNIEnv* env, jobject jmodule, jlong ptr, jint x, jint y) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->contains(x, y);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nContainsIRect(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->contains({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nContainsRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->contains(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nQuickContains(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->quickContains({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nQuickRejectIRect(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->quickReject({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nQuickRejectRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->contains(*region);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Region_00024__1nTranslate(JNIEnv* env, jobject jmodule, jlong ptr, jint dx, jint dy) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    instance->translate(dx, dy);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nOpIRect(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->op({left, top, right, bottom}, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nOpRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op(*region, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nOpIRectRegion(JNIEnv* env, jobject jmodule, jlong ptr, jint left, jint top, jint right, jint bottom, jlong regionPtr, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op({left, top, right, bottom}, *region, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nOpRegionIRect(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtr, jint left, jint top, jint right, jint bottom, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op(*region, {left, top, right, bottom}, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Region_00024__1nOpRegionRegion(JNIEnv* env, jobject jmodule, jlong ptr, jlong regionPtrA, jlong regionPtrB, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* regionA = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtrA));
    SkRegion* regionB = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtrB));
    return instance->op(*regionA, *regionB, static_cast<SkRegion::Op>(op));
}
