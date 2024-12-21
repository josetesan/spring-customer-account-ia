package com.josetesan.poc.springcustomer.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTO {
  String name;
  Double price;

  @JsonCreator
  public static ProductDTO of(
      @JsonProperty("name") String name, @JsonProperty("price") String price) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.name = name;
    productDTO.price = Double.valueOf(price);
    return productDTO;
  }
}
