package com.boot.juc.thread;

/**
 * @author binSin
 * @date 2021/11/15
 */
public class testDemo {

    final String str = "testDemo.str";
    private A innerClass;
    public A getInner() {
        innerClass = new A(12,"ads");
        return innerClass;

    }


    public static void main(String[] args) {
        testDemo a = new testDemo();
        A inner = a.getInner();

        System.out.println(inner.str);
        System.out.println(inner.str1);
        System.out.println(inner.str2);
    }

    class A {
        Integer num;

        String age;

        final String str1 = "testDemo.A.str";

        final String str2 = this.str1;

        final String str = testDemo.this.str;

        public A(){

        }

        public A(Integer num, String age) {
            this.num = num;
            this.age = age;
        }
    }
}


