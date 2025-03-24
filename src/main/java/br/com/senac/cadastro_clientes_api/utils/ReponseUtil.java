package br.com.senac.cadastro_clientes_api.utils;

import java.util.HashMap;
import java.util.Map;

public class ReponseUtil {

    public static Map<String, String> response(String mensagem) {

        Map<String, String> retorno = new HashMap<>();

        retorno.put("mensagem", mensagem);
        return retorno;

    }



}
