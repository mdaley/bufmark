package com.sequsoft.bufmark.protobuf;

import static com.sequsoft.bufmark.protobuf.Utils.convertHouseGroup;
import static com.sequsoft.bufmark.utils.CommonUtils.randomHouseGroup;

import com.sequsoft.bufmark.BufferRunner;
import com.sequsoft.bufmark.model.HouseGroup;

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
}
