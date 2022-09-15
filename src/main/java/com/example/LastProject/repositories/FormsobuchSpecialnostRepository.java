package com.example.LastProject.repositories;

import com.example.LastProject.models.FormsobuchSpecialnost;
import com.example.LastProject.models.Plannabor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormsobuchSpecialnostRepository extends CrudRepository<FormsobuchSpecialnost, Integer> {
    FormsobuchSpecialnost findByAbiturientid_Id(Integer id);
    List<FormsobuchSpecialnost> findByPlannaboraid(Plannabor id);
}