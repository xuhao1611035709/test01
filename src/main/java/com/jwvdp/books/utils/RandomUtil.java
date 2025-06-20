package com.jwvdp.books.utils;

public class RandomUtil {

    public  static  int getAToB(int x, int y){
        return  (int)((Math.random()*(y-x+1))+x);
    }
}
