package com.dengqinghua.calculate;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SalaryTest {
    @Test public void calculateYearSalary() {
        Salary salary = new Salary(10_000, 20_000);
        assertEquals(salary.calculateYearSalary(), 140_000);
    }

    @Test public void calculateYearSalary2() {
        Salary salary = new Salary(10_000, 20_000);
        assertEquals(salary.calculateYearSalary(), 140_000);
    }
}
