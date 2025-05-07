package br.com.senac.cadastro_clientes_api.exceptions;

public class NaoFoiPossivelAlterar extends RuntimeException {
    public NaoFoiPossivelAlterar(String message) {
        super(message);
    }
}
