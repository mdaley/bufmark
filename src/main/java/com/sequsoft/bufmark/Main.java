package com.sequsoft.bufmark;


import static com.sequsoft.bufmark.common.CommonUtils.randomHouseGroup;
import static com.sequsoft.bufmark.protobuf.Utils.convertHouseGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        for(int i = 0; i < 20; i++) {
            System.out.println(convertHouseGroup(randomHouseGroup()));
        }
    }
}
