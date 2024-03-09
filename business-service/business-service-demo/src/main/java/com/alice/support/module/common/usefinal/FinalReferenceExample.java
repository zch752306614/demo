package com.alice.support.module.common.usefinal;

public class FinalReferenceExample {

    final int[] intArray;

    static FinalReferenceExample obj;

    public FinalReferenceExample() {
        intArray = new int[1];
        intArray[0] = 1;
    }

    public static void writerOne() {
        obj = new FinalReferenceExample();
    }

    public static void writerTwo() {
        obj.intArray[0] = 2;
    }

    public static void reader() {
        if (obj != null) {
            int temp = obj.intArray[0];
            System.out.println("reader执行完成，输出结果=" + obj.intArray[0]);
        }
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10000; i++) {
        Thread thread1 = new Thread(FinalReferenceExample::writerOne);
//        Thread thread2 = new Thread(FinalReferenceExample::writerTwo);
        Thread thread3 = new Thread(FinalReferenceExample::reader);
        thread1.start();
//        thread2.start();
        thread3.start();
//    }
    }

}
