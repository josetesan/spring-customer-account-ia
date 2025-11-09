package com.josetesan.poc.springcustomer.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OllamaChat {

  // ChatModel is the primary interfaces for interacting with an LLM
  // it is a request/response interface that implements the ModelModel
  // interface. Make suer to visit the source code of the ChatModel and
  // checkout the interfaces in the core spring ai package.
  private final ChatClient chatClient;

  public OllamaChat(ChatClient.Builder builder, ChatMemory chatMemory, VectorStore vectorStore) {

    // @formatter:off
    this.chatClient =
        builder
            .defaultSystem(
                """
                          You are a friendly AI assistant designed to help with the management of a shop.
                          Your job is to answer questions about and to perform actions on the user's behalf, mainly around
                          products, customers and purchases.
                          You are required to answer an a professional manner. If you don't know the answer, politely tell the user
                          you don't know the answer, then ask the user a followup question to try and clarify the question they are asking.
                          If you do know the answer, provide the answer but do not provide any additional followup questions.
                          When dealing with products, if the user is unsure about the returned results, explain that there may be additional data that was not returned.
                          Only if the user is asking about the total number of all products, answer that there are a lot and ask for some additional criteria.
                          For customers or purchases - provide the correct data.
                          """)
            .defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build(),
                // // RAG advisor.
                // Chat memory helps us keep context when using the chatbot for up to 10 previous
                // messages.
                new SimpleLoggerAdvisor())
            .defaultTools("listProducts", "listCustomers", "createProduct", "createPurchase")
            .build();
  }

  @PostMapping("/chat")
  public String exchange(@RequestBody String query) {
    // All chatbot messages go through this endpoint and are passed to the LLM
    var text = query.replaceAll("'", "");
    return this.chatClient.prompt().user(u -> u.text(text)).call().content();
  }
}
