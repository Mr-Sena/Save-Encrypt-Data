package com.CypherLand.EncryptionData.repository;

import com.CypherLand.EncryptionData.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    public Optional<UsuarioModel> findByLogin(String login);

}
