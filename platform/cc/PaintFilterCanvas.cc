#include <iostream>
#include <jni.h>
#include "SkCanvas.h"
#include "SkDrawable.h"
#include "SkPaintFilterCanvas.h"
#include "interop.hh"

class SkijaPaintFilterCanvas : public SkPaintFilterCanvas {
public:
    SkijaPaintFilterCanvas(
        SkCanvas* canvas,
        bool unrollDrawable
    ) : SkPaintFilterCanvas(canvas), unrollDrawable(unrollDrawable) {}

    virtual ~SkijaPaintFilterCanvas() {
        skija::PaintFilterCanvas::detach(jobj);
    }

    jobject jobj;

protected:
    bool onFilter(SkPaint& paint) const override {
        return skija::PaintFilterCanvas::onFilter(jobj, paint);
    }

    void onDrawDrawable(SkDrawable* drawable, const SkMatrix* matrix) override {
        if (unrollDrawable) {
            drawable->draw(this, matrix);
        } else {
            SkPaintFilterCanvas::onDrawDrawable(drawable, matrix);
        }
    }

private:
    float unrollDrawable;
};

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_PaintFilterCanvas_00024__1nAttachToJava
  (JNIEnv* env, jobject, jobject jobj, jlong canvasPtr, jboolean unrollDrawable) {
    SkijaPaintFilterCanvas* canvas = reinterpret_cast<SkijaPaintFilterCanvas*>(static_cast<uintptr_t>(canvasPtr));
    canvas->jobj = skija::PaintFilterCanvas::attach(env, jobj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_PaintFilterCanvas_00024__1nMake
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jboolean unrollDrawable) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkijaPaintFilterCanvas* filterCanvas = new SkijaPaintFilterCanvas(canvas, unrollDrawable);
    return reinterpret_cast<jlong>(filterCanvas);
}