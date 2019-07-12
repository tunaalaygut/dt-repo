package com.alaygut.prototype.service;

import java.util.ArrayList;

public interface MeetingRequestTimeGenerator {
    ArrayList<String> generateTimes(int beginning, int end, int interval);
}
