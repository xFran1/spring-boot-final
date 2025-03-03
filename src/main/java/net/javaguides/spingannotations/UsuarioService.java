package net.javaguides.spingannotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
    
    public void registrarUsuario(Usuario usuario) {
       
        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }
   
    public boolean verificarPassword(String email, String password) {
        // Busca el usuario por el email
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario != null) {
            // Si el usuario existe, compara la contraseña ingresada con el hash almacenado
            return passwordEncoder.matches(password, usuario.getPassword());
        }
        return false; // Usuario no encontrado
    }
    public UsuarioDTO devolverId(String email, String password) {
        // Busca el usuario por el email
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            // Si el usuario existe, compara la contraseña ingresada con el hash almacenado
            return new UsuarioDTO(usuario.getId(), usuario.getNombre());
 
        }
        return null; // Usuario no encontrado
    }

	
    
    
    
   



    
}