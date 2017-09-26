package com.htc.eleven.autotest;

/**
 * Created by eleven on 17-9-25.
 */

public class NativeFunctionCall {

    /**
     *  Used to load the 'native-lib' library on application startup.
     */

    static {
        System.loadLibrary("autotestjni");
    }

    public NativeFunctionCall(){

    }

    public boolean swithAudioPath(long deviceId) {
        return switchPath(deviceId);
    }


    public boolean checkMixerControlEnabled(String ctrlName) {
        return isMixerControlEnabled(ctrlName);
    }


    public boolean checkPcmDeviceOpened(int pcmDevice) {
        return isPcmDeviceOpened(pcmDevice);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    private native boolean switchPath(long device );
    private native boolean isMixerControlEnabled(String ctrlName);
    private native boolean isPcmDeviceOpened(int pcmDevice);

}
