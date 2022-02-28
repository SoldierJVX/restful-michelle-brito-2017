package com.eventos.controller;

import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping
    public List<Evento> listaEventos(){
        return eventoRepository.findAll();
    }

    @PostMapping
    public Evento criarEvento(@RequestBody @Valid Evento evento){
        return eventoRepository.save(evento);
    }

    @DeleteMapping(value = "/{id}")
    public void deletarEvento(@PathVariable Long id) {
        eventoRepository.deleteById(id);
    }

}
