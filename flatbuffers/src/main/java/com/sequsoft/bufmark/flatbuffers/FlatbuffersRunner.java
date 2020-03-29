package com.sequsoft.bufmark.flatbuffers;

import com.sequsoft.bufmark.BufferRunner;
import com.sequsoft.bufmark.model.HouseGroup;

import java.nio.ByteBuffer;

public class FlatbuffersRunner implements BufferRunner {
    @Override
    public Object constructModel(HouseGroup commonModel) {
        byte[] bytes = Packer.createHouseGroupBuffer(commonModel);

        return bytes;
    }

    @Override
    public String modelToString(Object model) {
        return new String((byte[])model);
    }

    @Override
    public void interrogateModel(Object model) {
        ByteBuffer buffer = ByteBuffer.wrap((byte[])model);

        F_HouseGroup houseGroup = F_HouseGroup.getRootAsF_HouseGroup(buffer);
        F_Person.Vector organisers = houseGroup.organisersVector();

        int people = organisers.length();

        F_House.Vector houses = houseGroup.housesVector();

        for (int i = 0; i < houses.length(); i++) {
            people += houses.get(i).occupantsVector().length();
        }

        System.out.println("FLATBUFFERS PEOPLE COUNT = " + people);
    }

    @Override
    public byte[] serialize(Object model) {
        return (byte[])model;
    }

    @Override
    public Object deserialize(byte[] data) {
        return data;
    }

    @Override
    public HouseGroup unpack(Object model) {
        return Unpacker.unpack((byte[])model);
    }
}
