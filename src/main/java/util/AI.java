import java.util.HashMap;
import java.util.Map;

import ai.openai.OpenAI;
import ai.openai.prompt.CompletionRequest;
import ai.openai.prompt.CompletionResponse;

public class AI {
        OpenAI.apiKey = System.getenv("OPENAI_API_KEY");

        CompletionRequest request = new CompletionRequest();
        request.setModel("text-davinci-003");
        request.setPrompt("The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\n\nHuman: Hello, who are you?\nAI: I am an AI created by OpenAI. How can I help you today?\nHuman: I'd like to cancel my subscription.\nAI:");
        request.setTemperature(0.9);
        request.setMaxTokens(150);
        request.setTopP(1);
        request.setFrequencyPenalty(0.0);
        request.setPresencePenalty(0.6);
        request.setStop(new String[] { " Human:", " AI:" });

        CompletionResponse response = OpenAI.getCompletion(request);
        System.out.println(response.getChoices().get(0).getText());
}
