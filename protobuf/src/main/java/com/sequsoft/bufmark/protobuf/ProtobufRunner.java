package com.sequsoft.bufmark.protobuf;

import static com.sequsoft.bufmark.protobuf.Utils.convertHouseGroup;
import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.BufferRunner;

public class ProtobufRunner implements BufferRunner {
    @Override
    public void constructModel() {
        System.out.println(convertHouseGroup(randomHouseGroup()));
    }
}
