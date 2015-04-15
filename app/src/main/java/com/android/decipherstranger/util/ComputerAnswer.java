
package com.android.decipherstranger.util;

import java.util.Random;

/**
 * Created by peng on 2015/3/17.
 */
public class ComputerAnswer {

    public static int Answer() {
        Random random = new Random();
        int x, y, z, rand;
        x = ((random.nextInt() % 10) + 10) % 10;
        rand = 100 * x;
        y = ((random.nextInt() % 10) + 10) % 10;
        rand += (10 * y);
        z = ((random.nextInt() % 10) + 10) % 10;
        rand += z;
        if (rand <= MyStatic.rockInt) {
            return 5;
        } else if (rand <= MyStatic.rockInt + MyStatic.scissorsInt) {
            return 0;
        } else {
            return 2;
        }
    }
}