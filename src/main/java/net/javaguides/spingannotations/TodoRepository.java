package net.javaguides.spingannotations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todos, Long> {
	List<Todos> findByIdCliente(Integer idCliente);

	List<Todos> findByIdClienteAndAsuntoContainingIgnoreCase(Integer usuarioId, String asunto);

}
	