package com.wwzs.component.commonsdk.entity;

import com.google.gson.annotations.SerializedName;
import com.wwzs.component.commonsdk.base.BaseEntity;

public class ResultBean<T> implements BaseEntity {


    protected StatusBean status;
    protected ExpandBean expand;
    protected PageBean paginated;
    @SerializedName(value = "data", alternate = {"note", "date"})
    private T data;
    private String error;
    private String err_msg;

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public StatusBean getStatus() {
        if (status == null) {
            return new StatusBean(error, Integer.valueOf(error), err_msg != null ? err_msg : "");
        }
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ExpandBean getExpand() {
        return expand;
    }

    public void setExpand(ExpandBean expand) {
        this.expand = expand;
    }

    public PageBean getPaginated() {
        return paginated;
    }

    public void setPaginated(PageBean paginated) {
        this.paginated = paginated;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
