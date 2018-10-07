package com.intersog.loansdata.service;

import com.intersog.loansdata.entity.Credit;

import java.util.List;

public interface CreditService {

    List<Credit> findAllCredits();

    List<Credit> createCreditsFromCsvData(String csv);

    List<Credit> saveAll(List<Credit> credits);

    List<Credit> findFilteredData(String requestPath);

}
