package com.projetct.bootcamp.service;

import com.projetct.bootcamp.exceptions.BusinessException;
import com.projetct.bootcamp.exceptions.NotFoundException;
import com.projetct.bootcamp.mapper.StockMapper;
import com.projetct.bootcamp.model.Stock;
import com.projetct.bootcamp.model.dto.StockDTO;
import com.projetct.bootcamp.repository.StockRepository;
import com.projetct.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(),dto.getDate());

        if(optionalStock.isPresent()){
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {

        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());

        if(optionalStock.isPresent()){
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);

    }

    @Transactional
    public StockDTO delete(Long id) {
        StockDTO dto = this.findById(id);
        repository.deleteById(dto.getId());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }


    @Transactional(readOnly = true)
    public StockDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    public List<StockDTO> findByToday() {
        return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }
}
