package com.example.LastProject.repositories;

import com.example.LastProject.models.Specialnosti;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpecialnostiRepository extends CrudRepository<Specialnosti, Integer> {
    Specialnosti findByNamespecialnost(String name);
    List<Specialnosti> findByNamespecialnostContains(String name);
    Specialnosti findByNamespecialnostAndIdNot(String name, Integer id);
    Specialnosti findByKodpospo(String name);
    Specialnosti findByKodpospoAndIdNot(String name, Integer id);
}