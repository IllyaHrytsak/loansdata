package com.intersog.loansdata.service;

import com.intersog.loansdata.entity.Credit;
import com.intersog.loansdata.repository.CreditRepository;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Transactional
    @Override
    public List<Credit> findAllCredits() {
        return creditRepository.findAll();
    }

    @Override
    public List<Credit> createCreditsFromCsvData(String csv) {
        List<Credit> credits = new ArrayList<>();
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);
        csvReader.setFieldSeparator(',');
        try {
            CsvContainer csvContainer = csvReader.read(new StringReader(csv));
            for (int i = 0; i < csvContainer.getRowCount(); i++) {
                CsvRow row = csvContainer.getRow(i);
                Credit credit = transferCsvRowToCredit(row);
                credits.add(credit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credits;
    }

    @Override
    @Transactional
    public List<Credit> saveAll(List<Credit> credits) {
        return creditRepository.saveAll(credits);
    }

    @Override
    public List<Credit> findFilteredData(String requestPath) {
        String yearOfBirthFromUrlPath = null;
        String genderFromUrlPath = null;
        List<String> loanStateFromUrlPath = new ArrayList<>();
        String filters;
        if (requestPath.contains("db"))
            filters = requestPath.substring(requestPath.indexOf("/db/") + 4);
        else if (requestPath.contains("file"))
            filters = requestPath.substring(requestPath.indexOf("/file/") + 6);
        else
            filters = requestPath;
        String[] values = filters.split("/");
        for (int i = 0; i < values.length; i++) {
            if (i % 2 != 0)
                continue;
            String str = values[i];
            if (str.equalsIgnoreCase("gender")) {
                genderFromUrlPath = values[i + 1];
            } else if (str.equalsIgnoreCase("state")) {
                loanStateFromUrlPath.add(values[i + 1]);
            } else if (str.equalsIgnoreCase("year"))
                yearOfBirthFromUrlPath = values[i + 1];

        }
        String yearOfBirth = Objects.nonNull(yearOfBirthFromUrlPath) ? "%/" + yearOfBirthFromUrlPath : null;
        Integer gender = null;
        if (Objects.nonNull(genderFromUrlPath)) {
            if (genderFromUrlPath.equalsIgnoreCase("male"))
                gender = 0;
            if (genderFromUrlPath.equalsIgnoreCase("female"))
                gender = 1;
        }
        List<String> loanStates = loanStateFromUrlPath.stream()
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1))
                .collect(Collectors.toList());
        return executeAppropriateJpaMethod(yearOfBirth, gender, loanStates);
    }

    private List<Credit> executeAppropriateJpaMethod(String yearOfBirth, Integer gender, List<String> loanState) {
        if (Objects.isNull(yearOfBirth) && Objects.isNull(gender) && !loanState.isEmpty())
            return creditRepository.findByStatusIn(loanState);
        else if (Objects.isNull(yearOfBirth) && Objects.nonNull(gender) && loanState.isEmpty())
            return creditRepository.findByGender(gender);
        else if (Objects.nonNull(yearOfBirth) && Objects.isNull(gender) && loanState.isEmpty())
            return creditRepository.findByDateOfBirthLike(yearOfBirth);
        else if (Objects.isNull(yearOfBirth) && Objects.nonNull(gender) && !loanState.isEmpty())
            return creditRepository.findByGenderAndStatusIn(gender, loanState);
        else if (Objects.nonNull(yearOfBirth) && Objects.nonNull(gender) && loanState.isEmpty())
            return creditRepository.findByGenderAndDateOfBirthLike(gender, yearOfBirth);
        else if (Objects.nonNull(yearOfBirth) && Objects.isNull(gender) && !loanState.isEmpty())
            return creditRepository.findByStatusInAndDateOfBirthLike(loanState, yearOfBirth);
        else if (Objects.nonNull(yearOfBirth) && Objects.nonNull(gender) && !loanState.isEmpty())
            return creditRepository.findByGenderAndStatusInAndDateOfBirthLike(gender, loanState, yearOfBirth);
        else
            return new ArrayList<>();
    }

    private Credit transferCsvRowToCredit(CsvRow csvRow) {
        return Credit.builder()
                .id(parseIntegerFromString(csvRow.getField("ID")))
                .status(csvRow.getField("Status"))
                .amount(parseDoubleFromString(csvRow.getField("Amount")))
                .applicationSignedHour(parseIntegerFromString(csvRow.getField("ApplicationSignedHour")))
                .applicationSignedWeekday(parseIntegerFromString(csvRow.getField("ApplicationSignedWeekday")))
                .city(csvRow.getField("City"))
                .country(csvRow.getField("Country"))
                .creditScoreEsEquifaxRisk(csvRow.getField("CreditScoreEsEquifaxRisk"))
                .dateOfBirth(csvRow.getField("DateOfBirth"))
                .debtToIncome(parseDoubleFromString(csvRow.getField("DebtToIncome")))
                .education(parseIntegerFromString(csvRow.getField("Education")))
                .employmentDurationCurrentEmployer(csvRow.getField("EmploymentDurationCurrentEmployer"))
                .employmentPosition(csvRow.getField("EmploymentPosition"))
                .employmentStatus(parseIntegerFromString(csvRow.getField("EmploymentStatus")))
                .existingLiabilities(parseIntegerFromString(csvRow.getField("ExistingLiabilities")))
                .gender(parseIntegerFromString(csvRow.getField("Gender")))
                .homeOwnershipType(parseIntegerFromString(csvRow.getField("HomeOwnershipType")))
                .incomeFromPrincipalEmployer(parseIntegerFromString(csvRow.getField("IncomeFromPrincipalEmployer")))
                .incomeTotal(parseIntegerFromString(csvRow.getField("IncomeTotal")))
                .interestRate(parseDoubleFromString(csvRow.getField("Interest rate (APR)")))
                .loanDate(csvRow.getField("LoanDate"))
                .loanDuration(parseIntegerFromString(csvRow.getField("LoanDuration")))
                .maritalStatus(parseIntegerFromString(csvRow.getField("MaritalStatus")))
                .newCreditCustomer(Boolean.parseBoolean(csvRow.getField("NewCreditCustomer")))
                .noOfPreviousLoansBeforeLoan(parseIntegerFromString(csvRow.getField("NoOfPreviousLoansBeforeLoan")))
                .occupationArea(parseIntegerFromString(csvRow.getField("OccupationArea")))
                .useOfLoan(parseIntegerFromString(csvRow.getField("UseOfLoan")))
                .verificationType(parseIntegerFromString(csvRow.getField("VerificationType")))
                .workExperience(csvRow.getField("WorkExperience"))
                .previousScore(parseDoubleFromString(csvRow.getField("PreviousScore")))
                .defaulted(Boolean.parseBoolean(csvRow.getField("Defaulted")))
                .defaultDate(csvRow.getField("DefaultDate"))
                .build();
    }

    private Double parseDoubleFromString(String doubleString) {
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseIntegerFromString(String doubleString) {
        try {
            return Integer.parseInt(doubleString);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}
