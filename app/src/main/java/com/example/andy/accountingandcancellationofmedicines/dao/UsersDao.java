package com.example.andy.accountingandcancellationofmedicines.dao;

import com.example.andy.accountingandcancellationofmedicines.entity.UsersEntity;

import java.io.Serializable;

/**
 * Created by Andy on 28.11.16.
 */

public interface UsersDao extends Serializable{
    UsersEntity read(String login);
    long addUser(UsersEntity entity) throws Exception;
}
