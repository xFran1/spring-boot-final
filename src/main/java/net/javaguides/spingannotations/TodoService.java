package net.javaguides.spingannotations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
	@Autowired
    private TodoRepository todoRepository;

    public void registrarMensaje(Todos todo) {
        todoRepository.save(todo);
    }

	public List<Todos> findAll() {
		// TODO Auto-generated method stub
        return todoRepository.findAll(); // Recupera todos los registros de la BD
	}
	
	 public List<Todos> findByIdCliente(Integer idCliente) {
	        return todoRepository.findByIdCliente(idCliente);
	    }
	 
	 public void marcarComoCompletado(Long id) {
	        Todos todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
	        todo.setCompletado(true);
	        todoRepository.save(todo);
	    }

	public void toggle(Long id) {
        Todos todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        todo.setCompletado(!todo.getCompletado()); // Alternar entre true y false
        todoRepository.save(todo);
        
	}
	public boolean estado(Long id) {
        Todos todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        return todo.getCompletado();
	}
	
	public Todos devolverTodos(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
	}
	
	public void guardar(Todos todo) {
	    todoRepository.save(todo);
	}
	
	public void eliminarPorId(Long id) {
	    todoRepository.deleteById(id);
	}
	 // Método para buscar tareas por el asunto
    public List<Todos> buscarPorAsunto(Integer usuarioId, String asunto) {
        return todoRepository.findByIdClienteAndAsuntoContainingIgnoreCase(usuarioId, asunto);
    }
}
