package com.lc.skiplist.redisskiplist;

import java.util.Random;

public class RedisSkipListFactory<T> {
    public static final double PROBABILITY = 0.5;//向上提升一个的概率
    public static final int MAX_LEVEL = 32;
    /********API*******/
    /**
     * 创建跳表，其header是32层高节点
     *
     * @return
     */
    public RedisSkipList<T> createList() {
        RedisSkipList<T> rsl = new RedisSkipList<>();
        rsl.setHeader(createNode(null, 0, MAX_LEVEL));
        rsl.getHeader().setBackward(null);
        rsl.setTail(null);
        rsl.setLength(0);
        rsl.setLevel(1);
        return rsl;
    }

    /**
     * 创建跳表节点
     *
     * @param val
     * @param score
     * @param level
     * @return
     */
    public RedisSkipListNode<T> createNode(T val, int score, int level) {
        RedisSkipListNode<T> node = new RedisSkipListNode<>();
        node.setBackward(null);
        node.setValue(val);
        node.setScore(score);
        RedisSkipListLevel<T>[] tempLevel = new RedisSkipListLevel[MAX_LEVEL];
        node.setLevel(tempLevel);
        for (int i = 0; i < MAX_LEVEL; i++) {
            tempLevel[i] = new RedisSkipListLevel<T>();
        }
        return node;
    }

    public void insertList(RedisSkipList<T> target, int score, T val) throws Exception {

        RedisSkipListNode<T>[] update = creatNodeArray(MAX_LEVEL);
        RedisSkipListNode<T> x;
        int[] rank = new int[MAX_LEVEL];

        //通过score查找插入位置，并保存相应的路径
        x = target.getHeader();
        int currentLevel = target.getLevel();
        RedisSkipListLevel<T> temp;
        //保存路径，允许插入重复的score和value，重复score后插
        //这个路径就是插入节点的在每一层的前驱
        for (int i = currentLevel - 1; i >= 0; i--) {
            rank[i] = (i == currentLevel - 1 ? 0 : rank[i + 1]);
            temp = x.getLevelAtIndex(i);
            if (temp == null) {
                x.setLevelAtIndex(new RedisSkipListLevel<>(), i);
            }
            while (temp.getForward() != null &&
                    (temp.getForward().getScore() < score || (temp.getForward().getScore() == score &&
                            temp.getForward().getValue() != val))) {
                rank[i] += temp.getSpan();
                x = temp.getForward();
            }
            update[i] = x;
        }
        int randomLevel = getRandomLevel();
        //新插入的节点层数比已有最高节点层数还要高，修改header的层高
        if (randomLevel > target.getLevel()) {
            for (int i = target.getLength(); i < randomLevel; i++) {
                rank[i] = 0;
                update[i] = target.getHeader();
                update[i].getLevelAtIndex(i).setSpan(target.getLength());
            }
            target.setLevel(randomLevel);
        }
        //创建新节点
        x = createNode(val, score, randomLevel);
        for (int i = 0; i < randomLevel; i++) {
            //设置x在第i层的后继，同时修改span
            x.getLevelAtIndex(i).setForward(update[i].getLevelAtIndex(i).getForward());
            update[i].getLevelAtIndex(i).setForward(x);
            x.getLevelAtIndex(i).setSpan(update[i].getLevelAtIndex(i).getSpan() - (rank[0] - rank[i]));
            update[i].getLevelAtIndex(i).setSpan(rank[0] - rank[i] + 1);
        }
        int currentSpan;
        for (int i = randomLevel; i < MAX_LEVEL; i++) {
            if (update[i].getLevelAtIndex(i) == null) {
                update[i].setLevelAtIndex(new RedisSkipListLevel<>(), i);
            }
            currentSpan = update[i].getLevelAtIndex(i).getSpan();
            update[i].getLevelAtIndex(i).setSpan(currentSpan + 1);
        }

        x.setBackward(update[0] == target.getHeader() ? null : update[0]);

        //尾结点
        if (x.getLevelAtIndex(0).getForward() != null) {
            x.getLevelAtIndex(0).getForward().setBackward(x);
        } else {
            target.setTail(x);
        }
        target.setLength(target.getLength() + 1);
    }

    public int getRandomLevel() {
        int level = 1;
        Random random = new Random();
        while (random.nextDouble() < PROBABILITY) {
            level++;
        }
        if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }
        return level;
    }

    public RedisSkipListLevel<T>[] creatLevelArray(int level) {
        RedisSkipListLevel<T>[] levels = new RedisSkipListLevel[level];
        for (int i = 0; i < level; i++) {
            levels[i] = new RedisSkipListLevel<>();
        }
        return levels;
    }

    public RedisSkipListNode<T>[] creatNodeArray(int level) {
        RedisSkipListNode<T>[] levels = new RedisSkipListNode[level];
        for (int i = 0; i < level; i++) {
            levels[i] = new RedisSkipListNode<>();
        }
        return levels;
    }
}
