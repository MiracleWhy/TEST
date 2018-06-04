package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsokSoundRecordingRequest;

public interface SaasSoundRecordingService {
    public BaseResult updateOrDeleteRecording (CarsokSoundRecordingRequest vo);
    public BaseResult insertRecording (CarsokSoundRecordingRequest vo);
    public BaseResult selectRecording (CarsokSoundRecordingRequest vo);


}
