package com.htc.eleven.autotest;

/**
 * Created by eleven on 17-9-26.
 */

public class AudioConditionMixerControl extends Condition {

    public AudioConditionMixerControl(String id, int index, String description) {
        super(id, index, description);
    }

    @Override
    public boolean judge() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * call to native autotest_server to check mixer control.
         * */
        return App.getApp().getNativeAPI().checkMixerControlEnabled(getDescription());
    }
}
