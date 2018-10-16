package com.bolsaideas.springboot.app.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.model.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
