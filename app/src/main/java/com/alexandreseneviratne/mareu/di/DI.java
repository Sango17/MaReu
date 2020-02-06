package com.alexandreseneviratne.mareu.di;

import com.alexandreseneviratne.mareu.service.FakeMeetingApiService;
import com.alexandreseneviratne.mareu.service.MeetingApiService;

/**
 * Created by Alexandre SENEVIRATNE on 1/27/2020.
 */
public class DI {

    private static MeetingApiService service = new FakeMeetingApiService();

    public static MeetingApiService getService() {
        return service;
    }
}
