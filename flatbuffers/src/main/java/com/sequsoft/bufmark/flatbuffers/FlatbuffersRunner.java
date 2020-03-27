package com.sequsoft.bufmark.flatbuffers;

import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.BufferRunner;

import java.nio.ByteBuffer;

public class FlatbuffersRunner implements BufferRunner {
    @Override
    public void constructModel() {
        ByteBuffer buf = Utils.createHouseGroupBuffer(randomHouseGroup());
        System.out.println(new String(buf.array()));
    }
}
