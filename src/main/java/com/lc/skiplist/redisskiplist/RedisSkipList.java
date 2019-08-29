package com.lc.skiplist.redisskiplist;

/**
 * 学习Redis中跳表结构并用java实现
 * Redis中的跳表结构比较特殊，限定了最大层数是32，每次创建节点时随机生成一个1~32之间的数
 * 作为其层高
 */
public class RedisSkipList<T> {
    private RedisSkipListNode<T> header;
    private RedisSkipListNode<T> tail;
    //存放当前总的节点数
    private int length;
    //存放目前所有节点中最大的层数
    private int level;

    public RedisSkipListNode<T> getHeader() {
        return header;
    }

    public void setHeader(RedisSkipListNode<T> header) {
        this.header = header;
    }

    public RedisSkipListNode<T> getTail() {
        return tail;
    }

    public void setTail(RedisSkipListNode<T> tail) {
        this.tail = tail;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String print() {
        return "";
    }

}
