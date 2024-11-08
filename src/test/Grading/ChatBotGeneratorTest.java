package Grading;

import org.junit.jupiter.api.Test;
import static org.junit.api.Assertions.*;


public class ChatBotGeneratorTest{
    @Test
    void testGenerateChatBotLLM_LLaMa() {
        assertEquals("LLaMa", ChatBotGenerator.generateChatBotLLM(1));
    }

    @Test
    void testGenerateChatBotLLM_Mistral7B() {
        assertEquals("Mistral7B", ChatBotGenerator.generateChatBotLLM(2));
    }

    @Test
    void testGenerateChatBotLLM_Bard() {
        assertEquals("Bard", ChatBotGenerator.generateChatBotLLM(3));
    }

    @Test
    void testGenerateChatBotLLM_Claude() {
        assertEquals("Claude", ChatBotGenerator.generateChatBotLLM(4));
    }

    @Test
    void testGenerateChatBotLLM_Solar() {
        assertEquals("Solar", ChatBotGenerator.generateChatBotLLM(5));
    }

    @Test
    void testGenerateChatBotLLM_Default() {
        assertEquals("ChatGPT-3.5", ChatBotGenerator.generateChatBotLLM(0)); // Invalid code
        assertEquals("ChatGPT-3.5", ChatBotGenerator.generateChatBotLLM(6)); // Invalid code
        assertEquals("ChatGPT-3.5", ChatBotGenerator.generateChatBotLLM(-1)); // Negative code
    }
}