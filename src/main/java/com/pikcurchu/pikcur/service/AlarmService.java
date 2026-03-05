package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.ResAlarmDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmMapper alarmMapper;

    public List<ResAlarmDto> selectAlarmList(Integer memberNo) {
        return alarmMapper.selectAlarmList(memberNo);
    }

    @Transactional
    public void insertAlarm(Integer memberNo, String alarmTitle, String alarmContent, String imagePath) {
        int result = alarmMapper.insertAlarm(memberNo, alarmTitle, alarmContent, imagePath);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
