package com.example.mkt.controller;

import com.example.mkt.documentation.OrderControllerDoc;
import com.example.mkt.dto.order.OrderInputDTO;
import com.example.mkt.dto.order.OrderOutputDTO;
import com.example.mkt.exceptions.BussinessRuleException;
import com.example.mkt.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc {

    private final OrderService pedidoService;

    @GetMapping
    public ResponseEntity<List<OrderOutputDTO>> findAll(){
        return new ResponseEntity<>(pedidoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<OrderOutputDTO> save(@PathVariable Integer idCliente, @RequestBody OrderInputDTO pedidoInputDTO) throws BussinessRuleException, MessagingException {
        return new ResponseEntity<>(pedidoService.save(idCliente, pedidoInputDTO), HttpStatus.CREATED);
    }

//    @PutMapping("/payment/confirm/{idPedido}")
//    public ResponseEntity<OrderOutputDTO> confirmPayment(@PathVariable Integer idPedido) throws MessagingException {
//        return new ResponseEntity<>(pedidoService.confirmPayment(idPedido), HttpStatus.OK);
//    }
}
