package com.example.LastProject.repositories;

import com.example.LastProject.models.Formsobuch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormsobuchRepository extends CrudRepository<Formsobuch, Integer> {
    List<Formsobuch> findByNameformobuchContains(String name);
    Formsobuch findByNameformobuch(String name);
    Formsobuch findByNameformobuchAndAndIdNot(String name, Integer id);
}