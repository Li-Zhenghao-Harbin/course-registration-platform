package org.course_registration.controller.viewobject;

import java.math.BigDecimal;

public class StuWalletVO {
    private Integer id;
    private BigDecimal balance;
    private Integer stuId;

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

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }
}
