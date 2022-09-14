package com.example.LastProject.repositories;

import com.example.LastProject.models.Okrug;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OkrugRepository extends CrudRepository<Okrug, Integer> {
    List<Okrug> findByNameokrugContains(String nameokrug);
    Okrug findByNameokrug(String nameokrug);
    Okrug findByNameokrugAndIdNot(String nameokrug, Integer id);
}