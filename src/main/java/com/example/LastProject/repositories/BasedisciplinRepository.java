package com.example.LastProject.repositories;

import com.example.LastProject.models.Basedisciplin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasedisciplinRepository extends CrudRepository<Basedisciplin, Integer> {
    List<Basedisciplin> findByNamedisciplinsContains(String name);
    Basedisciplin findByNamedisciplins(String name);
    Basedisciplin findByNamedisciplinsAndIdNot(String name, Integer id);
}