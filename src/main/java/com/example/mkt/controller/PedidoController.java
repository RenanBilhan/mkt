package com.example.mkt.controller;

import com.example.mkt.dto.pedido.PedidoInputDTO;
import com.example.mkt.dto.pedido.PedidoOutputDTO;
import com.example.mkt.entity.enums.StatusPedido;
import com.example.mkt.exceptions.RegraDeNegocioException;
import com.example.mkt.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoOutputDTO>> findAll(){
        return new ResponseEntity<>(pedidoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<PedidoOutputDTO> save(@PathVariable Integer idCliente, @RequestBody PedidoInputDTO pedidoInputDTO) throws RegraDeNegocioException, MessagingException {
        return new ResponseEntity<>(pedidoService.save(idCliente, pedidoInputDTO), HttpStatus.CREATED);
    }

    @PutMapping("/payment/confirm/{idPedido}")
    public ResponseEntity<PedidoOutputDTO> confirmPayment(@PathVariable Integer idPedido) throws MessagingException {
        return new ResponseEntity<>(pedidoService.confirmPayment(idPedido), HttpStatus.OK);
    }
}
