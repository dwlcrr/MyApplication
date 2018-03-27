package com.example.test.utils.other;

import java.util.Random;

/**
 * Created by guizhen on 2016/10/14.
 */

public class RandomUtil {


    /**
     * 随机返回0~right 的一个数，包括0不包括right
     *
     * @param right
     * @return
     */
    public static int contain0(int right) {
        Random random = new Random();
        return random.nextInt(right);
    }

    public static String getChatMsgLocalId(int userId) {
        return System.currentTimeMillis() + "-" + userId;
    }


}
