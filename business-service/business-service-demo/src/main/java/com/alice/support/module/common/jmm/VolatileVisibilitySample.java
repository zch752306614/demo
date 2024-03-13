package com.alice.support.module.common.jmm;

/**
 * @Description TODO
 * @DateTime 2024/3/8 9:49
 */
public class VolatileVisibilitySample {

    volatile boolean initFlag = false;
    volatile int i = 0;

    public VolatileVisibilitySample() {
    }

    public void save() {
        this.initFlag = true;
        String threadName = Thread.currentThread().getName();
        System.out.println("线程：" + threadName + ":修改了共享变量initFlag");
    }

    public void load() {
        String threadName = Thread.currentThread().getName();
        while (!initFlag) {
            // 此处死循环，等待initFlag的变更
            System.out.println("线程：" + threadName + "：等待initFlag变量的变更");
        }
        System.out.println("线程：" + threadName + "：感知到initFlag变量的变更");
    }

    public void add1() {
        i++;
        System.out.println(this.i);
    }

    public void add2() {
        int t = i;
        i = t + 1;
        System.out.println(this.i);
    }

    public static void main(String[] args) throws InterruptedException {
//        VolatileVisibilitySample sample = new VolatileVisibilitySample();
//        Thread thread_a = new Thread(() -> sample.save(), "thread A");
//        Thread thread_b = new Thread(() -> sample.load(), "thread B");
//        thread_b.start();
//        Thread.sleep(10);
//        thread_a.start();
//        for (int i=0;i<100;i++) {
//            System.out.println("--------------------");
//            VolatileVisibilitySample sample = new VolatileVisibilitySample();
//            Thread thread_a = new Thread(sample::add1, "thread A");
//            Thread thread_b = new Thread(sample::add1, "thread B");
//            thread_a.start();
//            thread_b.start();
//            Thread.sleep(100);
//        }
        VolatileVisibilitySample sample = new VolatileVisibilitySample();
        Thread thread_a = new Thread(sample::add1, "thread A");
        Thread thread_b = new Thread(sample::add1, "thread B");
        thread_a.start();
        thread_b.start();
    }

}
