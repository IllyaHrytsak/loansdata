package com.intersog.loansdata.repository;

import com.intersog.loansdata.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    List<Credit> findByGender(Integer gender);

    List<Credit> findByStatusIn(List<String> status);

    List<Credit> findByDateOfBirthLike(String dateOfBirth);

    List<Credit> findByGenderAndStatusIn(Integer gender, List<String> status);

    List<Credit> findByGenderAndDateOfBirthLike(Integer gender, String dateOfBirth);

    List<Credit> findByStatusInAndDateOfBirthLike(List<String> status, String dateOfBirth);

    List<Credit> findByGenderAndStatusInAndDateOfBirthLike(Integer gender, List<String> status, String dateOfBirth);

}
