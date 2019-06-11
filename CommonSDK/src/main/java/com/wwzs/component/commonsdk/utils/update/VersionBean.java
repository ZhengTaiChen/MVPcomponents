package com.wwzs.component.commonsdk.utils.update;

import com.wwzs.component.commonsdk.base.BaseEntity;

/**
 * 功能
 * 描述
 * Created By 郑小国 on 2019/1/17
 */
public class VersionBean implements BaseEntity {


    /**
     * err_no : 0
     * err_msg :
     * note : {"Version":"229","UpgradeLog":"版本dev1.1.13 1、页面更新及BUG处理；","URL":"http://39.104.86.19/data/../data/applog/1546838953.apk","IsForced":"0"}
     */

    private int err_no;
    private String err_msg;
    private NoteBean note;

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public NoteBean getNote() {
        return note;
    }

    public void setNote(NoteBean note) {
        this.note = note;
    }

    public static class NoteBean {
        /**
         * Version : 229
         * UpgradeLog : 版本dev1.1.13 1、页面更新及BUG处理；
         * URL : http://39.104.86.19/data/../data/applog/1546838953.apk
         * IsForced : 0
         */

        private int Version;
        private String UpgradeLog;
        private String URL;
        private String IsForced;

        public int getVersion() {
            return Version;
        }

        public void setVersion(int Version) {
            this.Version = Version;
        }

        public String getUpgradeLog() {
            return UpgradeLog;
        }

        public void setUpgradeLog(String UpgradeLog) {
            this.UpgradeLog = UpgradeLog;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getIsForced() {
            return IsForced;
        }

        public void setIsForced(String IsForced) {
            this.IsForced = IsForced;
        }
    }
}
