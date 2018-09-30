package com.bolsaideas.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsaideas.springboot.app.model.dao.IClienteDao;
import com.bolsaideas.springboot.app.model.entity.Cliente;

@Controller
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@RequestMapping(value = "/lista", method = RequestMethod.GET)
	public String lista(Model modelo) {
		modelo.addAttribute("titulo", "Listado de Clientes");
		modelo.addAttribute("clientes", clienteDao.findAll());
		return "lista";
	}

	@RequestMapping(value = "/formulario")
	public String crear(Map<String, Object> modelo) {
		Cliente cliente = new Cliente();
		modelo.put("cliente", cliente);
		modelo.put("titulo", "Formulario de Cliente");
		return "formulario";
	}

	@RequestMapping(value = "/formulario/guardar", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult resultado, Model modelo) {
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Formulario de Cliente");
			return "/formulario";
		}

		clienteDao.save(cliente);
		return "redirect:/clientes/lista";
	}
}
