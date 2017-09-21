package com.htc.eleven.autotest;

import java.util.Vector;

/**
 * Created by eleven on 17-9-20.
 */

public class VideoCategory extends Category {

    private String file;

    public VideoCategory(int videoId, String name, String file) {
        this.id = videoId;
        this.mCategoryName = name;
        this.file = file;
        mCases = new Vector<>();
    }

    @Override
    public boolean initXml(String file) {
        XmlParser.XmlParser(file, this);
        return true;
    }

    @Override
    public void insertCase(int id, String name, String[] conditions) {

    }

    public boolean Load() {
        initXml(file);
        return true;
    }
}
