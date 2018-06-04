package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.model.CarsokSoundRecording;
import org.apache.ibatis.annotations.Param;

public interface SaasSoundRecordingMapper extends BaseMapper<CarsokSoundRecording> {
    public void deleteRecording(@Param("id") int id);

}