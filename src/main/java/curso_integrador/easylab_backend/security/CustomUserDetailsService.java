package curso_integrador.easylab_backend.security;

import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de UserDetailsService que carga usuarios desde MySQL.
 * Spring Security utiliza este servicio para resolver el usuario
 * a partir del identificador (subject del JWT).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByIdentificador(identificador)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + identificador
                ));

        return new User(
                usuario.getIdentificador(),
                usuario.getPassword(),
                usuario.getActivo(),      // enabled
                true,                      // accountNonExpired
                true,                      // credentialsNonExpired
                true,                      // accountNonLocked
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()))
        );
    }
}
