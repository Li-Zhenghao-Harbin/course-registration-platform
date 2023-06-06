package org.course_registration.service.model;

import java.math.BigDecimal;

public class TchWalletModel {
    private Integer id;
    private BigDecimal balance;
    private Integer tchId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getTchId() {
        return tchId;
    }

    public void setTchId(Integer tchId) {
        this.tchId = tchId;
    }
}
