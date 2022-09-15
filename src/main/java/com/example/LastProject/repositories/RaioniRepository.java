package com.example.LastProject.repositories;

import com.example.LastProject.models.Raioni;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RaioniRepository extends CrudRepository<Raioni, Integer> {
    List<Raioni> findByNameraionContains(String name);
    Raioni findByNameraion(String name);
    Raioni findByNameraionAndIdNot(String name, Integer id);
}