package com.bolsaideas.springboot.app.model.dao;

import java.util.List;

import com.bolsaideas.springboot.app.model.entity.Cliente;

public interface IClienteDao {
	public List<Cliente> findAll();
}
