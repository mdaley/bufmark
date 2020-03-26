package com.sequsoft.bufmark;


import static com.sequsoft.bufmark.common.CommonUtils.randomHouseGroup;
import static com.sequsoft.bufmark.flatbuffers.Utils.createHouseGroupBuffer;
import static com.sequsoft.bufmark.protobuf.Utils.convertHouseGroup;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        //System.out.println(convertHouseGroup(randomHouseGroup()));
        ByteBuffer buf = createHouseGroupBuffer(randomHouseGroup());
        System.out.println(new String(buf.array()));

    }
}
