package com.example.testskill.Response;

import com.example.testskill.model.Data_Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data_Response {

    private Boolean status;
    private String message;

    @SerializedName("data")
    List<Data_Model> seluruh_article;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data_Model> getSeluruh_article() {
        return seluruh_article;
    }

    public void setSeluruh_article(List<Data_Model> seluruh_article) {
        this.seluruh_article = seluruh_article;
    }
}
