package br.com.senac.cadastro_clientes_api.exceptions;

public class RegistroNaoEncontrado extends Exception{
    public RegistroNaoEncontrado(String mensagem){
        super(mensagem);
    }
}
