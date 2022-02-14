package com.boot.tenant.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author binSin
 * @date 2022/2/10
 */
public class TestMain {

    public static void main(String[] args) throws IOException {

        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("a","a");
        treeMap.put("a","a");
        treeMap.put("b","b");
        treeMap.put("d","d");
        treeMap.put("c","c");
        treeMap.forEach((k,v)->{
            System.out.println(k+";;"+v);
        });

//        Scanner scanner = new Scanner(System.in);
//            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        while (true){
//            String s = scanner.nextLine();
////            String s = input.readLine();
//            System.out.println(s);
//
//        }

//        List<Integer> list = new CopyOnWriteArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer integer : list) {
            System.out.println(integer);
            if (integer == 2) {
                list.add(4);
            }
        }
    }
}
