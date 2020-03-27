package com.sequsoft.bufmark.flatbuffers;

import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.BufferRunner;
import com.sequsoft.bufmark.model.HouseGroup;

import java.nio.ByteBuffer;

public class FlatbuffersRunner implements BufferRunner {
    @Override
    public Object constructModel(HouseGroup commonModel) {
        ByteBuffer buf = Utils.createHouseGroupBuffer(commonModel);

        return buf;
    }

    @Override
    public String modelToString(Object model) {
        return new String(((ByteBuffer)model).array());
    }

    @Override
    public void interrogateModel(Object model) {
        ByteBuffer buffer = (ByteBuffer)model;

        F_HouseGroup houseGroup = F_HouseGroup.getRootAsF_HouseGroup(buffer);
        F_Person.Vector organisers = houseGroup.organisersVector();

        int people = organisers.length();

        F_House.Vector houses = houseGroup.housesVector();

        for (int i = 0; i < houses.length(); i++) {
            people += houses.get(i).occupantsVector().length();
        }

        System.out.println("FLATBUFFERS PEOPLE COUNT = " + people);
    }
}
