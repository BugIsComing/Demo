package com.lc.weakreferencelock;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SegmentStrongLock<T> {
    private HashMap<T, ReentrantLock> lockMap = new HashMap<T, ReentrantLock>();

    public ReentrantLock get(T key) {

        ReentrantLock lockRef = lockMap.get(key);
        if (lockRef == null) {
            System.out.println("**********");
            lockRef = new ReentrantLock();
            lockMap.put(key, lockRef);

        }
        return lockRef;
    }


}
