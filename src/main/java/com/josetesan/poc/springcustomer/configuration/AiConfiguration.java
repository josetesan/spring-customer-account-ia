package com.josetesan.poc.springcustomer.configuration;

import com.josetesan.poc.springcustomer.controllers.dtos.CustomerDTO;
import com.josetesan.poc.springcustomer.controllers.dtos.ProductDTO;
import com.josetesan.poc.springcustomer.controllers.dtos.PurchaseDTO;
import java.util.List;
import java.util.function.Function;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class AiConfiguration {

  @Bean
  public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
  }

  @Bean
  VectorStore vectorStore(EmbeddingModel embeddingModel) {
    return new SimpleVectorStore(embeddingModel);
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
  public Function<CreateProductRequest, ProductResponse> createProduct(
      AIDataProvider aiDataProvider) {
    return aiDataProvider::createProduct;
  }

  @Bean
  @Description("A customer purchases a product")
  public Function<CreatePurchaseRequest, CreatePurchaseResponse> createPurchase(
      AIDataProvider aiDataProvider) {
    return aiDataProvider::createPurchase;
  }

  public record CustomerRequest() {}
  ;

  public record CustomersResponse(List<CustomerDTO> customers) {}
  ;

  public record ProductRequest() {}

  public record CreateProductRequest(String name, Double price) {}

  public record ProductResponse(ProductDTO product) {}

  public record ProductsResponse(List<ProductDTO> products) {}
  ;

  public record CreatePurchaseRequest(String customerName, String productName,Integer amount) {}
  ;

  public record CreatePurchaseResponse(PurchaseDTO purchaseDTO) {}
  ;
}
