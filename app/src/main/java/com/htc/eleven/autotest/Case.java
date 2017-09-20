package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public abstract class Case {
    private String mCaseName;
    private Vector<Condition> mConditions;
    private Condition err = null;

    /**
     * result = Passed or Failed.
     * */
    private String result;

    public String getResult() {
        return result;
    }

    public String getmCaseName() {
        return mCaseName;
    }

    public Condition getErr() {
        return err;
    }

    public abstract boolean run();

    public abstract boolean check();
}
