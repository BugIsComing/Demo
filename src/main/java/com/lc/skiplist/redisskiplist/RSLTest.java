package com.lc.skiplist.redisskiplist;

public class RSLTest {
    public static void main(String[] args) {
        RedisSkipListFactory<String> factory = new RedisSkipListFactory<>();
        RedisSkipList<String> rsl = factory.createList();
        String val = "lc";
        try {
            factory.insertList(rsl, 1, val);
            factory.insertList(rsl,5,val);
            factory.insertList(rsl,3,val);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("x");
    }
}
