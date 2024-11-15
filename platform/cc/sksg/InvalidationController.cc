#include <jni.h>
#include "../interop.hh"
#include "SkSGInvalidationController.h"

using namespace sksg;

static void deleteInvalidationController(InvalidationController* controller) {
    delete controller;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_sksg_InvalidationController_00024__1nGetFinalizer
  (JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteInvalidationController));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_sksg_InvalidationController_00024__1nMake
  (JNIEnv* env, jobject) {
    InvalidationController* instance = new InvalidationController();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_sksg_InvalidationController_00024__1nInvalidate
  (JNIEnv* env, jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloatArray matrixArr) {
    InvalidationController* instance = reinterpret_cast<InvalidationController*>(static_cast<uintptr_t>(ptr));
    SkRect bounds {left, top, right, bottom};
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    instance->inval(bounds, *matrix.get());
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_sksg_InvalidationController_00024__1nGetBounds
  (JNIEnv* env, jobject, jlong ptr) {
    InvalidationController* instance = reinterpret_cast<InvalidationController*>(static_cast<uintptr_t>(ptr));
    return types::Rect::fromSkRect(env, instance->bounds());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_sksg_InvalidationController_00024__1nReset
  (JNIEnv* env, jobject, jlong ptr) {
    InvalidationController* instance = reinterpret_cast<InvalidationController*>(static_cast<uintptr_t>(ptr));
    instance->reset();
}
