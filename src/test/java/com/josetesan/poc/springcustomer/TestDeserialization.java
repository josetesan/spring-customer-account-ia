package com.josetesan.poc.springcustomer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josetesan.poc.springcustomer.controllers.dtos.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestDeserialization {

  @Test
  void canDeserializeProduct() throws Exception {

    String PRODUCT =
        """
            {"name": "Coca-cola", "price": 200.0}
            """;
    ObjectMapper objectMapper = new ObjectMapper();
    var product = objectMapper.readValue(PRODUCT, ProductDTO.class);
    Assertions.assertEquals("Coca-cola", product.getName());
    Assertions.assertEquals(200.0d, product.getPrice());
  }
}
