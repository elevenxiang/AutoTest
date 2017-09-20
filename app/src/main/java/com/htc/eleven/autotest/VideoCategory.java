package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-20.
 */

public class VideoCategory extends Category {

    private String file;

    public VideoCategory(String name, String file) {
        this.mCategoryName = name;
        this.file = file;
        mCases = new Vector<>();
    }

    public boolean Load() {
        initCases(file);
        return true;
    }

    @Override
    public boolean initCases(String file) {
        XmlParser.XmlParser(file, this);
        return false;
    }
}
