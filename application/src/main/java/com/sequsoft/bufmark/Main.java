package com.sequsoft.bufmark;

import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.flatbuffers.FlatbuffersRunner;
import com.sequsoft.bufmark.flatbuffers.Utils;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.protobuf.ProtobufRunner;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        HouseGroup commonModel = randomHouseGroup();

        BufferRunner fbr = new FlatbuffersRunner();
        Object fmodel = fbr.constructModel(commonModel);
        fbr.interrogateModel(fmodel);

        BufferRunner pbr = new ProtobufRunner();
        Object pmodel = pbr.constructModel(commonModel);
        pbr.interrogateModel(pmodel);

    }
}
