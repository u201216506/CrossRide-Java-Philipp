/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.dto;

import java.time.LocalDateTime;

/**
 *
 * @author PhILiPp
 */
public class DriveRiderDTO {
    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long distance;
    PersonDTO driver;
    PersonDTO rider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public PersonDTO getDriver() {
        return driver;
    }

    public void setDriver(PersonDTO driver) {
        this.driver = driver;
    }

    public PersonDTO getRider() {
        return rider;
    }

    public void setRider(PersonDTO rider) {
        this.rider = rider;
    }
}
