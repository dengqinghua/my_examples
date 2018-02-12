package com.dengqinghua.calculate;

public class Salary {
    private int monthSalary;
    private int bonus;

    /**
     * 计算一年的总的薪水
     *
     * @return 返回总薪水值
     */
    public long calculateYearSalary() {
        return this.monthSalary * 12 + bonus;
    }

    public Salary(int monthSalary, int bonus) {
        this.monthSalary = monthSalary;
        this.bonus = bonus;
    }
}

