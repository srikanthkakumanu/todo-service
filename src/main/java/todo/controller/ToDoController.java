package todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import todo.dto.TodoDTO;
import todo.service.ToDoService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private final ToDoService service;

    @Autowired
    public ToDoController(ToDoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Retrieve All To-Dos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found zero or more To-Dos",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TodoDTO.class)))})})
    public ResponseEntity<List<TodoDTO>> getToDos() {
        log.debug("Fetch all ToDos");
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getToDoById(@PathVariable UUID id) {
        log.debug("Fetch ToDo By Id: [Id: {}]", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoDTO> setCompleted(@PathVariable UUID id) {
        log.debug("todo: setCompleted[Id: {}]", id);
        TodoDTO result = service.findById(id);
        result.setCompleted(true);
        TodoDTO saved = service.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.OK)
                .location(location)
                .body(saved);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<TodoDTO> createToDo(
            @Valid @RequestBody TodoDTO todo) {

        log.debug("save: [{}]", todo.toString());
        TodoDTO result = service.save(todo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.status(HttpStatus.OK).location(location).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoDTO> deleteToDo(@PathVariable UUID id) {

        // repository.delete(ToDoBuilder.create().withId(id).build());
        // return ResponseEntity.noContent().build();
        log.debug("Delete todo: [Id: {}]", id);
        TodoDTO deleted = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}