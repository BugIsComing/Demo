package com.lc.model;

import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * @Author:LC
 * @Date:Created in 22:46 2018/1/24
 * @Modifyed by:
 */
public class Student {
    private String name;
    private int age;

    public Student() {
        System.out.println("Student()");
    }

    public Student(String name, int age) {
        System.out.println("Student(String name, int age)");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName(String name)");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
