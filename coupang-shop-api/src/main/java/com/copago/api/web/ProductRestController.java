package com.copago.api.web;

import com.copago.api.service.ProductService;
import com.copago.api.enums.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/{category}")
    public ResponseEntity<?> getProducts(@PathVariable("category") String category,
                                         @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "sort", defaultValue = "SALE") SortType sort,
                                         @RequestParam(value = "brand") String brand,
                                         @RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "size") Integer size) {
        return ResponseEntity.ok(productService.getProducts(category, title, sort, brand, page, size));
    }
}
