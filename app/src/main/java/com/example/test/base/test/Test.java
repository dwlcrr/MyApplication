package com.example.test.base.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");list.add("2");
        list.add(0,"0");
        System.out.print(list);
    }
    /**
     * 小数点后保留四位有效数字
     */
    public static float getNumber(String number) {
        DecimalFormat df = new DecimalFormat("#.####");
//        DecimalFormat df = new DecimalFormat("#,###.0000");
        float f = Float.valueOf(df.format(Double.parseDouble(number)));
        return f;
    }

    public static String getNumber(double number) {
        DecimalFormat df = new DecimalFormat("#,##0.0000");
//        float f=Float.valueOf(df.format(number));
//        DecimalFormat df = new DecimalFormat("#,###.####");
        return String.valueOf(df.format(BigDecimal.valueOf(number)));
    }
}  