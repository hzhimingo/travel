package com.zhiming.travel.spot.controller;

import com.zhiming.travel.spot.service.SpotTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotTypeController {

    private final SpotTypeService spotTypeService;

    @Autowired
    public SpotTypeController(SpotTypeService spotTypeService) {
        this.spotTypeService = spotTypeService;
    }
}
