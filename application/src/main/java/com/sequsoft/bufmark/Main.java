package com.sequsoft.bufmark;

import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;
import static com.sequsoft.bufmark.utils.CommonUtils.time;

import com.sequsoft.bufmark.flatbuffers.FlatbuffersRunner;
import com.sequsoft.bufmark.model.HouseGroup;
import com.sequsoft.bufmark.protobuf.ProtobufRunner;
import com.sequsoft.bufmark.utils.CommonUtils;

public class Main {
    public static void main(String[] args) {

        HouseGroup commonModel = randomHouseGroup();

        long felapsed = time(() -> {
            BufferRunner fbr = new FlatbuffersRunner();
            Object fmodel = fbr.constructModel(commonModel);
            byte[] fser = fbr.serialize(fmodel);
            byte[] compressed = CommonUtils.compress(fser);
            System.out.println("FLATBUFFERS SERIALISED SIZE = " + fser.length);
            System.out.println("FLATBUFFERS COMPRESSED SERIALISED SIZE = " + compressed.length);
            byte[] uncompressed = CommonUtils.decompress(compressed);
            Object fmodel_deser = fbr.deserialize(uncompressed);
            fbr.interrogateModel(fmodel_deser);
            HouseGroup hg = fbr.unpack(fmodel_deser);
            System.out.println(hg);
        });

        System.out.println("FLATBUFFERS TIME = " + felapsed / 1000000 + "ms.");

        long pelasped = time(() -> {
            BufferRunner pbr = new ProtobufRunner();
            Object pmodel = pbr.constructModel(commonModel);
            byte[] pser = pbr.serialize(pmodel);
            byte[] compressed = CommonUtils.compress(pser);
            //System.out.println(new String(pser));
            System.out.println("PROTOBUF SERIALISED SIZE = " + pser.length);
            System.out.println("PROTOBUF COMPRESSED SERIALISED SIZE = " + compressed.length);
            byte[] decompressed = CommonUtils.decompress(compressed);
            Object pmodel_deser = pbr.deserialize(decompressed);
            pbr.interrogateModel(pmodel_deser);
            HouseGroup hg = pbr.unpack(pmodel_deser);
        });

        System.out.println("PROTOBUF TIME = " + pelasped / 1000000 + "ms.");

    }
}
