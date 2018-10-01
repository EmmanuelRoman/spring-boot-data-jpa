package com.bolsaideas.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.app.model.dao.IClienteDao;
import com.bolsaideas.springboot.app.model.entity.Cliente;

@Controller
@RequestMapping(value = "/clientes")
@SessionAttributes("cliente")
public class ClienteController {

	private static final String TITULO = "titulo";
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@RequestMapping(value = "/lista", method = RequestMethod.GET)
	public String lista(Model modelo) {
		modelo.addAttribute(TITULO, "Listado de Clientes");
		modelo.addAttribute("clientes", clienteDao.findAll());
		return "lista";
	}

	@RequestMapping(value = "/formulario")
	public String crear(Map<String, Object> modelo) {
		Cliente cliente = new Cliente();
		modelo.put("cliente", cliente);
		modelo.put(TITULO, "Formulario de Cliente");
		return "formulario";
	}

	@RequestMapping(value = "/formulario/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> modelo) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteDao.findOne(id);
		} else {
			return "redirect:/clientes/lista";
		}
		modelo.put("cliente", cliente);
		modelo.put(TITULO, "Editar Cliente");
		return "formulario";
	}

	@RequestMapping(value = "/formulario/guardar", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult resultado, Model modelo, SessionStatus status) {
		if (resultado.hasErrors()) {
			modelo.addAttribute(TITULO, "Formulario de Cliente");
			return "/formulario";
		}

		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:/clientes/lista";
	}
}
