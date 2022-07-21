package com.example.zj.tool;

import java.util.HashMap;
import java.util.Map;

public class TestCos {
    public static void main(String[] args) {
//        String filename[]={"test1.jpg","外星人.jpg","飞船.jpg"};
//        System.out.println(Cos.DownFile("123",filename,"D://cos下载"));
        Map<String,String> map=new HashMap<>();
        Cos.SerchContext("123",map);
        System.out.println(map);
    }
}
