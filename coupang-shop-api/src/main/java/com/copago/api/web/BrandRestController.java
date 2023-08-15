package com.copago.api.web;

import com.copago.api.web.dto.response.BrandResponse;
import com.copago.api.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandRestController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getBrands(@RequestParam("category") String category) {
        return ResponseEntity.ok(brandService.getBrands(category));
    }
}
