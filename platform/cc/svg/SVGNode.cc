#include <jni.h>
#include "../interop.hh"
#include "SkSVGNode.h"

extern "C" JNIEXPORT jint JNICALL Java_gay_menkissing_skisca_svg_SVGNode_00024__1nGetTag
  (JNIEnv* env, jobject, jlong ptr) {
    SkSVGNode* instance = reinterpret_cast<SkSVGNode*>(static_cast<uintptr_t>(ptr));
    return static_cast<jint>(instance->tag());
}
