package com.progweb.Progweb.Repository;

import com.progweb.Progweb.Models.Users;

public interface YourCustomRepository<T, S> {
    public Users findByEmail(String email);
    public Boolean existsByEmail(String email);
    public  Users findByid(Integer id);
}
