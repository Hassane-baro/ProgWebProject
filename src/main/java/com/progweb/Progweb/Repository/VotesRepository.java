package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Votes;
import org.springframework.data.repository.CrudRepository;

public interface VotesRepository extends CrudRepository<Votes, Integer> {
}
