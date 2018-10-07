package com.lc.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author lc
 * @desc 演示Collections进行二叉查找的两种方式
 * @date 2018-09-30 22:05:58
 **/
public class CollectionsDemo {
    public static void main(String[] args) {
        /**
         * 二叉查找两种方式，一种是class实现Comparable接口，一种不实现，在查找方法中给出元素偏序关系
         */
        List<Item> list1 = new ArrayList<Item>();
        list1.add(new Item("林青霞", 27));
        list1.add(new Item("风清扬", 30));
        list1.add(new Item("刘晓曲", 28));
        list1.add(new Item("武鑫", 29));
        list1.add(new Item("林青霞", 27));
        Collections.sort(list1);
        int index = Collections.binarySearch(list1, new Item("林青霞", 27));
        System.out.println(index);

        List<Item2> list2 = new ArrayList<Item2>();
        list2.add(new Item2("林青霞", 27));
        list2.add(new Item2("风清扬", 30));
        list2.add(new Item2("刘晓曲", 28));
        list2.add(new Item2("武鑫", 29));
        list2.add(new Item2("林青霞", 27));
        Collections.sort(list2,new MyComparable());
        int index2 = Collections.binarySearch(list2, new Item2("林青霞", 27),new MyComparable());
        System.out.println(index2);

    }
}

/**
 * 方式一，类实现Comparable接口
 */
class Item implements Comparable<Item> {

    private String name;
    private int age;

    public Item() {
    }

    public Item(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    /**
     * 自定义两个元素的偏序关系
     *
     * @param s
     * @return
     */
    @Override
    public int compareTo(Item s) {
        int num = this.age - s.age;
        int num1 = (num == 0 ? this.name.compareTo(s.name) : num);
        return num1;
    }
}

/**
 * 方式二，不实现Comparable接口，单独实现一个类MyComparable并实现Compartor接口
 */
class Item2 {
    private String name;
    private int age;

    public Item2() {
    }

    public Item2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

/**
 * 比较器
 */
class MyComparable implements Comparator<Item2> {
    @Override
    public int compare(Item2 o1, Item2 o2) {
        int num = o1.getAge() - o2.getAge();
        int num1 = (num == 0 ? o1.getName().compareTo(o2.getName()) : num);
        return num1;
    }
}