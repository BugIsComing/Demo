package com.lc.client;

/**
 * @author lc
 * 学习内部类的Demo
 * InnerClassDemo和Outter编译之后分别位于两个不同的class文件
 * JDK1.8之前Outter和Inner会被编译生成不同的class文件，1.8之后只生成一个class文件
 */
public class InnerClassDemo {
    public static void main(String[] args) {
        Outter outter = new Outter();
        outter.setMember("lc");
        //通过.new创建内部类对象
        Outter.Inner inner = outter.new Inner();
        inner.setMember("lclc");
        inner.print("lclclc");
        outter.changeName("lclclclc");
        outter.printName("Anonymous Inner Class");
    }
}

/**
 * 成员内部类、局部内部类、匿名内部类的Demo
 */
class Outter {
    private String member;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Inner getInnerInstance() {
        return new Inner();
    }

    /**
     * 成员内部类
     */
    class Inner {
        /**
         * 和外部类同名的属性
         */
        private String member;

        /**
         * arg无需是final
         *
         * @param arg
         */
        public void print(String arg) {
            System.out.println("Outter member:" + Outter.this.member + "\tInner member:" + member + "\targ:" + arg);
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }
    }

    public void changeName(String name) {
        /**
         * 局部内部类
         */
        class LocalInnerClass {
            public void print() {
                System.out.println(name);
            }
        }
    }
    /**
     * 匿名内部类
     * JDK1.8之前，如果一个方法的形参会被局部内部类使用，该形参必须声明为final，JDK1.8之后不用声明为final
     */
    public void printName(String name) {
        new Anonymous() {
            @Override
            public void print() {
                System.out.println(name);
            }
        }.print();
    }
}

interface Anonymous {
    /**
     * 打印
     * @param
     * @return
     */
    void print();
}