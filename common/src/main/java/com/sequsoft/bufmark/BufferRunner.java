package com.sequsoft.bufmark;

import com.sequsoft.bufmark.model.HouseGroup;

public interface BufferRunner {

    Object constructModel(HouseGroup commonModel);

    String modelToString(Object model);

    void interrogateModel(Object model);

    byte[] serialize(Object model);

    Object deserialize(byte[] data);

    HouseGroup unpack(Object model);
}
