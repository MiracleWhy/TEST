package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsokSoundRecordingRequest;
import com.uton.carsokApi.controller.request.FollowMessageRequest;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.CarsokSoundRecording;
import com.uton.carsokApi.service.SaasFollowMessageService;
import com.uton.carsokApi.service.SaasSoundRecordingService;
import com.uton.carsokApi.util.DozerMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaasSoundRecordingServiceImpl implements SaasSoundRecordingService {

    @Autowired
    private SaasSoundRecordingMapper saasSoundRecordingMapper;
    @Autowired
    private SaasFollowMessageService saasFollowMessageService;

    @Override
    public BaseResult updateOrDeleteRecording(CarsokSoundRecordingRequest vo) {
        //如果Enable=0，则代表是删除操作
        CarsokSoundRecording carsokSoundRecording = new CarsokSoundRecording();
        DozerMapperUtil.getInstance().map(vo,carsokSoundRecording);
        if (vo.getEnable()==2){
            saasSoundRecordingMapper.deleteRecording(vo.getId());
            return BaseResult.success();
        }
        //如果Enable=1，则代表是更新操作
        if(vo.getEnable()==1){
            carsokSoundRecording.setUpdateTime(new Date());
            saasSoundRecordingMapper.updateById(carsokSoundRecording);
            return BaseResult.success();
        }
        return BaseResult.fail("code","Enable参数错误，请传1或者2");
    }

    @Override
    public BaseResult insertRecording(CarsokSoundRecordingRequest vo) {
        //先将数据插入到carsokRecord表中
        FollowMessageRequest followMessageRequest = new FollowMessageRequest();
        followMessageRequest.setAccountId(vo.getAccountId());
        followMessageRequest.setChildId(vo.getChildId());
        followMessageRequest.setMessage(vo.getUrl());
        followMessageRequest.setModel(vo.getModule());
        followMessageRequest.setOutId(String.valueOf(vo.getBusiness_id()));
        followMessageRequest.setType("录音");
        followMessageRequest.setRecordTime(vo.getRecordTime());
        followMessageRequest.setSoundToWord(vo.getSoundToWord());
        saasFollowMessageService.saveFollowMessage(followMessageRequest);
        CarsokSoundRecording carsokSoundRecording = new CarsokSoundRecording();
        DozerMapperUtil.getInstance().map(vo,carsokSoundRecording);
        //先将数据插入到carsokSoundRecording表中
        carsokSoundRecording.setCreateTime(new Date());
        saasSoundRecordingMapper.insert(carsokSoundRecording);
        return BaseResult.success();
    }

    @Override
    public BaseResult selectRecording(CarsokSoundRecordingRequest vo) {
        CarsokSoundRecording carsokSoundRecording = new CarsokSoundRecording();
        DozerMapperUtil.getInstance().map(vo,carsokSoundRecording);
        Page<CarsokSoundRecording> page = new Page<CarsokSoundRecording>(vo.getPageNum(),vo.getPageSize());
        EntityWrapper<CarsokSoundRecording> ew = new EntityWrapper<CarsokSoundRecording>();
        List<CarsokSoundRecording> carsokSoundRecordingList = saasSoundRecordingMapper.selectPage(page,ew);
        return BaseResult.success(carsokSoundRecordingList);
    }
}
