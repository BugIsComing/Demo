package com.lc.client;

/**
 * @author lc
 * @desc
 * @date 2019-01-24 19:09:25
 **/
public class MethodTest {
    public static void main(String[] args) {
        A a = new B();
        B b = new B();
        C c = new C();
        D d = new D();
        System.out.println(a.show(b));
        System.out.println(a.show(c));
        System.out.println(a.show(d));
    }
}
class A{
    public String show(D obj){
        return "DinA";
    }
    public String show(A obj){
        return "AinA";
    }
}

class B extends A{
    public String show(B obj){
        return "BinB";
    }
    public String show(A obj){
        return "AinB";
    }
}
class C extends B{
    public String show(C obj){
        return "CinC";
    }
}
class D extends B{
    public String show(B obj){
        return "BinD";
    }
}