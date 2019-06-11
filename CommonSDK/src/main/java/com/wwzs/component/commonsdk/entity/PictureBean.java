package com.wwzs.component.commonsdk.entity;

import com.google.gson.annotations.SerializedName;
import com.wwzs.component.commonsdk.base.BaseEntity;

public class PictureBean implements BaseEntity {
    /**
     * name : img-e886565e09dc89535c0fe0f976bd4828_compress.jpg
     * path : http://121.40.203.16/wwzs/images/201804/1524080051408824454.jpg
     */

    private String name;
    @SerializedName(value = "path", alternate = {"file_url"})
    private String path;
    private String id;
    private boolean isHasFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHasFile() {
        return isHasFile;
    }

    public void setHasFile(boolean hasFile) {
        isHasFile = hasFile;
    }
}
