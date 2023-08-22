package org.springframework.samples.ai.azure.evaluation;

import org.junit.jupiter.api.Test;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.evaluation.AbstractEvaluationTest;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class EvaluationTest extends AbstractEvaluationTest {

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Test
    void roleTest() {
        String request = "Tell me about 3 famous pirates from the Golden Age of Piracy and why they did.";
        String name = "Bob";
        String voice = "pirate";
        UserMessage userMessage = new UserMessage(request);
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        AiResponse response = openAiClient.generate(prompt);
        evaluateQuestionAndAnswer(request, response, false);
    }
}
