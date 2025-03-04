#include <jni.h>
#include "../interop.hh"
#include "SkStream.h"
#include "Skottie.h"
#include "SkFontMgr.h"
#include "SkResources.h"
#include "src/utils/SkOSPath.h"

using namespace skottie;

static void deleteAnimationBuilder(Animation::Builder* builder) {
    delete builder;
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nGetFinalizer
  (JNIEnv* env, jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteAnimationBuilder));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nMake
  (JNIEnv* env, jobject, jint flags) {
    return reinterpret_cast<jlong>(new Animation::Builder(flags));
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nSetFontManager
  (JNIEnv* env, jobject, jlong ptr, jlong fontMgrPtr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    sk_sp<SkFontMgr> fontMgr = sk_ref_sp(reinterpret_cast<SkFontMgr*>(static_cast<uintptr_t>(fontMgrPtr)));
    instance->setFontManager(fontMgr);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nSetLogger
  (JNIEnv* env, jobject, jlong ptr, jlong loggerPtr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    sk_sp<skottie::Logger> logger = sk_ref_sp(reinterpret_cast<skottie::Logger*>(static_cast<uintptr_t>(loggerPtr)));
    instance->setLogger(logger);
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nSetResourceProvider
  (JNIEnv* env, jobject, jlong ptr, jlong resourceProviderPtr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    sk_sp<ResourceProvider> resourceProvider = sk_ref_sp(reinterpret_cast<ResourceProvider*>(static_cast<uintptr_t>(resourceProviderPtr)));
    instance->setResourceProvider(resourceProvider);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nBuildFromString
  (JNIEnv* env, jobject, jlong ptr, jstring dataStr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    SkString data = skString(env, dataStr);
    sk_sp<Animation> animation = instance->make(data.c_str(), data.size());
    return reinterpret_cast<jlong>(animation.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nBuildFromFile
  (JNIEnv* env, jobject, jlong ptr, jstring pathStr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    SkString path = skString(env, pathStr);
    sk_sp<Animation> animation = instance->makeFromFile(path.c_str());
    return reinterpret_cast<jlong>(animation.release());
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_skottie_AnimationBuilder_00024__1nBuildFromData
  (JNIEnv* env, jobject, jlong ptr, jlong dataPtr) {
    Animation::Builder* instance = reinterpret_cast<Animation::Builder*>(static_cast<uintptr_t>(ptr));
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    SkMemoryStream stream(sk_ref_sp(data));
    sk_sp<Animation> animation = instance->make(&stream);
    return reinterpret_cast<jlong>(animation.release());
}
