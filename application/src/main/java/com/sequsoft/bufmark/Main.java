package com.sequsoft.bufmark;

import static com.sequsoft.bufmark.common.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.flatbuffers.Utils;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        //System.out.println(convertHouseGroup(randomHouseGroup()));
        ByteBuffer buf = Utils.createHouseGroupBuffer(randomHouseGroup());
        System.out.println(new String(buf.array()));

    }
}
