#include <iostream>
#include <jni.h>
#include "SkFont.h"
#include "SkPath.h"
#include "SkShaper.h"
#include "interop.hh"

static void deleteFont(SkFont* font) {
    // std::cout << "Deleting [Font " << font << "]" << std::endl;
    delete font;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetFinalizer
  (JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteFont));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nMakeDefault
  (JNIEnv* env, jobject) {
    SkFont* obj = new SkFont();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nMakeTypeface
  (JNIEnv* env, jobject, jlong typefacePtr) {
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    SkFont* obj = new SkFont(sk_ref_sp<SkTypeface>(typeface));
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nMakeTypefaceSize
  (JNIEnv* env, jobject, jlong typefacePtr, jfloat size) {
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    SkFont* obj = new SkFont(sk_ref_sp<SkTypeface>(typeface), size);
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nMakeTypefaceSizeScaleSkew
  (JNIEnv* env, jobject, jlong typefacePtr, jfloat size, jfloat scaleX, jfloat skewX) {
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    SkFont* obj = new SkFont(sk_ref_sp<SkTypeface>(typeface), size, scaleX, skewX);
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nMakeClone
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkFont* clone = new SkFont(*instance);
    return reinterpret_cast<jlong>(clone);
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nEquals
  (JNIEnv* env, jobject, jlong ptr, jlong otherPtr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkFont* other = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(otherPtr));
    return *instance == *other;
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nIsAutoHintingForced
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isForceAutoHinting();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nAreBitmapsEmbedded
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isEmbeddedBitmaps();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nIsSubpixel
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isSubpixel();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nAreMetricsLinear
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isLinearMetrics();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nIsEmboldened
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isEmbolden();
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Font_00024__1nIsBaselineSnapped
  (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->isBaselineSnap();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetAutoHintingForced
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setForceAutoHinting(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetBitmapsEmbedded
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setEmbeddedBitmaps(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetSubpixel
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setSubpixel(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetMetricsLinear
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setLinearMetrics(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetEmboldened
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setEmbolden(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetBaselineSnapped
  (JNIEnv* env, jobject, jlong ptr, jboolean value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setBaselineSnap(value);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetEdging
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getEdging());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetEdging
 (JNIEnv* env, jobject, jlong ptr, jint value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setEdging(static_cast<SkFont::Edging>(value));
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetHinting
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->getHinting());
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetHinting
 (JNIEnv* env, jobject, jlong ptr, jint value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setHinting(static_cast<SkFontHinting>(value));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetTypeface
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = instance->refTypeface().release();
    return reinterpret_cast<jlong>(typeface);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetTypefaceOrDefault
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = instance->refTypefaceOrDefault().release();
    return reinterpret_cast<jlong>(typeface);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetSize
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->getSize();
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetScaleX
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->getScaleX();
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetSkewX
 (JNIEnv* env, jobject, jlong ptr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->getSkewX();
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetTypeface
 (JNIEnv* env, jobject, jlong ptr, jlong typefacePtr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = reinterpret_cast<SkTypeface*>(static_cast<uintptr_t>(typefacePtr));
    instance->setTypeface(sk_ref_sp(typeface));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetSize
 (JNIEnv* env, jobject, jlong ptr, jfloat value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setSize(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetScaleX
 (JNIEnv* env, jobject, jlong ptr, jfloat value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setScaleX(value);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_Font_00024__1nSetSkewX
 (JNIEnv* env, jobject, jlong ptr, jfloat value) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    instance->setSkewX(value);
}

extern "C" JNIEXPORT jshortArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetStringGlyphs
  (JNIEnv* env, jobject, jlong ptr, jstring str) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    jsize len = env->GetStringLength(str);
    const jchar* chars = env->GetStringCritical(str, nullptr);
    int count = instance->textToGlyphs(chars, len * sizeof(jchar), SkTextEncoding::kUTF16, nullptr, 0);
    std::vector<short> glyphs(count);
    instance->textToGlyphs(chars, len * sizeof(jchar), SkTextEncoding::kUTF16, reinterpret_cast<SkGlyphID*>(glyphs.data()), count);
    env->ReleaseStringCritical(str, chars);
    return javaShortArray(env, glyphs);
}

extern "C" JNIEXPORT jshortArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetUTF32Glyphs
  (JNIEnv* env, jobject, jlong ptr, jintArray uniArr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    int count = env->GetArrayLength(uniArr);
    std::vector<jshort> glyphs(count);
    jint* uni = env->GetIntArrayElements(uniArr, nullptr);
    instance->unicharsToGlyphs(reinterpret_cast<SkUnichar*>(uni), count, reinterpret_cast<SkGlyphID*>(glyphs.data()));
    env->ReleaseIntArrayElements(uniArr, uni, 0);
    return javaShortArray(env, glyphs);
}

extern "C" JNIEXPORT jshort JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetUTF32Glyph
  (JNIEnv* env, jobject, jlong ptr, jint uni) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->unicharToGlyph(uni);
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetStringGlyphsCount
  (JNIEnv* env, jobject, jlong ptr, jstring str) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    jsize len = env->GetStringLength(str);
    const jchar* chars = env->GetStringCritical(str, nullptr);
    int count = instance->countText(chars, len * sizeof(jchar), SkTextEncoding::kUTF16);
    env->ReleaseStringCritical(str, chars);
    return count;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Font_00024__1nMeasureText
  (JNIEnv* env, jobject, jlong ptr, jstring str, jlong paintPtr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    jsize len = env->GetStringLength(str);
    const jchar* chars = env->GetStringCritical(str, nullptr);
    SkRect bounds;
    instance->measureText(chars, len * sizeof(jchar), SkTextEncoding::kUTF16, &bounds, paint);
    env->ReleaseStringCritical(str, chars);
    return types::Rect::fromSkRect(env, bounds);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Font_00024__1nMeasureTextWidth
  (JNIEnv* env, jobject, jlong ptr, jstring str, jlong paintPtr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    jsize len = env->GetStringLength(str);
    const jchar* chars = env->GetStringCritical(str, nullptr);
    float width = instance->measureText(chars, len * sizeof(jchar), SkTextEncoding::kUTF16, nullptr, paint);
    env->ReleaseStringCritical(str, chars);
    return width;
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetWidths
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    int count = env->GetArrayLength(glyphsArr);
    std::vector<jfloat> widths(count);
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    instance->getWidths(reinterpret_cast<SkGlyphID*>(glyphs), count, widths.data());
    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    return javaFloatArray(env, widths);
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetBounds
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr, jlong paintPtr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    int count = env->GetArrayLength(glyphsArr);
    std::vector<SkRect> bounds(count);
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    instance->getBounds(reinterpret_cast<SkGlyphID*>(glyphs), count, bounds.data(), paint);
    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);

    jobjectArray res = env->NewObjectArray(count, types::Rect::cls, nullptr);
    for (int i = 0; i < count; ++i) {
        skija::AutoLocal<jobject> boundsObj(env, types::Rect::fromSkRect(env, bounds[i]));
        env->SetObjectArrayElement(res, i, boundsObj.get());
    }

    return res;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetPositions
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr, jfloat dx, jfloat dy) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    
    int count = env->GetArrayLength(glyphsArr);
    std::vector<SkPoint> positions(count);
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    instance->getPos(reinterpret_cast<SkGlyphID*>(glyphs), count, positions.data(), {dx, dy});
    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);

    return types::Point::fromSkPoints(env, positions);
}

extern "C" JNIEXPORT jfloatArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetXPositions
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr, jfloat dx) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    int count = env->GetArrayLength(glyphsArr);
    std::vector<jfloat> positions(count);
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    instance->getXPos(reinterpret_cast<SkGlyphID*>(glyphs), count, positions.data(), dx);
    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    return javaFloatArray(env, positions);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetPath
  (JNIEnv* env, jobject, jlong ptr, jshort glyph) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkPath* path = new SkPath();
    instance->getPath(glyph, path);
    return reinterpret_cast<jlong>(path);
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetPaths
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    
    int count = env->GetArrayLength(glyphsArr);
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);

    struct Ctx {
        jobjectArray paths;
        jsize        idx;
        JNIEnv*      env;
    } ctx = { env->NewObjectArray(count, skija::Path::cls, nullptr), 0, env };

    instance->getPaths(reinterpret_cast<SkGlyphID*>(glyphs), count, [](const SkPath* orig, const SkMatrix& mx, void* voidCtx) {
        Ctx* ctx = static_cast<Ctx*>(voidCtx);
        if (orig) {
            SkPath* path = new SkPath();
            orig->transform(mx, path);
            jobject pathObj = ctx->env->NewObject(skija::Path::cls, skija::Path::ctor, reinterpret_cast<jlong>(path));
            ctx->env->SetObjectArrayElement(ctx->paths, ctx->idx, pathObj);
            ctx->env->DeleteLocalRef(pathObj);
            ++ctx->idx;
        }
    }, &ctx);

    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    return ctx.paths;
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetMetrics
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    SkFontMetrics m;
    instance->getMetrics(&m);
    return skija::FontMetrics::toJava(env, m);
}

extern "C" JNIEXPORT jfloat JNICALL Java_gay_menkissing_skisca_Font_00024__1nGetSpacing
  (JNIEnv* env, jobject, jlong ptr, jshortArray glyphsArr) {
    SkFont* instance = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(ptr));
    return instance->getSpacing();
}