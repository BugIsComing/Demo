package com.lc.io;

import java.io.*;

public class ObjectInputStreamExample {

    public static class Person implements Serializable {
        //private static final long serialVersionUID = 1L;
        public String name = null;
        public int    age  =   2;
        public int    id = 3;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream("/person.bin"));

        Person person = new Person();
        person.name = "Jakob Jenkov";
        person.age  = 40;
        //person.id = 4;

        objectOutputStream.writeObject(person);
        objectOutputStream.close();


        ObjectInputStream objectInputStream =
                new ObjectInputStream(new FileInputStream("/person.bin"));

        Person personRead = (Person) objectInputStream.readObject();

        objectInputStream.close();

        System.out.println(personRead.name);
        System.out.println(personRead.age);
        //System.out.println(person == personRead);
    }
}