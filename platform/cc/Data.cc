#include <jni.h>
#include "interop.hh"
#include "SkData.h"

static void deleteData(SkData* data) {
    // std::cout << "Deleting [SkData " << data << "]" << std::endl;
    data->unref();
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nGetFinalizer(JNIEnv* env, jobject jobject) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteData));
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nSize
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkData* instance = reinterpret_cast<SkData*>(static_cast<uintptr_t>(ptr));
    return instance->size();
}

extern "C" JNIEXPORT jobject JNICALL Java_gay_menkissing_skisca_Data_00024__1nToByteBuffer
  (JNIEnv* env, jobject jobject, jlong ptr) {
    SkData* instance = reinterpret_cast<SkData*>(static_cast<uintptr_t>(ptr));
    return env->NewDirectByteBuffer(instance->writable_data(), instance->size());
}

extern "C" JNIEXPORT jbyteArray JNICALL Java_gay_menkissing_skisca_Data_00024__1nBytes
  (JNIEnv* env, jobject jobject, jlong ptr, jlong offset, jlong length) {
    SkData* instance = reinterpret_cast<SkData*>(static_cast<uintptr_t>(ptr));
    jbyteArray bytesArray = env->NewByteArray((jsize) length);
    const jbyte* bytes = reinterpret_cast<const jbyte*>(instance->bytes() + offset);
    env->SetByteArrayRegion(bytesArray, 0, (jsize) length, bytes);
    return bytesArray;
}

extern "C" JNIEXPORT jboolean JNICALL Java_gay_menkissing_skisca_Data_00024__1nEquals
  (JNIEnv* env, jobject jobject, jlong ptr, jlong otherPtr) {
    SkData* instance = reinterpret_cast<SkData*>(static_cast<uintptr_t>(ptr));
    SkData* other = reinterpret_cast<SkData*>(static_cast<uintptr_t>(otherPtr));
    return instance->equals(other);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nMakeFromBytes
  (JNIEnv* env, jobject jobject, jbyteArray bytesArray, jlong offset, jlong length) {
    jbyte* bytes = reinterpret_cast<jbyte*>(malloc(length));
    if (!bytes) return 0;
    env->GetByteArrayRegion(bytesArray, (jsize) offset, (jsize) length, bytes);
    SkData* instance = SkData::MakeFromMalloc(bytes, length).release();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nMakeFromFileName
  (JNIEnv* env, jobject jobject, jstring pathStr) {
    SkString path = skString(env, pathStr);
    SkData* instance = SkData::MakeFromFileName(path.c_str()).release();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nMakeSubset
  (JNIEnv* env, jobject jobject, jlong ptr, jlong offset, jlong length) {
    SkData* instance = reinterpret_cast<SkData*>(static_cast<uintptr_t>(ptr));
    SkData* subset = SkData::MakeSubset(instance, offset, length).release();
    return reinterpret_cast<jlong>(subset);
}

extern "C" JNIEXPORT jlong JNICALL Java_gay_menkissing_skisca_Data_00024__1nMakeEmpty
  (JNIEnv* env, jobject jobject) {
    SkData* instance = SkData::MakeEmpty().release();
    return reinterpret_cast<jlong>(instance);
}
