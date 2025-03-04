#include <jni.h>
#include "../interop.hh"
#include "../shaper/interop.hh"
#include "../skottie/interop.hh"
#include "../paragraph/interop.hh"
#include "../svg/interop.hh"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_8) != JNI_OK)
        return JNI_ERR;

    return JNI_VERSION_1_8;
}

extern "C" JNIEXPORT void JNICALL Java_gay_menkissing_skisca_impl_Library_00024__1nAfterLoad
  (JNIEnv* env, jobject jmodule) {
    env->EnsureLocalCapacity(64);
    java::onLoad(env);
    types::onLoad(env);
    java::lang::Throwable::exceptionThrown(env);
    skija::onLoad(env);
    skija::shaper::onLoad(env);
    skija::skottie::onLoad(env);
    skija::paragraph::onLoad(env);
    skija::svg::onLoad(env);
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_8) != JNI_OK)
        return;

    skija::svg::onUnload(env);
    skija::paragraph::onUnload(env);
    skija::skottie::onUnload(env);
    skija::shaper::onUnload(env);
    skija::onUnload(env);
    types::onUnload(env);
    java::onUnload(env);
}
