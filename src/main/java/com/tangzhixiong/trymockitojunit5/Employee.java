package com.tangzhixiong.trymockitojunit5;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private Integer salary = 1000;

    public String deliverValue() throws Exception {
        Thread.sleep(10000);
        return "value";
    }

    void adjustSalary(Integer delta) {
        salary += delta;
    }

}
