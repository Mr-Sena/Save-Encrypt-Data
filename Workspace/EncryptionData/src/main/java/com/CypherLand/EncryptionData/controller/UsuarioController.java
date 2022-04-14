package com.CypherLand.EncryptionData.controller;

import com.CypherLand.EncryptionData.model.UsuarioModel;
import com.CypherLand.EncryptionData.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listAll() {

        return ResponseEntity.ok(repository.findAll());

    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario) {

        usuario.setSenha(encoder.encode(usuario.getSenha()));

        return ResponseEntity.ok(repository.save(usuario));

    }

    //Continuar com a validação de senha.

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha (@RequestParam String login,
                                                 @RequestParam String password) {

        Optional<UsuarioModel> optUsuario = repository.findByLogin(login);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UsuarioModel usuario = optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getSenha());

        HttpStatus status = (valid) ?HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);

    }

}
