package com.copago.api.web;

import com.copago.api.service.AlarmProductService;
import com.copago.api.web.dto.request.AlarmAddRequest;
import com.copago.api.web.dto.request.AlarmModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarm")
public class AlarmRestController {
    private final AlarmProductService alarmProductService;

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getProducts(@PathVariable("chatId") String chatId,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "30") Integer size) {
        return ResponseEntity.ok(alarmProductService.getProducts(chatId, page, size));
    }

    @PostMapping("/{chatId}")
    public ResponseEntity<?> addProduct(@PathVariable("chatId") String chatId,
                                        @RequestBody AlarmAddRequest request) {
        return ResponseEntity.ok(alarmProductService.addProduct(chatId, request));
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<Void> modifyProduct(@PathVariable("chatId") String chatId,
                                              @RequestBody AlarmModifyRequest request) {
        alarmProductService.editProductPrice(chatId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{chatId}/{pid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("chatId") String chatId, @PathVariable("pid") Long pid) {
        alarmProductService.deleteProduct(chatId, pid);
        return ResponseEntity.noContent().build();
    }
}
