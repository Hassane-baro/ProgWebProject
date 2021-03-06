package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Votes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface VotesRepository extends CrudRepository<Votes, Integer> {
    @Transactional
    @Modifying
    @Query(value= "delete from votes v WHERE v.sondage_fk = ?1 AND v.user_fk = ?2",nativeQuery = true)
    void DeleteVote(int sondage_fk, int user_fk);
}
