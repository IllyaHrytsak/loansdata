package com.intersog.loansdata.service;

import com.intersog.loansdata.config.ServiceConfig;
import com.intersog.loansdata.entity.Credit;
import com.intersog.loansdata.repository.CreditRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles("test")
public class CreditServiceImplTest {

    private String csvData;
    private Credit expectedCredit;

    @Autowired
    private CreditRepository creditRepository;
    private CreditService creditService;

    @Before
    public void setUp() throws Exception {
        creditService = new CreditServiceImpl(creditRepository);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("init.csv").getFile());
        csvData = new String(Files.readAllBytes(file.toPath()));
        expectedCredit = Credit.builder()
                .id(1)
                .status("Current")
                .amount(5000.0)
                .applicationSignedHour(12)
                .applicationSignedWeekday(4)
                .city("AESPA")
                .country("EE")
                .creditScoreEsEquifaxRisk("")
                .dateOfBirth("21/11/1975")
                .debtToIncome(51.52)
                .education(4)
                .employmentDurationCurrentEmployer("UpTo5Years")
                .employmentPosition("Worker")
                .employmentStatus(3)
                .existingLiabilities(9)
                .gender(0)
                .homeOwnershipType(6)
                .incomeFromPrincipalEmployer(1000)
                .incomeTotal(1000)
                .interestRate(24.52)
                .loanDate("05/05/2015")
                .loanDuration(60)
                .maritalStatus(4)
                .newCreditCustomer(false)
                .noOfPreviousLoansBeforeLoan(1)
                .occupationArea(8)
                .useOfLoan(0)
                .verificationType(1)
                .workExperience("15To25Years")
                .previousScore(0.0957)
                .defaulted(false)
                .defaultDate("")
                .build();
    }

    @Test
    public void creationCreditsFromCsvDataTest() {
        Assert.assertEquals(Collections.singletonList(expectedCredit),
                creditService.createCreditsFromCsvData(csvData));
    }

    @Test
    public void findAllTestTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        assertEquals(Collections.singletonList(expectedCredit), creditService.findAllCredits());
    }


    @Test
    public void findCreditByYearTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/db/year/1975";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByGenderTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/file/gender/male";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByStateTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/db/state/current";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByYearAndStateTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/file/year/1975/state/current";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByYearAndGenderTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/db/year/1975/gender/male";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByGenderAndStateTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/file/gender/male/state/current";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByYearAndGenderAndStateTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/db/year/1975/gender/male/state/current";
        assertEquals(Collections.singletonList(expectedCredit), creditService.findFilteredData(requestPath));
    }

    @Test
    public void findCreditByAnotherYearTest() {
        creditRepository.saveAll(Collections.singletonList(expectedCredit));
        String requestPath = "/data/db/year/1999";
        assertEquals(Collections.emptyList(), creditService.findFilteredData(requestPath));
    }


}