package com.example.TestTheRest.controller;

import exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {
    private int counter = 5;

    private List<Map<String, String>> products = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "Sneakers");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "Necklace");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "TV");
        }});
        add(new HashMap<String, String>() {{
            put("id", "4");
            put("text", "Headphones");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return products;
    }

    @GetMapping("{id}")
    public Map<String, String> findOne(@PathVariable String id) {
        return findProduct(id);
    }

    private Map<String, String> findProduct(@PathVariable String id) {
        return products.stream()
                .filter(product -> product.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> product) {
        product.put("id", String.valueOf(counter++));

        products.add(product);

        return product;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> product = findProduct(id);

        products.remove(product);
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> product) {
        Map<String, String> someProduct = findProduct(id);

        someProduct.putAll(product);
        someProduct.put("id", id);

        return someProduct;
    }
}
