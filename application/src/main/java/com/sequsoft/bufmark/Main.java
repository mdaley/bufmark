package com.sequsoft.bufmark;

import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;
import static com.sequsoft.bufmark.utils.CommonUtils.time;

import com.sequsoft.bufmark.flatbuffers.FlatbuffersRunner;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.protobuf.ProtobufRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        HouseGroup commonModel = randomHouseGroup();

        long felapsed = time(() -> {
            BufferRunner fbr = new FlatbuffersRunner();
            Object fmodel = fbr.constructModel(commonModel);
            byte[] fser = fbr.serialize(fmodel);
            Object fmodel_deser = fbr.deserialize(fser);
            fbr.interrogateModel(fmodel_deser);
        });

        System.out.println("FLATBUFFERS TIME = " + felapsed / 1000000 + "ms.");

        long pelasped = time(() -> {
            BufferRunner pbr = new ProtobufRunner();
            Object pmodel = pbr.constructModel(commonModel);
            byte[] pser = pbr.serialize(pmodel);
            Object pmodel_deser = pbr.deserialize(pser);
            pbr.interrogateModel(pmodel_deser);
        });

        System.out.println("PROTOBUF TIME = " + pelasped / 1000000 + "ms.");

    }
}
