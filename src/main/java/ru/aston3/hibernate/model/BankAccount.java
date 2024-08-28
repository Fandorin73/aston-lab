package ru.aston3.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount extends BillingDetails {
    @Column(name = "account")
    private int account;

    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "swift")
    private String swift;

    public BankAccount() {
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "account=" + account +
                ", bankName='" + bankName + '\'' +
                ", swift='" + swift + '\'' +
                '}';
    }
}
