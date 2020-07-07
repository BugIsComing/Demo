package com.lc.client;

class ProductOfNumbers {
    int[] data;
    int total;
    public ProductOfNumbers() {
        total=0;
        data = new int[50000];
    }

    public void add(int num) {
        data[total]=num;
        total++;
        for(int i=total-2;i>=0;i--){
            data[i] = data[i]*num;
        }
    }

    public int getProduct(int k) {
        if(k<=total) {
            return data[total - k];
        }
        else {
            return data[0];
        }
    }

    public static void main(String[] args) {
        ProductOfNumbers p = new ProductOfNumbers();

    }
}
