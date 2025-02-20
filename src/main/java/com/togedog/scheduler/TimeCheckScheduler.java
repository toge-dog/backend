package com.togedog.scheduler;

import com.togedog.matchingStandBy.service.MatchingStandByService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TimeCheckScheduler {
    private final MatchingStandByService service;

    // 매일 00:00 (자정)에 실행
    @Async
    @Scheduled(cron = "0 0 0 * * *")  // 초, 분, 시, 일, 월, 요일
    public void checkTime() {
        LocalDateTime now = LocalDateTime.now();

        service.changeStatusToTimeOut(now);

        System.out.println("스케줄 실행 - 현재 시간: " + now);
    }
}
