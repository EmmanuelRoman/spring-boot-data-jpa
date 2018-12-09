package com.bolsaideas.springboot.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.model.entity.Cliente;
import com.bolsaideas.springboot.app.model.service.IClienteService;
import com.bolsaideas.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping(value = "/clientes")
@SessionAttributes("cliente")
public class ClienteController {

	private static final String TITULO = "titulo";
	private static final String CLIENTETITULO = "cliente";
	private static final String REDIRECTLISTA = "redirect:/clientes/lista";

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe!");
			return REDIRECTLISTA;
		}
		modelo.put(CLIENTETITULO, cliente);
		modelo.put(TITULO, "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}

	@GetMapping(value = "/lista")
	public String lista(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
		Pageable pageRequest = new PageRequest(page, 4);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/clientes/lista", clientes);
		modelo.addAttribute(TITULO, "Listado de Clientes");
		modelo.addAttribute("clientes", clientes);
		modelo.addAttribute("page", pageRender);
		return "lista";
	}

	@RequestMapping(value = "/formulario")
	public String crear(Map<String, Object> modelo) {
		Cliente cliente = new Cliente();
		modelo.put(CLIENTETITULO, cliente);
		modelo.put(TITULO, "Formulario de Cliente");
		return "formulario";
	}

	@GetMapping(value = "/formulario/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("warning", "El cliente no existe!");
				return REDIRECTLISTA;
			}
		} else {
			flash.addFlashAttribute("danger", "El ID del cliente no puede ser 0!");
			return REDIRECTLISTA;
		}
		modelo.put(CLIENTETITULO, cliente);
		modelo.put(TITULO, "Editar Cliente");
		return "formulario";
	}

	@PostMapping(value = "/formulario/guardar")
	public String guardar(@Valid Cliente cliente, BindingResult resultado, Model modelo,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (resultado.hasErrors()) {
			modelo.addAttribute(TITULO, "Formulario de Cliente");
			return "/formulario";
		}
		if (!foto.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Se subio correctamente " + foto.getOriginalFilename());
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			cliente.setFoto("");
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return REDIRECTLISTA;
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}
		return REDIRECTLISTA;
	}

}
