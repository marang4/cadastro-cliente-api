package br.com.senac.cadastro_clientes_api.utils;

import br.com.senac.cadastro_clientes_api.exceptions.RegistroNaoEncontrado;
import org.springframework.data.jpa.repository.JpaRepository;

public class ValidacoesUtil {
    public static void validarSeRegistroExiste(JpaRepository repository, Long id) throws RegistroNaoEncontrado {
        if (!repository.existsById(id)) {
            throw new RegistroNaoEncontrado("Registro nao encontrado");
        }
    }
}
