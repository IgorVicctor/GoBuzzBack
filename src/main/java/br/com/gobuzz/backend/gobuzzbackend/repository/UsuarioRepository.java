package br.com.gobuzz.backend.gobuzzbackend.repository;

import br.com.gobuzz.backend.gobuzzbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    // Define a method to find the user ID by email
    @Query("SELECT u.id FROM Usuario u WHERE u.email = :email")
    Optional<Long> findIdByEmail(@Param("email") String email);

}

