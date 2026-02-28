package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.ResAlarmDto;
import com.pikcurchu.pikcur.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "alarm api", description = "알람 API")
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(summary = "알람 조회", description = "알람 조회 API")
    @GetMapping
    public List<ResAlarmDto> selectAlarmList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return alarmService.selectAlarmList(memberNo);
    }
}
