#include <iostream>
#include <jni.h>
#include "SkDrawable.h"
#include "SkPicture.h"
#include "interop.hh"

class SkijaDrawableImpl: public SkDrawable {
public:
    SkijaDrawableImpl() {
    }

    ~SkijaDrawableImpl() {
        JNIEnv* env;
        if (fJavaVM->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_8) == JNI_OK)
          env->DeleteWeakGlobalRef(fObject);
    }

    void init(JNIEnv* e, jobject o) {
        fEnv = e;
        fEnv->GetJavaVM(&fJavaVM);
        fObject = fEnv->NewWeakGlobalRef(o);
    }

protected:
    void onDraw(SkCanvas* canvas) override {
        fEnv->CallVoidMethod(fObject, skija::Drawable::onDraw, reinterpret_cast<jlong>(canvas));
        java::lang::Throwable::exceptionThrown(fEnv);
    }

    SkRect onGetBounds() override {
        skija::AutoLocal<jobject> rect(fEnv, fEnv->CallObjectMethod(fObject, skija::Drawable::onGetBounds));
        java::lang::Throwable::exceptionThrown(fEnv);
        return *(types::Rect::toSkRect(fEnv, rect.get()));
    }

private:
    JNIEnv* fEnv;
    JavaVM* fJavaVM;
    jobject fObject;
};

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nMake
  (JNIEnv* env, jobject) {
    SkijaDrawableImpl* instance = new SkijaDrawableImpl();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nInit
  (JNIEnv* env, jobject, jobject jthis, jlong ptr) {
    SkijaDrawableImpl* instance = reinterpret_cast<SkijaDrawableImpl*>(static_cast<uintptr_t>(ptr));
    instance->init(env, jthis);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nDraw
  (JNIEnv* env, jobject, jlong ptr, jlong canvasPtr, jfloatArray matrixArr) {
    SkijaDrawableImpl* instance = reinterpret_cast<SkijaDrawableImpl*>(static_cast<uintptr_t>(ptr));
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    instance->draw(canvas, matrix.get());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nMakePictureSnapshot
  (JNIEnv* env, jobject, jlong ptr) {
    SkijaDrawableImpl* instance = reinterpret_cast<SkijaDrawableImpl*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkPicture> pic = instance->makePictureSnapshot();
    return reinterpret_cast<jlong>(pic.release());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nGetGenerationId
  (JNIEnv* env, jobject, jlong ptr) {
    SkijaDrawableImpl* instance = reinterpret_cast<SkijaDrawableImpl*>(static_cast<uintptr_t>(ptr));
    return instance->getGenerationID();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Drawable_00024__1nNotifyDrawingChanged
  (JNIEnv* env, jobject, jlong ptr) {
    SkijaDrawableImpl* instance = reinterpret_cast<SkijaDrawableImpl*>(static_cast<uintptr_t>(ptr));
    return instance->notifyDrawingChanged();
}
