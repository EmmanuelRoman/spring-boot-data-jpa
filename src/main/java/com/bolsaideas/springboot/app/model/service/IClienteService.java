package com.bolsaideas.springboot.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsaideas.springboot.app.model.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
}
