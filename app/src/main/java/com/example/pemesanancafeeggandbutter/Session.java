package com.example.pemesanancafeeggandbutter;
import androidx.lifecycle.ViewModel;

public class Session extends ViewModel {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
