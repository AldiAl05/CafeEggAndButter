package com.example.pemesanancafeeggandbutter.Entitas;

public class Pesanan {
    private String nama, nohp, alamat, produk;
    private String  harga;
    private Long jumlah,totalharga;
    private int quantity;

    public Pesanan() {
        // Empty constructor needed for Firebase
    }

    public Pesanan(String nama, String nohp, String alamat, String produk, Long jumlah, String harga, Long totalharga, int quantity) {
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
        this.produk = produk;
        this.jumlah = jumlah;
        this.harga = harga;
        this.totalharga = totalharga;
        this.quantity = quantity;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public Long getJumlah() {
        return jumlah;
    }

    public void setJumlah(Long jumlah) {
        this.jumlah = jumlah;
    }

    public Long getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(Long totalharga) {
        this.totalharga = totalharga;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
