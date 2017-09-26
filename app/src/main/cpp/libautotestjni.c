#include <jni.h>

JNIEXPORT jboolean JNICALL
Java_com_htc_eleven_autotest_NativeFunctionCall_switchPath(JNIEnv *env, jobject instance,
                                                           jlong device) {

    // TODO

}

JNIEXPORT jboolean JNICALL
Java_com_htc_eleven_autotest_NativeFunctionCall_isMixerControlEnabled(JNIEnv *env, jobject instance,
                                                                      jstring ctrlName_) {
    const char *ctrlName = (*env)->GetStringUTFChars(env, ctrlName_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, ctrlName_, ctrlName);
}

JNIEXPORT jboolean JNICALL
Java_com_htc_eleven_autotest_NativeFunctionCall_isPcmDeviceOpened(JNIEnv *env, jobject instance,
                                                                  jint pcmDevice) {

    // TODO

}