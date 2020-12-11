package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Sondages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SondagesRepository extends CrudRepository<Sondages, Integer> {

    @Query(value = "SELECT * FROM sondages s WHERE s.id_sondage NOT IN (select sondage_fk from votes WHERE user_fk = ?1)",nativeQuery = true)
    public List<Sondages> AllSpecial( int idUser);

    @Query(value = "SELECT * FROM sondages s WHERE s.id_sondage IN (select sondage_fk from votes WHERE user_fk = ?1)",nativeQuery = true)
    public List<Sondages> AllSondageVoter( int idUser);


}
