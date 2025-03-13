package com.rgbrain.brianbot.domain.brian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgbrain.brianbot.domain.clima.infrastructure.adivsor.model.reponse.ResponsePrevisaoUmidade;

public class AdvisorDados {

    private static ObjectMapper objectMapper = new ObjectMapper();
    

    public static String exemploResponsePrevisaoUmidade() {
        StringBuilder jsonString = exemploResponsePrevisao();
        jsonString.append("    \"humidities\": [\n");
        adicionarExemploResponseDadosTemporais(jsonString);

        return jsonString.toString();
    }


    public static ResponsePrevisaoUmidade exemploClasseResponsePrevisaoUmidade() {
        try {
            return objectMapper.readValue(AdvisorDados.exemploResponsePrevisaoUmidade(),
                    ResponsePrevisaoUmidade.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static List<String> datasParaTeste() {
        List<String> datasTeste = new ArrayList<>();
        var dataAtual = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var horaInicial = 6;
        var horaFinal = 20;

        for (int i = horaInicial; i <= horaFinal; i++) {
            LocalDateTime date = dataAtual.withHour(i).withMinute(0).withSecond(0);
            datasTeste.add(date.format(formatter));
        }

        return datasTeste;
    }

    private static int[] valoresParaTeste() {
        int[] valoresTeste = { 79, 71, 65, 60, 56, 53, 56, 60, 65, 71, 79, 85, 85, 79, 71 };
        return valoresTeste;
    }

    private static StringBuilder exemploResponsePrevisao() {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\n");
        jsonString.append("    \"id\": 6997,\n");
        jsonString.append("    \"name\": \"Londrina\",\n");
        jsonString.append("    \"state\": \"PR\",\n");
        jsonString.append("    \"country\": \"BR\",\n");

        return jsonString;
    }

    private static void adicionarExemploResponseDadosTemporais(StringBuilder jsonString) {
        List<String> datasTeste = datasParaTeste();

        int[] valoresTeste = valoresParaTeste();

        for (int i = 0; i < datasTeste.size(); i++) {
            jsonString.append("        {\n");
            jsonString.append("            \"date\": \"").append(datasTeste.get(i)).append("\",\n");
            jsonString.append("            \"value\": ").append(valoresTeste[i]).append("\n");
            jsonString.append("        }");
            if (i < datasTeste.size() - 1) {
                jsonString.append(",");
            }
            jsonString.append("\n");
        }

        jsonString.append("    ]\n");
        jsonString.append("}");
    }
}
