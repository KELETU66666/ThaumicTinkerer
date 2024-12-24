package com.nekokittygames.thaumictinkerer.common.utils;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.Collections;

public class CombinationGenerator {
    private static final  ArrayList<MutablePair<Short, ArrayList<ArrayList<Short>>>> Cache = new ArrayList<>();
    public static ArrayList<Short> getIncreasingCombinations(int index, short k){
        while (true){
            if(CombinationGenerator.Cache.size() < k){
                CombinationGenerator.calculateIncreasingCombinations(k, k);
            }else if(CombinationGenerator.Cache.get(k - 1).getRight().size() < index + 1){
                CombinationGenerator.calculateIncreasingCombinations((short)(CombinationGenerator.Cache.get(k - 1).getLeft() + 1), k);
            }else {
                break;
            }
        }
        return new ArrayList<>(CombinationGenerator.Cache.get(k - 1).getRight().get(index));
    }

    public static int combination(int n, int k){
        if (k > n || k < 0) return 0;
        if (k > n - k) k = n - k;
        int result = 1;
        for (int i = 1; i <= k; i++) {
            result *= (n - i + 1);
            result /= i;
        }
        return result;
    }

    private static void calculateIncreasingCombinations(short n, short k){
        if(n < 1 || k < 1 || (CombinationGenerator.Cache.size() >= k && CombinationGenerator.Cache.get(k - 1).getLeft() >= n)) return;

        if(n != k && (CombinationGenerator.Cache.size() < k || CombinationGenerator.Cache.get(k - 1).getLeft() < n - 1)) {
            CombinationGenerator.calculateIncreasingCombinations((short) (n - 1), k);
        }
        if(k != 1 && (CombinationGenerator.Cache.size() < k - 1 || CombinationGenerator.Cache.get(k - 2).getLeft() < n )){
            CombinationGenerator.calculateIncreasingCombinations(n, (short) (k - 1));
        }
        if(n == k){
            ArrayList<Short> temp = new ArrayList<>();
            for(short i = 0; i < k; i++){
                temp.add(i);
            }
            CombinationGenerator.Cache.add(new MutablePair<>(n, new ArrayList<>(Collections.singletonList(temp))));
            return;
        }else if(k == 1){
            CombinationGenerator.Cache.get(0).getRight().add(new ArrayList<>(Collections.singletonList((short) (n - 1))));
            CombinationGenerator.Cache.get(0).setLeft(n);
            return;
        }
        int iMax = CombinationGenerator.combination(n - 1, k - 1);
        for (int i = 0; i < iMax; i++) {
            ArrayList<Short> temp = new ArrayList<>(CombinationGenerator.Cache.get(k - 2).getRight().get(i));
            temp.add((short) (n - 1));
            CombinationGenerator.Cache.get(k - 1).getRight().add(temp);
        }
        CombinationGenerator.Cache.get(k - 1).setLeft(n);
    }
}
