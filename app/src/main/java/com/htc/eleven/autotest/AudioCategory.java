package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-20.
 */

public class AudioCategory extends Category {

    private String file;

    public class AudioCondition extends Condition {

        public AudioCondition(String id, int index, String description) {
            super(id, index, description);
        }

        @Override
        public boolean judge() {

            //TODO, implement the real check here.
            return true;
        }
    }
    public class AudioCase extends Case {

        public AudioCase(int categoryId, String caseName){
            this.CategoryId = categoryId;
            this.mCaseName = caseName;

            mConditions = new Vector<>();
        }

        @Override
        public boolean run() {
            return check();
        }

        @Override
        public boolean check() {
            boolean ret = true;
            for(Condition c: mConditions) {
                ret = c.judge();
                if(!ret) {
                    err = c;
                    break;
                }
            }
            return ret;
        }
    }
    public AudioCategory(int id, String name, String file) {
        this.id = id;
        this.mCategoryName = name;
        this.file = file;
        mCases = new Vector<>();
    }

    public boolean Load() {
        initXml(file);
        App.getApp().registerCategory(this);
        return true;
    }

    public void insertCase(int categoryID, String name, String[] conditions) {
        AudioCase audioCase = new AudioCase(categoryID, name);
        for(int i=0; i<conditions.length; i++) {
            audioCase.mConditions.add(new AudioCondition(null, i, conditions[i]));
        }
        mCases.add(audioCase);
    }
    @Override
    public boolean initXml(String file) {
        XmlParser.XmlParser(file, this);
        return false;
    }
}
