package com.target.myRetail.controllers;

import com.target.myRetail.exceptions.ProductNotFoundException;
import com.target.myRetail.models.Product;
import com.target.myRetail.respository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private static String getProductName(long id){
        String url = "https://redsky.target.com/v2/pdp/tcin/" +
                String.valueOf(id) +"?excludes=taxonomy,price,promotion,bulk_ship," +
                "rating_and_review_reviews,rating_and_review_statistics," +
                "question_answer_statistics";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode> result = restTemplate.getForEntity(url, JsonNode.class);
        if (result.getStatusCode().value() == 200) {

            JsonNode productNode = result.getBody().get("product");
            String name = productNode.get("item").get("product_description")
                    .get("title").asText();

            return name;

        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Map<String, Object> getProduct(@PathVariable long id) {

        String name = getProductName(id);

        if (name == null || name ==""){
            throw new ProductNotFoundException(id);
        }

        Product product = productRepository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        } else {
            product.setName(name);
            Map<String, Object> productMap = new HashMap<String, Object>();
            productMap.put("message", "Product found!");
            productMap.put("Product", product);

            return productMap;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> updateProduct(@PathVariable long id, @RequestBody Product productInfo) {

        Product product = productRepository.save(productInfo);

        Map<String, Object> productMap = new HashMap<String, Object>();
        productMap.put("message", "Product Updated!");
        productMap.put("status", "1");
        productMap.put("product", product);

        return productMap;
    }
}