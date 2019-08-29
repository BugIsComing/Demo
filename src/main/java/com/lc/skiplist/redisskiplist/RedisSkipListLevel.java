package com.lc.skiplist.redisskiplist;

public class RedisSkipListLevel<T> {
    private RedisSkipListNode<T> forward;
    private int span;

    public RedisSkipListLevel() {
        this.forward = null;
        this.span = 0;
    }

    public RedisSkipListLevel(RedisSkipListNode<T> forward, int span) {
        this.forward = forward;
        this.span = span;
    }

    public RedisSkipListNode<T> getForward() {
        return forward;
    }

    public void setForward(RedisSkipListNode<T> forward) {
        this.forward = forward;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }
}
