package com.bolsaideas.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.app.model.entity.Cliente;
import com.bolsaideas.springboot.app.model.service.IClienteService;

@Controller
@RequestMapping(value = "/clientes")
@SessionAttributes("cliente")
public class ClienteController {

	private static final String TITULO = "titulo";
	private static final String REDIRECTLISTA = "redirect:/clientes/lista";

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/lista", method = RequestMethod.GET)
	public String lista(Model modelo) {
		modelo.addAttribute(TITULO, "Listado de Clientes");
		modelo.addAttribute("clientes", clienteService.findAll());
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
			cliente = clienteService.findOne(id);
		} else {
			return REDIRECTLISTA;
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

		clienteService.save(cliente);
		status.setComplete();
		return REDIRECTLISTA;
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if (id > 0) {
			clienteService.delete(id);
		}
		return REDIRECTLISTA;
	}

}
