package net.javaguides.spingannotations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class HomeController {
	 @Autowired
	    private UsuarioService usuarioService;
	 
	 @Autowired
	    private TodoService todoService;
	 
	@GetMapping({"","/","home"})
	public String home(HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();  // Destruye la sesión
		    }
		return "registro";
	}
	@GetMapping({"/inicio"})
	public String inicio(HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();  // Destruye la sesión
		    }
		return "inicio";
	}
	@GetMapping({"/login"})
	public String login(HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();  // Destruye la sesión
		    }
		return "inicio";
	}
	@GetMapping({"/registro"})
	public String registro(HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();  // Destruye la sesión
		    }
		return "registro";
	}
	
	@PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model,HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();  // Destruye la sesión
		    }
		// Verificar si el correo ya está en uso
        if (usuarioService.emailExiste(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro"; // Regresa a la vista con el mensaje de error
        }
		// Registrar el usuario si el correo no existe
        usuarioService.registrarUsuario(usuario);
        return "inicio"; // Redirigir a la página de login después del registro exitoso
       
    }
	
	@PostMapping("/iniciar")
    public String iniciarUsuario(@ModelAttribute Usuario usuario, Model model,HttpSession session) {
		 Integer usuarioId = (Integer) session.getAttribute("usuarioId");
         UsuarioDTO usuarioDTO = usuarioService.devolverId(usuario.getEmail(), usuario.getPassword());

		 if(usuarioId == null) {
			// Verificar si el correo ya está en uso

		        if (usuarioDTO!=null) {
		        		
		            
		   		

		            session.setAttribute("usuarioNombre", usuarioDTO.getNombre());
		        	session.setAttribute("usuarioId", usuarioDTO.getId());
			        model.addAttribute("usuarioId", usuarioDTO.getId());
			        model.addAttribute("usuarioNombre",  usuarioDTO.getNombre());


			        
			        List<Todos> todos = todoService.findByIdCliente(usuarioDTO.getId()); // Obtiene todos los registros
			        model.addAttribute("todos", todos);
		        	
		        	return "index";
		        }
		        model.addAttribute("error", "Los datos son incorrectos");

		        return "inicio"; // Regresa a la vista con el mensaje de error
		 }else {
		        model.addAttribute("usuarioNombre", usuarioDTO.getNombre());
		        
		        List<Todos> todos = todoService.findByIdCliente(usuarioId); // Obtiene todos los registros
		        model.addAttribute("todos", todos);
		        
	        	return "index";

		 }
		
       
    }
	@GetMapping("/iniciar")
    public String iniciarUsuarioa(@ModelAttribute Usuario usuario,@RequestParam(name = "buscar", required = false) String buscar,  Model model,HttpSession session) {
		 Integer usuarioId = (Integer) session.getAttribute("usuarioId");
         UsuarioDTO usuarioDTO = usuarioService.devolverId(usuario.getEmail(), usuario.getPassword());

         List<Todos> todos = todoService.findByIdCliente(usuarioId); // Obtiene todos los registros
	      
         if (buscar != null && !buscar.isEmpty()) {
        	 todos = todoService.buscarPorAsunto(usuarioId, buscar); // Busca por asunto
         } else {
             // Si no hay búsqueda, muestra todas las tareas
        	 todos = todoService.findByIdCliente(usuarioId);
         }
         model.addAttribute("todos", todos);
	        
	        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
	        model.addAttribute("usuarioNombre",  usuarioNombre);
	        Boolean estado = (Boolean) session.getAttribute("activo");
	        if(estado!=null) {
	        	if(estado) {
			        model.addAttribute("success",  "Se ha completado la tarea");

		        }else {
			        model.addAttribute("error",  "Se ha desmarcado la tarea");

		        }
	        }
	        
     	return "index";

     	
    }
	@PostMapping("/todos/{id}/toggle")
	public String toggleTodo(@PathVariable Long id,HttpSession session) {
	    todoService.toggle(id);
	    Boolean estado = todoService.estado(id);
    	session.setAttribute("activo", estado);

        return "redirect:/iniciar";	   
	}
	
	
	 @PostMapping("/introducir")
	    public String crearTodo(@ModelAttribute Todos todo, Model model,HttpSession session) {
		 Integer usuarioId = (Integer) session.getAttribute("usuarioId");
		 	todo.setCompletado(false);
		 	todo.setFechaCreacion(LocalDateTime.now());
		 	todo.setIdCliente(usuarioId);
	        todoService.registrarMensaje(todo);
	        
	        List<Todos> todos = todoService.findByIdCliente(usuarioId); // Obtiene todos los registros
	        model.addAttribute("todos", todos);
	        
	        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
	        model.addAttribute("usuarioNombre",  usuarioNombre);
	        return "index"; // Redirige a la página principal o donde necesites
	    }
	 
	 @GetMapping("/todos/{id}/editar")
	 public String editarTodo(@PathVariable Long id, Model model,HttpSession session) {
	     Todos todo = todoService.devolverTodos(id);
	     model.addAttribute("todo", todo);
	     return "editar-todo"; // Nombre del HTML de edición
	 }
	 
	 @PostMapping("/todos/{id}/actualizar")
	 public String actualizarTodo(@PathVariable Long id, 
	                              @RequestParam String asunto,
	                              @RequestParam String mensaje,
	                              @RequestParam(required = false) boolean completado,HttpSession session) {
		 session.removeAttribute("activo");

		 try {
	         Todos todo = todoService.devolverTodos(id);
	         todo.setAsunto(asunto);
	         todo.setMensaje(mensaje);
	         todo.setCompletado(completado);
	         todoService.guardar(todo);

	         return "redirect:/iniciar";	   
	     } catch (Exception e) {
	         return "index";
	     }
	 }
	 
	 @PostMapping("/todos/{id}/eliminar")
	 public String eliminarTodo(@PathVariable Long id, RedirectAttributes redirectAttributes,HttpSession session) {
	     try {
	    	 session.removeAttribute("activo");

	         todoService.eliminarPorId(id);
	         redirectAttributes.addFlashAttribute("message", "Tarea eliminada exitosamente.");
	         return "redirect:/iniciar"; // Redirige a la lista de tareas
	     } catch (Exception e) {
	         redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar la tarea.");
	         return "redirect:/index"; // Redirige a la lista de tareas
	     }
	 }
	 
	 @Controller
	 public class UsuarioController {

	     @PostMapping("/cerrar-sesion")
	     public String cerrarSesion(HttpSession session) {
	         // Elimina los atributos de la sesión
	         session.invalidate();  // Esto elimina todos los atributos de la sesión

	         // Redirige al usuario a la página de login o la página principal
	         return "redirect:/login";  // Cambia esto a la URL que desees
	     }
	 }
	 
	
	
	
	

	
	
}
