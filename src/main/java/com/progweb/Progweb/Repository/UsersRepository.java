package com.progweb.Progweb.Repository;
import com.progweb.Progweb.Models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users,Integer>, CustomRepositoryUsers<Users, String> {


}
