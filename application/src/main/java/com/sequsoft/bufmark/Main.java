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
        byte[] fser = fbr.serialize(fmodel);
        Object fmodel_deser = fbr.deserialize(fser);
        fbr.interrogateModel(fmodel_deser);

        BufferRunner pbr = new ProtobufRunner();
        Object pmodel = pbr.constructModel(commonModel);
        byte[] pser = pbr.serialize(pmodel);
        Object pmodel_deser = pbr.deserialize(pser);
        pbr.interrogateModel(pmodel_deser);

    }
}
