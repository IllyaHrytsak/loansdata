package com.intersog.loansdata.controller;

import com.intersog.loansdata.entity.Credit;
import com.intersog.loansdata.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;
    private final RestTemplate restTemplate;
    @Value("${download.url}")
    private String downloadCsvUrl;

    @GetMapping(value = {"/data/file", "/data/db"})
    public List<Credit> getAllCredits() {
        return creditService.findAllCredits();
    }

    @GetMapping(value = "/sync")
    public ResponseEntity<List<Credit>> createAllCredits() {
        String csvData = restTemplate.getForObject(downloadCsvUrl, String.class);
        List<Credit> credits = creditService.createCreditsFromCsvData(csvData);
        List<Credit> storedCredits = creditService.saveAll(credits);
        return new ResponseEntity<>(storedCredits, HttpStatus.OK);
    }

    @GetMapping(value = {"/data/db/**", "/data/file/**"})
    public ResponseEntity<List<Credit>> getFilteredCredits(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURI();
        List<Credit> filteredCredits = creditService.findFilteredData(path);
        return new ResponseEntity<>(filteredCredits, HttpStatus.OK);
    }

}
