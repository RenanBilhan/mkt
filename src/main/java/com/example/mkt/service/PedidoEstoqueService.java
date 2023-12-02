//package com.example.mkt.service;
//
//import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueInputDTO;
//import com.example.mkt.dto.pedidoEstoque.PedidoEstoqueOutputDTO;
//import com.example.mkt.entity.PedidoEstoqueEntity;
//import com.example.mkt.entity.ProdutoEntity;
//import com.example.mkt.repository.EstoqueRepository;
//import com.example.mkt.repository.PedidoEstoqueRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PedidoEstoqueService {
//
//    private final PedidoEstoqueRepository pedidoEstoqueRepository;
//
//    private final EstoqueRepository estoqueRepository;
//
//    public PedidoEstoqueOutputDTO save(PedidoEstoqueInputDTO pedidoEstoqueInputDTO){
//
//        PedidoEstoqueEntity pedidoEstoqueEntity = new PedidoEstoqueEntity();
//
////        idestoque    quant
//        ProdutoEntity produto = estoqueRepository.getById(pedidoEstoqueInputDTO.getIdEstoque()).getProduto();
//
//        PedidoEstoqueOutputDTO dto = new PedidoEstoqueOutputDTO(
//            pedidoEstoqueInputDTO.getIdEstoque(),
//        )
//    }


//}
