package com.yz.lo;

import org.junit.jupiter.api.Test;

public class test {
    /**
     * 打印心形
     */
    public static void aiXin() {
        for (float y = (float) 1.2; y > -1.2; y -= 0.1) {
            for (float x = (float) -1.2; x < 1.2; x += 0.05) {
                float a = x * x + y * y - 1;
                if ((a * a * a - x * x * y * y * y) <= 0.0) {
                    System.out.print("^");
                } else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    public void test1() {
        aiXin();
    }
}
