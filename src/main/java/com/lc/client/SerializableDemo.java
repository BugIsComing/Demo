package com.lc.client;

import java.io.*;

/**
 * @author lc
 * @desc
 * @date 2018-10-10 22:04:12
 **/
public class SerializableDemo {
    public static void main(String[] args) {
        Person person = new Person("lc", 27);
        System.out.println(person);
        writeObject(person);
        Person temp = (Person) readObject();
        System.out.println(temp);
    }

    public static void writeObject(Serializable arg) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(".\\obj\\object.obj"));
            oos.writeObject(arg);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object readObject() {
        Object obj = null;
        try {

            ObjectInputStream input = new ObjectInputStream(new FileInputStream(".\\obj\\object.obj"));
            obj = input.readObject();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}

class Person implements Serializable {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static final String FLAG = "DEMO";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" + this.name + ":" + this.age + "}";
    }
}