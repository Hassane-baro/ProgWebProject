package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Votes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends CrudRepository<Votes, Integer>, CustomRepositoryVotes<Votes,String> {
    @Modifying
    @Query(value= "delete from votes v WHERE v.sondage_fk = ?1 AND v.user_fk = ?2",nativeQuery = true)
    void DeleteVote(int idSondages,int idUser);
}
