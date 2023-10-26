package practica.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import practica.dao.ClienteDao;
import practica.javabean.Cliente;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteDao cdao;
	
	@GetMapping("/verDetalle/{id}")
	public String verCliente(@PathVariable("id") int idCliente, Model model){
		Cliente cliente = cdao.findBy(idCliente);
		if(cliente != null) {
			model.addAttribute("cliente", cliente);
			return "verDetalle";
		} else {
			model.addAttribute("mensaje", "NO existe ese cliente");
			return "forward:/";
		}
		
	}
	
	@GetMapping("/eliminiar/{id}")
	public String eliminarCliente(@PathVariable("id") int idCliente, Model model) {
		if(cdao.delete(idCliente)== 1) {
			model.addAttribute("Mensaje", "Cliente Borrado");
		} else {
			model.addAttribute("Mensaje", "Cliente no Borrado");
		}
		return "forward:/";
	}
	
	@GetMapping ("/alta")
	public String verFormAlta() {
		
		return "formAlta";
	}
	
	@PostMapping("/alta")
	public String procFormAlta(Cliente cliente, RedirectAttributes ratt) {
		cliente.setFechaAlta(new Date());
		if(cdao.insert(cliente)==1) {
			ratt.addFlashAttribute("mensaje", "Alta realizada");
			System.out.println(cliente);
		} else {
			ratt.addFlashAttribute("mensaje", "Alta no realizada");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public String editarCliente (@PathVariable("id") int idCliente, Model model) {
		Cliente cliente = cdao.findBy(idCliente);
		if (cliente != null) {
			model.addAttribute("cliente", cliente);
			return "formEdicion";
		}else {
			model.addAttribute("mensaje", "Cliente no existe");
			return "forward:/";
		}
	}
	
	@PostMapping("/editar/{id}")
	public String procEditarCliente ( Cliente cliente, @PathVariable("id") int idCliente, RedirectAttributes ratt) {
		cliente.setIdCliente(idCliente);
		if(cdao.updateOne(cliente)== 1) {
			ratt.addFlashAttribute("mensaje", "Modificación realizada");
		} else {
			ratt.addFlashAttribute("mensaje", "Modificación no realizada");

		}
		
		return "redirect:/";
	}
	
	
	
	
	
	@InitBinder
	public void initBinder (WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	

}
