package com.eventos.controller;

import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "API REST Eventos",
                version = "1.0",
                description = "API REST de cadastro de eventos",
                contact = @Contact(
                        name = "João Silva",
                        email = "jv_rss@hotmail.com"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                )
        )
)
@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Operation(summary = "Retorna uma lista de eventos")
    @GetMapping
    public List<Evento> listaEventos(){
        return eventoRepository.findAll();
    }

    @Operation(summary = "Salva um evento")
    @PostMapping
    public Evento criarEvento(@RequestBody @Valid Evento evento){
        return eventoRepository.save(evento);
    }

    @Operation(summary = "Exclui um evento")
    @DeleteMapping()
    public void deletarEvento(@RequestBody Evento evento) {
        eventoRepository.delete(evento);
    }

}
