package com.bolsaideas.springboot.app.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.model.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
