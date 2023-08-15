package com.copago.api.web;

import com.copago.api.service.AdminProductService;
import com.copago.api.web.dto.request.AdminProductAddRequest;
import com.copago.api.web.dto.response.AdminProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductRestController {
    private final AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<Page<AdminProductResponse>> getProducts(@RequestParam("title") String title,
                                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return ResponseEntity.ok(adminProductService.getProducts(title, page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<AdminProductResponse> getProductDetail(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(adminProductService.getProduct(productId));
    }

    @PostMapping
    public ResponseEntity<AdminProductResponse> addProduct(@RequestBody AdminProductAddRequest request) {
        return ResponseEntity.ok(adminProductService.addProduct(request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        adminProductService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
