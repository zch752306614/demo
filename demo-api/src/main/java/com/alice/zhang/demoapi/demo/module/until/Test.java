package com.alice.zhang.demoapi.demo.module.until;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/4 15:13
 */
public class Test {

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDate = df.format(new Date());
        System.out.println(startDate);
    }

}
