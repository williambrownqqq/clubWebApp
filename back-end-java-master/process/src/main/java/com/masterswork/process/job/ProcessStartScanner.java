package com.masterswork.process.job;

import com.masterswork.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessStartScanner {

    private final ProcessService processService;

    @Scheduled(fixedDelay = 1000)
    public void scanProcessStartRecords() {
        processService.startScheduledProcesses();
    }
}
