package com.example.testskill.model;

import com.google.gson.annotations.SerializedName;

public class Data_Model {

    @SerializedName("id_article")
    private String id_article;
    @SerializedName("nama_article")
    private String nama_article;
    @SerializedName("isi_Article")
    private String isi_Article;

    public String getIdArticle() {
        return id_article;
    }

    public void setIdArticle(String id_article) {
        this.id_article = id_article;
    }

    public String getNama_article() {
        return nama_article;
    }

    public void setNama_article(String nama_barang) {
        this.nama_article = nama_barang;
    }

    public String getIsi_Article() {
        return isi_Article;
    }

    public void setIsi_article(String isi_Article) {
        this.isi_Article = isi_Article;
    }
}
