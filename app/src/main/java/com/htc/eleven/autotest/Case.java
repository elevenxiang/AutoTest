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

    public String getCaseName() {
        return mCaseName;
    }

    public Condition getErr() {
        return err;
    }

    public abstract boolean run();

    public boolean check() {
        boolean ret = true;
        for(Condition c: mConditions) {
            ret = c.judge();
            if(!ret) {
                err = c;
                break;
            }
        }
        result = ret ? "Passed" : "Failed";
        return ret;
    }

    public abstract boolean initConditions(String[] conditions);
}
