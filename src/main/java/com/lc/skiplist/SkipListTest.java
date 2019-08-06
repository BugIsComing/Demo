package com.lc.skiplist;

public class SkipListTest {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        skipList.put(1,3);
        skipList.put(2,3);
        skipList.put(6,3);
        skipList.put(4,3);
        skipList.put(8,3);
        skipList.search(6);
        System.out.println(skipList.printByLevel());
        skipList.remove(4);
        System.out.println(skipList.printByLevel());
    }
}
