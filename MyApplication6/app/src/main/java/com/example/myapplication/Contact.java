package com.example.myapplication;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    public String hoTen;
    public String soDT;

    public Contact(String hoTen, String soDT) {
        this.hoTen = hoTen;
        this.soDT = soDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(hoTen, contact.hoTen) && Objects.equals(soDT, contact.soDT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoTen, soDT);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "hoTen='" + hoTen + '\'' +
                ", soDT='" + soDT + '\'' +
                '}';
    }
}
