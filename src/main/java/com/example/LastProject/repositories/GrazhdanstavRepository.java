package com.example.LastProject.repositories;

import com.example.LastProject.models.Grazhdanstav;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GrazhdanstavRepository extends CrudRepository<Grazhdanstav, Integer> {
    List<Grazhdanstav> findByNamegrazhdanstvoContains(String namegrazhdanstvo);
    Grazhdanstav findByNamegrazhdanstvo(String namegrazhdanstvo);
    Grazhdanstav findByNamegrazhdanstvoAndIdNot(String namegrazhdanstvo, Integer id);
}