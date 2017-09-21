package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public abstract class Category {

    public String mCategoryName = null;
    public int id = 0;
    public Vector<Case> mCases = null;

    /**
     * each function need implement initialize function to load its test cases
     *
     * argument file, for example, Audio_Test_Cases.xml.
     * */
    public abstract boolean initXml(String file);
    public abstract void insertCase(int id, String name, String[] conditions);
    public abstract boolean Load();

    public String getCategoryName() {
        return mCategoryName;
    }

    public Vector<Case> getCases() {
        return mCases;
    }

    public int getId() {
        return id;
    }
}
