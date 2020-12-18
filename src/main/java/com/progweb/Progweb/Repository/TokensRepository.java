package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TokensRepository extends CrudRepository<Token,Integer> {

    @Transactional
    @Modifying
    @Query(value= "DELETE FROM token WHERE user_fk =?1",nativeQuery = true)
    void DeleteToken(int user_fk);

    @Query(value = "SELECT * FROM token t WHERE t.user_fk =?1",nativeQuery = true)
    public List<Token> userTokens(int idUser);


}
