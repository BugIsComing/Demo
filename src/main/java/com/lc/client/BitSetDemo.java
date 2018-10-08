package com.lc.client;

import java.util.BitSet;

/**
 * @author lc
 * @desc
 * @date 2018-10-08 22:01:49
 **/
public class BitSetDemo {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
//        bitSet.set(3);
//        bitSet.set(5);
//        bitSet.set(63);
        bitSet.set(64);
        System.out.println(bitSet.size());
        bitSet.flip(64);
        System.out.println(bitSet);
    }
}
