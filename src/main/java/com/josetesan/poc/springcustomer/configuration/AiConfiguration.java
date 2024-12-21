package com.josetesan.poc.springcustomer.configuration;

import com.josetesan.poc.springcustomer.controllers.dtos.CustomerDTO;
import com.josetesan.poc.springcustomer.controllers.dtos.ProductDTO;
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

  @Bean
  @Description("Retrieve all the products the shop has")
  public Function<ProductRequest, ProductsResponse> listProducts(AIDataProvider aiDataProvider) {
    return productRequest -> aiDataProvider.getAllProducts();
  }

  @Bean
  @Description("Create a Product")
  public Function<ProductRequest, ProductResponse> createProduct(AIDataProvider aiDataProvider) {
    return aiDataProvider::createProduct;
  }

  public record CustomerRequest(CustomerDTO customer) {}
  ;

  public record CustomersResponse(List<CustomerDTO> customers) {}
  ;

  public record ProductRequest(ProductDTO product) {}

  public record ProductResponse(ProductDTO product) {}

  public record ProductsResponse(List<ProductDTO> products) {}
  ;
}
