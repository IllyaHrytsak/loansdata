package com.intersog.loansdata.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CREDIT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Credit {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "ID")
    private Integer id;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "APPLICATION_SIGNED_HOUR")
    private Integer applicationSignedHour;
    @Column(name = "APPLICATION_SIGNED_WEEKDAY")
    private Integer applicationSignedWeekday;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CREDIT_SCORE_ES_EQUIFAX_RISK")
    private String creditScoreEsEquifaxRisk;
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @Column(name = "DEBT_TO_INCOME")
    private Double debtToIncome;
    @Column(name = "EDUCATION")
    private Integer education;
    @Column(name = "EMPLOYMENT_DURATION_CURRENT_EMPLOYER")
    private String employmentDurationCurrentEmployer;
    @Column(name = "EMPLOYMENT_POSITION")
    private String employmentPosition;
    @Column(name = "EMPLOYMENT_STATUS")
    private Integer employmentStatus;
    @Column(name = "EXISTING_LIABILITIES")
    private Integer existingLiabilities;
    @Column(name = "GENDER")
    private Integer gender;
    @Column(name = "HOME_OWNERSHIP_TYPE")
    private Integer homeOwnershipType;
    @Column(name = "INCOME_FROM_PRINCIPAL_EMPLOYER")
    private Integer incomeFromPrincipalEmployer;
    @Column(name = "INCOME_TOTAL")
    private Integer incomeTotal;
    @Column(name = "INTEREST_RATE")
    private Double interestRate;
    @Column(name = "LOAN_DATE")
    private String loanDate;
    @Column(name = "LOAN_DURATION")
    private Integer loanDuration;
    @Column(name = "MARITAL_STATUS")
    private Integer maritalStatus;
    @Column(name = "NEW_CREDIT_CUSTOMER")
    private Boolean newCreditCustomer;
    @Column(name = "NO_OF_PREVIOUS_LOANS_BEFORE_LOAN")
    private Integer noOfPreviousLoansBeforeLoan;
    @Column(name = "OCCUPATION_AREA")
    private Integer occupationArea;
    @Column(name = "USE_OF_LOAN")
    private Integer useOfLoan;
    @Column(name = "VERIFICATION_TYPE")
    private Integer verificationType;
    @Column(name = "WORK_EXPERIENCE")
    private String workExperience;
    @Column(name = "PREVIOUS_SCORE")
    private Double previousScore;
    @Column(name = "DEFAULTED")
    private Boolean defaulted;
    @Column(name = "DEFAULT_DATE")
    private String defaultDate;

}
