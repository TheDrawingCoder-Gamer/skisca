#include <jni.h>
#include "../interop.hh"
#include "SkResources.h"

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_resources_FileResourceProvider_00024__1nMake
  (JNIEnv* env, jobject, jstring basePathStr, jboolean predecode) {
    SkString basePath = skString(env, basePathStr);
    auto instance = skresources::FileResourceProvider::Make(basePath, predecode);
    return ptrToJlong(instance.release());
}