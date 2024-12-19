package com.josetesan.poc.springcustomer.ia;

import com.josetesan.poc.springcustomer.model.Customer;
import java.util.List;
import java.util.function.Function;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class AiConfiguration {

  @Bean
  public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
  }

  // The @Description annotation helps the model understand when to call the function
  @Bean
  @Description("List all the customers the shop has")
  public Function<CustomerRequest, CustomersResponse> listCustomers(AIDataProvider aiDataProvider) {
    return request -> aiDataProvider.getAllCustomers();
  }

  public record CustomerRequest(Customer customer) {}
  ;

  public record CustomersResponse(List<Customer> customers) {}
  ;
}
