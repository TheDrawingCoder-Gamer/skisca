#include <jni.h>
#include "interop.hh"
#include "SkFont.h"
#include "SkShaper.h"
#include "SkLoadICU.h"

namespace skija {
    namespace shaper {
        namespace BidiRun {
            jfieldID _end;
            jfieldID _level;

            void onLoad(JNIEnv* env) {
                jclass cls = env->FindClass("gay/menkissing/skisca/shaper/BidiRun");
                _end = env->GetFieldID(cls, "end", "I");
                _level = env->GetFieldID(cls, "level", "I");
            }
        }

        namespace FontMgrRunIterator {
            jclass cls;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/FontMgrRunIterator");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }
        }

        namespace FontRun {
            jfieldID _end;
            jmethodID _getFontPtr;

            void onLoad(JNIEnv* env) {
                jclass cls = env->FindClass("gay/menkissing/skisca/shaper/FontRun");
                _end = env->GetFieldID(cls, "end", "I");
                _getFontPtr = env->GetMethodID(cls, "_getFontPtr", "()J");
            }
        }

        namespace HbIcuScriptRunIterator {
            jclass cls;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/HbIcuScriptRunIterator");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));            
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

        }

        namespace IcuBidiRunIterator {
            jclass cls;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/IcuBidiRunIterator");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));            
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

        }

        namespace LanguageRun {
            jfieldID _end;
            jfieldID _language;

            void onLoad(JNIEnv* env) {
                jclass cls = env->FindClass("gay/menkissing/skisca/shaper/LanguageRun");
                _end = env->GetFieldID(cls, "end", "I");
                _language = env->GetFieldID(cls, "language", "Ljava/lang/String;");
            }
        }

        namespace RunHandler {
            jclass cls;
            jmethodID beginLine;
            jmethodID runInfo;
            jmethodID commitRunInfo;
            jmethodID runOffset;
            jmethodID commitRun;
            jmethodID commitLine;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/RunHandler");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                beginLine       = env->GetMethodID(cls, "beginLine",     "()V");
                runInfo         = env->GetMethodID(cls, "runInfo",       "(Lgay/menkissing/skisca/shaper/RunInfo;)V");
                commitRunInfo   = env->GetMethodID(cls, "commitRunInfo", "()V");
                runOffset       = env->GetMethodID(cls, "runOffset",     "(Lgay/menkissing/skisca/shaper/RunInfo;)Lio/github/humbleui/types/Point;");
                commitRun       = env->GetMethodID(cls, "commitRun",     "(Lgay/menkissing/skisca/shaper/RunInfo;[S[Lio/github/humbleui/types/Point;[I)V");
                commitLine      = env->GetMethodID(cls, "commitLine",    "()V");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }
        }

        namespace RunInfo {
            jclass cls;
            jmethodID ctor;
            jfieldID _fontPtr;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/RunInfo");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
                ctor = env->GetMethodID(cls, "<init>", "(JIFFJII)V");
                _fontPtr = env->GetFieldID(cls, "fontPtr", "J");
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }

            jobject toJava(JNIEnv* env, const SkShaper::RunHandler::RunInfo& info, size_t begin, size_t end) {
                SkFont* font = new SkFont(info.fFont);
                return env->NewObject(
                    cls, 
                    ctor,
                    reinterpret_cast<jlong>(font),
                    info.fBidiLevel,
                    info.fAdvance.fX,
                    info.fAdvance.fY,
                    info.glyphCount,
                    begin,
                    end - begin);
            }

            jobject toJava(JNIEnv* env, const SkShaper::RunHandler::RunInfo& info, skija::UtfIndicesConverter& indicesConverter) {
                size_t begin = indicesConverter.from8To16(info.utf8Range.fBegin);
                size_t end = indicesConverter.from8To16(info.utf8Range.fBegin + info.utf8Range.fSize);
                return toJava(env, info, begin, end);
            }
        }

        namespace ScriptRun {
            jfieldID _end;
            jfieldID _scriptTag;

            void onLoad(JNIEnv* env) {
                jclass cls = env->FindClass("gay/menkissing/skisca/shaper/ScriptRun");
                _end = env->GetFieldID(cls, "end", "I");
                _scriptTag = env->GetFieldID(cls, "scriptTag", "I");
            }
        }

        namespace ShapingOptions {
            jfieldID _fontMgr;
            jmethodID featuresArray;
            jfieldID _leftToRight;
            jfieldID _approximateSpaces;
            jfieldID _approximatePunctuation;

            void onLoad(JNIEnv* env) {
                jclass cls = env->FindClass("gay/menkissing/skisca/shaper/ShapingOptions");
                _fontMgr = env->GetFieldID(cls, "fontMgr", "Lgay/menkissing/skisca/FontMgr;");
                featuresArray = env->GetMethodID(cls, "featuresArray", "()[Lgay/menkissing/skisca/FontFeature;");
                _leftToRight = env->GetFieldID(cls, "leftToRight", "Z");
                _approximateSpaces = env->GetFieldID(cls, "approximateSpaces", "Z");
                _approximatePunctuation = env->GetFieldID(cls, "approximatePunctuation", "Z");
            }

            std::vector<SkShaper::Feature> getFeatures(JNIEnv* env, jobject opts) {
                return skija::FontFeature::fromJavaArray(env, (jobjectArray) env->CallObjectMethod(opts, featuresArray));
            }
        }

        namespace TextBlobBuilderRunHandler {
            jclass cls;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/TextBlobBuilderRunHandler");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }
       }
       
        namespace TextLineRunHandler {
            jclass cls;

            void onLoad(JNIEnv* env) {
                jclass local = env->FindClass("gay/menkissing/skisca/shaper/TextLineRunHandler");
                cls  = static_cast<jclass>(env->NewGlobalRef(local));
            }

            void onUnload(JNIEnv* env) {
                env->DeleteGlobalRef(cls);
            }
       }

       void onLoad(JNIEnv* env) {
            BidiRun::onLoad(env);
            FontMgrRunIterator::onLoad(env);
            FontRun::onLoad(env);
            HbIcuScriptRunIterator::onLoad(env);
            IcuBidiRunIterator::onLoad(env);
            LanguageRun::onLoad(env);
            RunHandler::onLoad(env);
            RunInfo::onLoad(env);
            ScriptRun::onLoad(env);
            ShapingOptions::onLoad(env);
            TextBlobBuilderRunHandler::onLoad(env);
            TextLineRunHandler::onLoad(env);

            SkLoadICU();
        }

        void onUnload(JNIEnv* env) {
            TextLineRunHandler::onUnload(env);
            TextBlobBuilderRunHandler::onUnload(env);
            RunInfo::onUnload(env);
            RunHandler::onUnload(env);
            IcuBidiRunIterator::onUnload(env);
            HbIcuScriptRunIterator::onUnload(env);
            FontMgrRunIterator::onUnload(env);
        }

        std::shared_ptr<UBreakIterator> graphemeBreakIterator(SkString& text) {
            UErrorCode status = U_ZERO_ERROR;
            
            ICUUText utext(utext_openUTF8(nullptr, text.c_str(), text.size(), &status));
            if (U_FAILURE(status)) {
                SkDEBUGF("utext_openUTF8 error: %s", u_errorName(status));
                return nullptr;
            }

            std::shared_ptr<UBreakIterator> graphemeIter(
                ubrk_open(static_cast<UBreakIteratorType>(UBreakIteratorType::UBRK_CHARACTER), uloc_getDefault(), nullptr, 0, &status),
                [](UBreakIterator* p) { ubrk_close(p); }
            );
            if (U_FAILURE(status)) {
                SkDEBUGF("ubrk_open error: %s", u_errorName(status));
                return nullptr;
            }

            ubrk_setUText(graphemeIter.get(), utext.get(), &status);
            if (U_FAILURE(status)) {
                SkDEBUGF("ubrk_setUText error: %s", u_errorName(status));
                return nullptr;
            }

            return graphemeIter;    
        }
    }
}