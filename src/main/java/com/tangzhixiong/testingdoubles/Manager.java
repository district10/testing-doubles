package com.tangzhixiong.testingdoubles;

public class Manager {
    private Employee employee;

    Manager(Employee employee) {
        this.employee = employee;
    }

    public String deliverValue() throws Exception {
        try {
            return employee.deliverValue();
        } catch (Exception e) {
            return deliverFallbackValue();
        }
    }

    String deliverFallbackValue() throws Exception {
        Thread.sleep(5000);
        return "fallback value";
    }

    void review(String result) {
        int delta = 0;
        if ("good".equals(result)) {
            delta = 10;
        } else if ("bad".equals(result)) {
            delta = -10;
        }
        employee.adjustSalary(delta);
    }

}
