package com.example.LastProject.repositories;

import antlr.collections.List;
import com.example.LastProject.models.Formsobuch;
import com.example.LastProject.models.Plannabor;
import com.example.LastProject.models.Specialnosti;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlannaborRepository extends CrudRepository<Plannabor, Integer> {
    Plannabor findByFormsobucidEqualsAndAndSpecialnostidEquals(Integer idform, Integer idspec);
    Plannabor findByFormsobucid(Integer id);

    @Query("SELECT u FROM Plannabor u WHERE u.specialnostid = ?1 AND u.formsobucid =?2")
    Plannabor findByspecforms(Specialnosti specid, Formsobuch formsobuchid);

/*    @Query(value = "SELECT u FROM Plannabor u WHERE u.specialnostid = ?1 AND u.formsobucid =?2 AND u.id != ?3",nativeQuery = true)*/
    Plannabor findBySpecialnostidAndFormsobucidAndAndIdNot(Specialnosti specid, Formsobuch formsobuchid, Integer id);
}