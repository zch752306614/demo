package com.alice.zhang.demoapi.demo.proxy.service.impl;

import com.alice.zhang.demoapi.demo.proxy.service.GirlService;

/**
 * @author Alice
 * @version 1.0
 * @date 2021/3/26 9:37
 */
public class GirlServiceImpl implements GirlService {
    @Override
    public void date() {
        System.out.println("约会...");
    }

    @Override
    public void watchMovie() {
        System.out.println("看电影...");
    }

    @Override
    public void paPaPa() {
        System.out.println("PaPaPa...");
    }

    @Override
    public void paPaPa(Integer times) {
        System.out.println("一夜" + times + "郎！！！");
    }
}
