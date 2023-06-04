package org.course_registration.controller.viewobject;

import java.math.BigDecimal;

public class TchWalletVO {
    private Integer id;
    private BigDecimal balance;

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
}
