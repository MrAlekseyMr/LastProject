package com.example.LastProject.repositories;

import com.example.LastProject.models.DisciplinsAbiturient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DisciplinsAbiturientRepository extends CrudRepository<DisciplinsAbiturient, Integer> {
    List<DisciplinsAbiturient> findByAbiturientid_Id(Integer id);
}