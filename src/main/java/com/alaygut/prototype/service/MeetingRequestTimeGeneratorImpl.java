package com.alaygut.prototype.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MeetingRequestTimeGeneratorImpl implements MeetingRequestTimeGenerator {

    @Override
    public ArrayList<String> generateTimes(int beginning, int end, int interval) {
        ArrayList<String> timeList = new ArrayList<>();
        int hour, minute;

        for (hour = beginning; hour < end; hour++)
            for (minute  = 0; minute < 60; minute+=interval){
                String time = "";

                time = (hour < 10 ? "0" + hour + ":" :  time + hour + ":");

                time += (minute < 10 ? "0" + minute : minute);

                timeList.add(time);
            }
        timeList.add((end < 10 ? "0" + end : end) + ":00");
        return timeList;
    }
}
