package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.dto.FrenetTrackingResponse;
import com.hvivox.srealizacao.service.FrenetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frenet")
public class FrenetController {
    @Autowired
    FrenetService frenetService;

    @PostMapping("/track")
    @Operation(summary = "Como usuário, eu quero monitorar o status dos pedidos com relação ao frete")
    public ResponseEntity<FrenetTrackingResponse> trackOrderStatus(@RequestBody String trackingNumber) {
        System.out.println( trackingNumber );
        return ResponseEntity.ok(frenetService.getTrackingInfo(  trackingNumber ));
        //return ResponseEntity.ok(correiosService.calculateShippingPriceAndDeadline(shippingPriceAndDeadlineCorreios));
    }
}
