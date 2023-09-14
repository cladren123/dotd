package com.dotd.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
@Slf4j
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job simpleJob;
    private final Job userJobV1;
    private final Job userJobV2;
    private final Job updateUserUsedMoneyJobV3;
    private final Job jobV4;
    private final Job jobV5;


    @PostMapping("/batchV5")
    public ResponseEntity<String> batchV5() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(jobV5, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }



    // batch version3
    @PostMapping("/batchV4")
    public ResponseEntity<String> batchV4() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(jobV4, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }

    // batch version3
    @PostMapping("/batchV3")
    public ResponseEntity<String> batchV3() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(updateUserUsedMoneyJobV3, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }


    // user 등급 업데이트 메소드
    @PostMapping("/set-tier-v2")
    public ResponseEntity<String> setTierVersionTwo() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(userJobV2, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }


    // user 등급 업데이트 메소드
    @PostMapping("/set-tier")
    public ResponseEntity<String> setTier() {
        try {
            /*
            jobParameters를 사용하는 이유는 Spring  Batch 작업의 인스턴스화와 실행을 구별하기 위해서다
            JobInstance는 job의 이름과 JobParameters의 이름을 조합하여 유일성이 결정된다.
            동일한 파라미터와 이름으로 Job을 실행하면 Spring Batch는 이미 실행되는지 확인하고 중복 실행을 방지한다.
            또, 만약 작업이 실패하면 다시 시작하는 기능을 제공하는데 동일한 파라미터를 사용해 재시작할 수 있다.
            parameter는 실행에 직접적인 영향을 주지 않습니다. 메타데이터 관리, 재시작 기능, JobInstance 식별 기능 등을 수행합니다.
             */

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(userJobV1, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }


    // batch 실행 테스트
    @PostMapping("/start")
    public ResponseEntity<String> startBatch() {

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(simpleJob, jobParameters);
            return ResponseEntity.ok("Batch job has been started.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start the batch job.");
        }
    }




}
