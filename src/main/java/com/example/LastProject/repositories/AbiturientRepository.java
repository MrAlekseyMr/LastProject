package com.example.LastProject.repositories;

import com.example.LastProject.models.Abiturient;
import org.springframework.data.repository.CrudRepository;

public interface AbiturientRepository extends CrudRepository<Abiturient, Integer> {
    Iterable<Abiturient> findByFamiliaContainsOrImaContainsOrSnilsContains(String fam, String ima, String snils);
    Abiturient findBySnils(String snils);
    Abiturient findBySnilsAndAndIdNot(String snils, Integer id);
    Abiturient findBySeriapasportaAndNomerpasporta(String seria, String nomer);
    Abiturient findBySeriapasportaAndNomerpasportaAndIdNot(String seria, String nomer,Integer id);
}