package com.sequsoft.bufmark.protobuf;

import static com.sequsoft.bufmark.protobuf.Packer.convertHouseGroup;

import com.sequsoft.bufmark.BufferRunner;
import com.sequsoft.bufmark.model.HouseGroup;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Optional;

public class ProtobufRunner implements BufferRunner {
    @Override
    public Object constructModel(HouseGroup commonModel) {
        P_HouseGroup model = convertHouseGroup(commonModel);

        return model;
    }

    @Override
    public String modelToString(Object model) {
        return model.toString();
    }

    @Override
    public void interrogateModel(Object model) {
        // find out the number of people in the entire model
        P_HouseGroup houseGroup = (P_HouseGroup) model;

        int people = houseGroup.getOrganisersList().size();

        Optional<Integer> housePeople = houseGroup.getHousesList().stream().map(house -> {
            return house.getOccupantsList().size();
        }).reduce(Integer::sum);

        int totalPeople = people + housePeople.get();
        System.out.println("PROTOBUF PEOPLE COUNT = " + totalPeople);
    }

    @Override
    public byte[] serialize(Object model) {
        P_HouseGroup houseGroup = (P_HouseGroup)model;

        return houseGroup.toByteArray();
    }

    @Override
    public Object deserialize(byte[] data) {
        try {
            return P_HouseGroup.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HouseGroup unpack(Object model) {
        return Unpacker.unpack((P_HouseGroup) model);
    }
}
