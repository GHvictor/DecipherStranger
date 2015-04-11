
package com.android.decipherstranger.util;

import java.util.Random;

/**
 * Created by peng on 2015/3/17.
 */
public class ComputerAnswer {

    private static int rockInt = 0;
    private static int scissors = 0;
    private static int paperInt = 0;

    private static void setZero() {
        /*读取数据库*/
        rockInt = 333;
        scissors = 333;
        paperInt = 333;
    }

    public static int Answer(int count, int player,int grade) {
        Random random = new Random();
        int flag = (random.nextInt() % 3 + 3) % 3;
        if (count >= (grade - 2) && flag > 1) {
            switch (player) {
                case 0:
                    return 5;
                case 2:
                    return 0;
                case 5:
                    return 2;
            }
        } else {
            setZero();
            int x, y, z, rand;
            x = ((random.nextInt() % 10) + 10) % 10;
            rand = 100 * x;
            y = ((random.nextInt() % 10) + 10) % 10;
            rand += (10 * y);
            z = ((random.nextInt() % 10) + 10) % 10;
            rand += z;
            if (rand <= rockInt) {
                return 5;
            } else if (rand <= rockInt + scissors) {
                return 0;
            } else {
                return 2;
            }
        }
        return 0;
    }
}