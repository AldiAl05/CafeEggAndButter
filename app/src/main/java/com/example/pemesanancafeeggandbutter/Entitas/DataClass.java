package com.example.pemesanancafeeggandbutter.Entitas;

//gerbang penghubung database
public class DataClass {
    private String dataTitle;
    private String dataDesc;
    private String dataHarga;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDataTitle() {
        return dataTitle;
    }
    public String getDataDesc() {
        return dataDesc;
    }
    public String getDataHarga() {
        return dataHarga;
    }
    public String getDataImage() {
        return dataImage;
    }
    public DataClass(String dataTitle, String dataDesc, String dataHarga, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataHarga = dataHarga;
        this.dataImage = dataImage;
    }
    public DataClass(){

    }
}
