package br.com.alura.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
public class ConsultaChatGPT {
    public static String obterInformacao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("sk-proj-4Zgd3YN2xRCLPpXFDjD5T3BlbkFJ1RBeROE810cLU2ofaW4z"));


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("me fale sobre o artista: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
