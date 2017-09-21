package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public abstract class Case {
    public String mCaseName;
    public Vector<Condition> mConditions;
    public Condition err = null;

    public int CategoryId;

    public int getCategoryId() {
        return CategoryId;
    }

    /**
     * result = Passed or Failed.
     * */
    public String result;

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
