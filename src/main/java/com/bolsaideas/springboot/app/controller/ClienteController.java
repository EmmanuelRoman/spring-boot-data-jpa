package com.bolsaideas.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsaideas.springboot.app.model.dao.IClienteDao;

@Controller
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@RequestMapping(value = "/clientes/lista", method = RequestMethod.GET)
	public String lista(Model modelo) {
		modelo.addAttribute("titulo", "Listado de Clientes");
		modelo.addAttribute("clientes", clienteDao.findAll());
		return "lista";
	}
}
