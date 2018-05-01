package com.dengqinghua.example;

public class Field {
    private int instanceVarWithInitValue = 1;
    final int instanceFinalVarWithInitValue = 2;
    static int staticVarWithInitValue = 3;
    static final int staticFinalVarWithInitValue = 4;
    public int instanceVarWithoutInitValue;
    static int staticVarWithoutInitValue;

    public static void main(String[] args) {
        int a = "D".hashCode();
    }
}
