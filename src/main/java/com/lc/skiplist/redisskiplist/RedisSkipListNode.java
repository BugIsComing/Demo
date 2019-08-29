package com.lc.skiplist.redisskiplist;

/**
 * redis中的跳表节点包括两部分
 * 一部分是单链表所需的信息（value，score，backward）
 * 一部分是节点层高信息，是个数组,每个元素也是个单链表
 * ----------
 * |   层     |
 * |   高     |
 * |   部     |-------
 * |   分     |linked |
 * |          |node   |
 * --------------------
 *
 * @param <T>
 */
public class RedisSkipListNode<T> {
    private T value;
    private int score;
    private RedisSkipListNode<T> backward;
    RedisSkipListLevel<T>[] level;

    public RedisSkipListNode() {
        level = new RedisSkipListLevel[32];
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public RedisSkipListNode<T> getBackward() {
        return backward;
    }

    public void setBackward(RedisSkipListNode<T> backward) {
        this.backward = backward;
    }

    public RedisSkipListLevel<T>[] getLevel() {
        return level;
    }

    public void setLevel(RedisSkipListLevel<T>[] level) {
        this.level = level;
    }

    public RedisSkipListLevel<T> getLevelAtIndex(int index) throws Exception {
        if (index < 0 || index > RedisSkipListFactory.MAX_LEVEL) {
            throw new Exception("Out of Bound");
        }
        return this.level[index];
    }

    public void setLevelAtIndex(RedisSkipListLevel<T> val, int index) throws Exception {
        if (index < 0 || index > RedisSkipListFactory.MAX_LEVEL) {
            throw new Exception("Out of Bound");
        }
        this.level[index] = val;
    }


}
