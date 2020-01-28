package com.alexandreseneviratne.mareu.di;

import com.alexandreseneviratne.mareu.service.FakeMeetingApiService;

/**
 * Created by Alexandre SENEVIRATNE on 1/27/2020.
 */
public class DI {

    private static FakeMeetingApiService service = new FakeMeetingApiService();

    public static FakeMeetingApiService getService() {
        return service;
    }

    //TODO: Make a getService() for tests
}
