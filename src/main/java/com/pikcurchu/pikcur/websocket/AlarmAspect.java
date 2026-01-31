package com.pikcurchu.pikcur.websocket;

import com.pikcurchu.pikcur.anotation.Alarm;
import com.pikcurchu.pikcur.dto.request.AlarmMessageDto;
import com.pikcurchu.pikcur.service.AlarmService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmAspect {
    private final AlarmService alarmService;
    private final SimpMessagingTemplate messagingTemplate;

    @AfterReturning("@annotation(alarmAnnotation)")
    public void sendAlarm(JoinPoint joinPoint, Alarm alarmAnnotation) {
        Object[] args = joinPoint.getArgs();
        Integer memberNo = null;

        for (Object arg : args) {
            if (arg instanceof Integer) {
                memberNo = (Integer) arg;
                break;
            } else if (arg instanceof HttpServletRequest) {
                memberNo = (Integer) ((HttpServletRequest) arg).getAttribute("memberNo");
                break;
            }
        }
        if (memberNo == null) {
            throw new IllegalArgumentException("memberNo를 찾을 수 없습니다. 메서드 파라미터를 확인하세요.");
        }

        String alarmTitle = alarmAnnotation.alarmTitle();
        String alarmContent = alarmAnnotation.alarmContent();
        String imagePath = null;

        // DB insert
        alarmService.insertAlarm(memberNo, alarmTitle, alarmContent, imagePath);

        // WebSocket push
        AlarmMessageDto message = new AlarmMessageDto(alarmTitle, alarmContent, imagePath);
        messagingTemplate.convertAndSend("/topic/alarm/" + memberNo, message);
    }
}
