package com.lc.proxy;

public class Proxy implements Subject {
    /**
     * 代理的目标对象
     */
    private Subject target;

    public Proxy() {
        this.target = null;
    }

    public Proxy(Subject subject) {
        this.target = subject;
    }

    @Override
    public void request() {
        target.request();
    }
}
