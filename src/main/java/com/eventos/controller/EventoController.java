package com.eventos.controller;

import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    public ResponseEntity<List<Evento>> listaEventos(){
        List<Evento> eventoList = eventoRepository.findAll();

        for (Evento evento :
                eventoList) {
            long id = evento.getCodigo();
            evento.add(
                    linkTo(
                        methodOn(EventoController.class)
                                .retornaEvento(id)
                    ).withSelfRel()
            );
        }

        return new ResponseEntity<>(eventoList, HttpStatus.OK);
    }

    @Operation(summary = "Retorna um evento")
    @GetMapping("/{id}")
    public ResponseEntity<Evento> retornaEvento(@PathVariable Long id){
        Optional<Evento> eventoOptional = eventoRepository.findById(id);

        if(!eventoOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        eventoOptional.get().add(
                linkTo(
                        methodOn(EventoController.class)
                                .listaEventos()
                ).withRel("Lista de Eventos")
        );

        return new ResponseEntity<>(eventoOptional.get(), HttpStatus.OK);
    }

    @Operation(summary = "Salva um evento")
    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody @Valid Evento evento){

        Evento eventoCriado = eventoRepository.save(evento);

        eventoCriado.add(
                linkTo(
                        methodOn(EventoController.class)
                                .retornaEvento(eventoCriado.getCodigo())
                ).withRel("Lista de Eventos")
        );

        return new ResponseEntity<>(eventoCriado, HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um evento")
    @DeleteMapping()
    public ResponseEntity<String> deletarEvento(@RequestBody Evento evento) {
        eventoRepository.delete(evento);

        return new ResponseEntity<>("Excluído com sucesso!", HttpStatus.OK);
    }

}
