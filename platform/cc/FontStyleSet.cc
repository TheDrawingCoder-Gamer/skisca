#include <iostream>
#include <jni.h>
#include "interop.hh"
#include "SkTypeface.h"
#include "SkFontMgr.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nMakeEmpty
  (JNIEnv* env, jobject jobject) {
    sk_sp<SkFontStyleSet> instance = SkFontStyleSet::CreateEmpty();
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nCount
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    return instance->count();
}

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nGetStyle
  (JNIEnv* env, jobject jobject, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkFontStyle fontStyle;
    instance->getStyle(index, &fontStyle, nullptr);
    return fontStyle.weight() + (fontStyle.width() << 16) + (fontStyle.slant() << 24);
}

extern "C" JNIEXPORT jstring JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nGetStyleName
  (JNIEnv* env, jobject jobject, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkString style;
    instance->getStyle(index, nullptr, &style);
    return javaString(env, style);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nGetTypeface
  (JNIEnv* env, jobject jobject, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkTypeface> typeface = instance->createTypeface(index);
    return reinterpret_cast<jlong>(typeface.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_FontStyleSet_00024__1nMatchStyle
  (JNIEnv* env, jobject jobject, jlong ptr, jint fontStyle) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkTypeface> typeface = instance->matchStyle(skija::FontStyle::fromJava(fontStyle));
    return reinterpret_cast<jlong>(typeface.release());
}