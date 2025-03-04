#include <iostream>
#include <jni.h>
#include "SkCanvas.h"
#include "SkRRect.h"
#include "SkSurface.h"
#include "SkTextBlob.h"
#include "SkVertices.h"
#include "hb.h"
#include "interop.hh"

static void deleteCanvas(SkCanvas* canvas) {
    // std::cout << "Deleting [SkCanvas " << canvas << "]" << std::endl;
    delete canvas;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetFinalizer(JNIEnv* env, jobject obj) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteCanvas));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nMakeFromBitmap
  (JNIEnv* env, jobject obj, jlong bitmapPtr, jint flags, jint pixelGeometry) {
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    SkCanvas* canvas = new SkCanvas(*bitmap, {static_cast<uint32_t>(flags), static_cast<SkPixelGeometry>(pixelGeometry)});
    return reinterpret_cast<jlong>(canvas);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetBaseProps
  (JNIEnv* env, jobject obj, jlong canvasPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkSurfaceProps props = canvas->getBaseProps();
    return skija::SurfaceProps::toJava(env, props);
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetTopProps
  (JNIEnv* env, jobject obj, jlong canvasPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkSurfaceProps props = canvas->getTopProps();
    return skija::SurfaceProps::toJava(env, props);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetSurface
  (JNIEnv* env, jobject obj, jlong canvasPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkSurface* surface = canvas->getSurface();
    surface->ref();
    return reinterpret_cast<jlong>(surface);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPoint
  (JNIEnv* env, jobject obj, jlong canvasPtr, jfloat x, jfloat y, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawPoint(x, y, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPoints
  (JNIEnv* env, jobject obj, jlong canvasPtr, int mode, jfloatArray coords, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    SkCanvas::PointMode skMode = static_cast<SkCanvas::PointMode>(mode);
    jsize len = env->GetArrayLength(coords);
    jfloat* arr = static_cast<jfloat*>(env->GetPrimitiveArrayCritical(coords, 0));
    canvas->drawPoints(skMode, len / 2, reinterpret_cast<SkPoint*>(arr), *paint);
    env->ReleasePrimitiveArrayCritical(coords, arr, 0);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawLine
  (JNIEnv* env, jobject obj, jlong canvasPtr, jfloat x0, jfloat y0, jfloat x1, jfloat y1, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawLine(x0, y0, x1, y1, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawArc
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloat startAngle, jfloat sweepAngle, jboolean includeCenter, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawArc({left, top, right, bottom}, startAngle, sweepAngle, includeCenter, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawRect({left, top, right, bottom}, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawOval
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawOval({left, top, right, bottom}, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawRRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloatArray jradii, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawRRect(types::RRect::toSkRRect(env, left, top, right, bottom, jradii), *paint);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nQuickReject
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    return canvas->quickReject({left, top, right, bottom});
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nQuickRejectPath
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong pathPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    return canvas->quickReject(*path);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawDRRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr,
   jfloat ol, jfloat ot, jfloat oright, jfloat ob, jfloatArray ojradii,
   jfloat il, jfloat it, jfloat ir, jfloat ib, jfloatArray ijradii,
   jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawDRRect(types::RRect::toSkRRect(env, ol, ot, oright, ob, ojradii),
        types::RRect::toSkRRect(env, il, it, ir, ib, ijradii), *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPath
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong pathPtr, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawPath(*path, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawImageRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong imagePtr, jfloat sl, jfloat st, jfloat sr, jfloat sb, jfloat dl, jfloat dt, jfloat dr, jfloat db, jlong samplingMode, jlong paintPtr, jboolean strict) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkRect src {sl, st, sr, sb};
    SkRect dst {dl, dt, dr, db};
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    SkCanvas::SrcRectConstraint constraint = strict ? SkCanvas::SrcRectConstraint::kStrict_SrcRectConstraint : SkCanvas::SrcRectConstraint::kFast_SrcRectConstraint;
    canvas->drawImageRect(image, src, dst, skija::SamplingMode::unpack(samplingMode), paint, constraint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawImageNine
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong imagePtr, jint cl, jint ct, jint cr, jint cb, jfloat dl, jfloat dt, jfloat dr, jfloat db, jint filterMode, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkImage* image = reinterpret_cast<SkImage*>(static_cast<uintptr_t>(imagePtr));
    SkIRect center {cl, ct, cr, cb};
    SkRect dst {dl, dt, dr, db};
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawImageNine(image, center, dst, static_cast<SkFilterMode>(filterMode), paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawRegion
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong regionPtr, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawRegion(*region, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawString
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jstring stringObj, jfloat x, jfloat y, jlong skFontPtr, jlong paintPtr) {
    SkCanvas* canvas    = reinterpret_cast<SkCanvas*>   (static_cast<uintptr_t>(canvasPtr));
    SkString string     = skString(env, stringObj);
    SkFont* font        = reinterpret_cast<SkFont*>     (static_cast<uintptr_t>(skFontPtr));
    SkPaint* paint      = reinterpret_cast<SkPaint*>    (static_cast<uintptr_t>(paintPtr));

    canvas->drawString(string, x, y, *font, *paint);
}


extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawTextBlob
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong blobPtr, jfloat x, jfloat y, jlong paintPtr) {
    SkCanvas* canvas    = reinterpret_cast<SkCanvas*>   (static_cast<uintptr_t>(canvasPtr));
    SkTextBlob* blob    = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(blobPtr));
    SkPaint* paint      = reinterpret_cast<SkPaint*>    (static_cast<uintptr_t>(paintPtr));

    canvas->drawTextBlob(blob, x, y, *paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPicture
  (JNIEnv* env, jobject jobject, jlong ptr, jlong picturePtr, jfloatArray matrixArr, jlong paintPtr) {
    SkCanvas* canvas   = reinterpret_cast<SkCanvas*>   (static_cast<uintptr_t>(ptr));
    SkPicture* picture = reinterpret_cast<SkPicture*>(static_cast<uintptr_t>(picturePtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    SkPaint* paint     = reinterpret_cast<SkPaint*>    (static_cast<uintptr_t>(paintPtr));
    canvas->drawPicture(picture, matrix.get(), paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawVertices
  (JNIEnv* env, jobject jobject, jlong ptr, jint verticesMode, jfloatArray positionsArr, jintArray colorsArr, jfloatArray texCoordsArr, jshortArray indexArr, jint blendMode, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>   (static_cast<uintptr_t>(ptr));
    int indexCount = indexArr == nullptr ? 0 : env->GetArrayLength(indexArr);
    jfloat* positions = env->GetFloatArrayElements(positionsArr, 0);
    jint*   colors    = colorsArr == nullptr ? nullptr : env->GetIntArrayElements(colorsArr, 0);
    jfloat* texCoords = texCoordsArr == nullptr ? nullptr : env->GetFloatArrayElements(texCoordsArr, 0);
    const jshort* indices = indexArr == nullptr ? nullptr : env->GetShortArrayElements(indexArr, 0);
    sk_sp<SkVertices> vertices = SkVertices::MakeCopy(
        static_cast<SkVertices::VertexMode>(verticesMode),
        env->GetArrayLength(positionsArr) / 2,
        reinterpret_cast<SkPoint*>(positions),
        reinterpret_cast<SkPoint*>(texCoords), 
        reinterpret_cast<SkColor*>(colors),
        indexCount,
        reinterpret_cast<const uint16_t *>(indices));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));

    canvas->drawVertices(vertices, static_cast<SkBlendMode>(blendMode), *paint);
    
    if (texCoords != nullptr)
        env->ReleaseFloatArrayElements(texCoordsArr, texCoords, 0);
    if (colors != nullptr)
        env->ReleaseIntArrayElements(colorsArr, colors, 0);
    env->ReleaseFloatArrayElements(positionsArr, positions, 0);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPatch
  (JNIEnv* env, jobject jobject, jlong ptr, jfloatArray cubicsArr, jintArray colorsArr, jfloatArray texCoordsArr, jint blendMode, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>   (static_cast<uintptr_t>(ptr));
    jfloat* cubics    = env->GetFloatArrayElements(cubicsArr, 0);
    jint*   colors    = env->GetIntArrayElements(colorsArr, 0);
    jfloat* texCoords = texCoordsArr == nullptr ? nullptr : env->GetFloatArrayElements(texCoordsArr, 0);
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));

    canvas->drawPatch(reinterpret_cast<SkPoint*>(cubics), reinterpret_cast<SkColor*>(colors), reinterpret_cast<SkPoint*>(texCoords), static_cast<SkBlendMode>(blendMode), *paint);
    
    if (texCoords != nullptr)
        env->ReleaseFloatArrayElements(texCoordsArr, texCoords, 0);
    env->ReleaseIntArrayElements(colorsArr, colors, 0);
    env->ReleaseFloatArrayElements(cubicsArr, cubics, 0);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawDrawable
  (JNIEnv* env, jobject jobject, jlong ptr, jlong drawablePtr, jfloatArray matrixArr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkDrawable* drawable = reinterpret_cast<SkDrawable*>(static_cast<uintptr_t>(drawablePtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    canvas->drawDrawable(drawable, matrix.get());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawColor(JNIEnv* env, jobject jobject, jlong ptr, jint color, jint blendMode) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    canvas->drawColor(color, static_cast<SkBlendMode>(blendMode));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawColor4f(JNIEnv* env, jobject jobject, jlong ptr, jfloat r, jfloat g, jfloat b, jfloat a, jint blendMode) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkColor4f color { r, g, b, a };
    canvas->drawColor(color, static_cast<SkBlendMode>(blendMode));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nClear(JNIEnv* env, jobject jobject, jlong ptr, jint color) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    canvas->clear(color);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nDrawPaint
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    canvas->drawPaint(*paint);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSetMatrix
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloatArray matrixArr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    std::unique_ptr<SkMatrix> matrix = skMatrix(env, matrixArr);
    canvas->setMatrix(*matrix);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSetMatrix44
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloatArray matrixArr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    std::unique_ptr<SkM44> matrix = skM44(env, matrixArr);
    canvas->setMatrix(*matrix);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nResetMatrix
  (JNIEnv* env, jobject jobject, jlong canvasPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    canvas->resetMatrix();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetLocalToDevice
(JNIEnv* env, jobject jobject, jlong canvasPtr) {
  SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
  SkM44 matrix = canvas->getLocalToDevice();
  std::vector<float> floats(16);
  matrix.getRowMajor(floats.data());
  return javaFloatArray(env, floats);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nClipRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jint mode, jboolean antiAlias) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    canvas->clipRect({left, top, right, bottom}, static_cast<SkClipOp>(mode), antiAlias);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nClipRRect
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jfloat left, jfloat top, jfloat right, jfloat bottom, jfloatArray jradii, jint mode, jboolean antiAlias) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    canvas->clipRRect(types::RRect::toSkRRect(env, left, top, right, bottom, jradii), static_cast<SkClipOp>(mode), antiAlias);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nClipPath
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong pathPtr, jint mode, jboolean antiAlias) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    canvas->clipPath(*path, static_cast<SkClipOp>(mode), antiAlias);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nClipRegion
  (JNIEnv* env, jobject jobject, jlong canvasPtr, jlong regionPtr, jint mode) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    canvas->clipRegion(*region, static_cast<SkClipOp>(mode));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nConcat
  (JNIEnv* env, jobject jobject, jlong ptr, jfloatArray matrixArr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkMatrix> m = skMatrix(env, matrixArr);
    canvas->concat(*m);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nConcat44
  (JNIEnv* env, jobject jobject, jlong ptr, jfloatArray matrixArr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    std::unique_ptr<SkM44> m = skM44(env, matrixArr);
    canvas->concat(*m);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nReadPixels
  (JNIEnv* env, jobject jobject, jlong ptr, jlong bitmapPtr, jint srcX, jint srcY) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    return canvas->readPixels(*bitmap, srcX, srcY);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nWritePixels
  (JNIEnv* env, jobject jobject, jlong ptr, jlong bitmapPtr, jint x, jint y) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkBitmap* bitmap = reinterpret_cast<SkBitmap*>(static_cast<uintptr_t>(bitmapPtr));
    return canvas->writePixels(*bitmap, x, y);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSave(JNIEnv* env, jobject jobject, jlong ptr) {
    return reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr))->save();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayer
  (JNIEnv* env, jobject jobject, jlong ptr, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    return canvas->saveLayer(nullptr, paint);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayerRect
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jlong paintPtr) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkRect bounds {left, top, right, bottom};
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    return canvas->saveLayer(&bounds, paint);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayerAlpha
  (JNIEnv* env, jobject jobject, jlong ptr, jint alpha) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    return canvas->saveLayerAlpha(nullptr, alpha);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayerAlphaRect
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jint alpha) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkRect bounds {left, top, right, bottom};
    return canvas->saveLayerAlpha(&bounds, alpha);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayerRec
  (JNIEnv* env, jobject jobject, jlong ptr, jlong paintPtr, jlong backdropPtr, jint flags) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    SkImageFilter* backdrop = reinterpret_cast<SkImageFilter*>(static_cast<uintptr_t>(backdropPtr));
    SkCanvas::SaveLayerRec layerRec(nullptr, paint, backdrop, flags);
    return canvas->saveLayer(layerRec);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nSaveLayerRecRect
  (JNIEnv* env, jobject jobject, jlong ptr, jfloat left, jfloat top, jfloat right, jfloat bottom, jlong paintPtr, jlong backdropPtr, jint flags) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr));
    SkRect bounds {left, top, right, bottom};
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    SkImageFilter* backdrop = reinterpret_cast<SkImageFilter*>(static_cast<uintptr_t>(backdropPtr));
    SkCanvas::SaveLayerRec layerRec(&bounds, paint, backdrop, flags);
    return canvas->saveLayer(layerRec);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nGetSaveCount(JNIEnv* env, jobject jobject, jlong ptr) {
    return reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr))->getSaveCount();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nRestore(JNIEnv* env, jobject jobject, jlong ptr) {
    reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr))->restore();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Canvas_00024__1nRestoreToCount(JNIEnv* env, jobject jobject, jlong ptr, jint saveCount) {
    reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(ptr))->restoreToCount(saveCount);
}