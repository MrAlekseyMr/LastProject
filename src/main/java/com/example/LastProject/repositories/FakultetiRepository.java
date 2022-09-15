package com.example.LastProject.repositories;

import com.example.LastProject.models.Fakulteti;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FakultetiRepository extends CrudRepository<Fakulteti, Integer> {
    Fakulteti findByNamefakultet(String name);
    List<Fakulteti> findByNamefakultetContains(String name);
    Fakulteti findByNamefakultetAndIdNot(String name, Integer id);
}