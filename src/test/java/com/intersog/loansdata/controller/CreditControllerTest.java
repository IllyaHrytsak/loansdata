package com.intersog.loansdata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersog.loansdata.config.ControllerConfig;
import com.intersog.loansdata.entity.Credit;
import com.intersog.loansdata.service.CreditService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ControllerConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public class CreditControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;
    @InjectMocks
    private CreditController creditController;
    @Mock
    private CreditService creditService;

    private Credit expectedCredit;

    private String expectedCreditAsJson;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(creditController).build();
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
            expectedCreditAsJson = mapper.writeValueAsString(Collections.singletonList(expectedCredit));
    }

    @Test
    public void shouldGetAllCredits() throws Exception {
        when(creditService.findAllCredits())
                .thenReturn(Collections.singletonList(expectedCredit));
        mockMvc.perform(get("/data/file"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCreditAsJson));
    }

    @Test
    public void shouldGetCreditsFilteredByYear() throws Exception {
        String requestPath = "/data/file/year/1975";
        when(creditService.findFilteredData(requestPath))
                .thenReturn(Collections.singletonList(expectedCredit));
        mockMvc.perform(get(requestPath))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCreditAsJson));
    }

    @Test
    public void shouldGetNoCreditsFilteredByAnotherYear() throws Exception {
        String requestPath = "/data/db/year/1999";
        when(creditService.findFilteredData(requestPath))
                .thenReturn(Collections.singletonList(expectedCredit));
        mockMvc.perform(get(requestPath))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCreditAsJson));
    }

    @Test
    public void shouldGetCreditsFilteredByGenderAndStateAndYear() throws Exception {
        String requestPath = "/data/db/year/1975/gender/male/state/current";
        when(creditService.findFilteredData(requestPath))
                .thenReturn(Collections.singletonList(expectedCredit));
        mockMvc.perform(get(requestPath))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCreditAsJson));
    }

}