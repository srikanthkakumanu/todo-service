package todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import todo.domain.ToDo;
import todo.dto.TodoDTO;
import todo.exception.TodoServiceException;
import todo.mapper.TodoMapper;
import todo.repository.ToDoRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repository;
    private final TodoMapper mapper;

    public ToDoServiceImpl(ToDoRepository repository, TodoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TodoDTO save(TodoDTO dto) {
        log.debug("save: [{}]", dto.toString());

        if (Objects.nonNull(dto.getId())) {
            Optional<ToDo> foundOptional = repository.findById(dto.getId());

            Optional<TodoDTO> updated = foundOptional.map(found -> {
                if (Objects.nonNull(dto.getTitle()))
                    found.setTitle(dto.getTitle());
                if (Objects.nonNull(dto.getDescription()))
                    found.setDescription(dto.getDescription());
                if (Objects.nonNull(dto.getUserId()))
                    found.setUserId(dto.getUserId());
                if (Objects.nonNull(dto.getUserName()))
                    found.setUserName(dto.getUserName());
                if (Objects.nonNull(dto.getCompleted()))
                    found.setCompleted(dto.getCompleted());

                return mapper.toDTO(repository.save(found));
            });

            if (updated.isPresent())
                return updated.get();
        }

        ToDo saved = repository.save(mapper.toDomain(dto));
        log.info("Generated Id after saving ToDo: {}", saved.getId());
        return mapper.toDTO(saved);
    }

    @Override
    public TodoDTO delete(UUID id) {
        log.debug("delete: [Id: {}]", id);

        ToDo found = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("ToDo with id {} not found", id);
                    return new TodoServiceException("id", HttpStatus.NOT_FOUND, "ToDo does not exist");
                });

        repository.delete(found);
        return mapper.toDTO(found);

    }

    @Override
    public List<TodoDTO> findAll() {
        log.debug("findAll() called");
        List<ToDo> todos = repository.findAll();
        log.debug("todos: length: {}", todos.size());

        return todos.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TodoDTO findById(UUID id) {
        log.debug("findById: [Id: {}]", id);

        ToDo found = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("ToDo with id {} not found", id);
                    return new TodoServiceException("id", HttpStatus.NOT_FOUND, "ToDo does not exist");
                });

        return mapper.toDTO(found);

    }

    @Override
    public TodoDTO findByTitle(String title) {
        log.debug("findByTitle: [title: {}]", title);

        ToDo found =
                repository.findByTitle(title)
                        .orElseThrow(() -> {
                            log.error(String.format("title with %s does not exist", title));
                            return new TodoServiceException("title",
                                    HttpStatus.NOT_FOUND, "title does not exist");
                        });
        return mapper.toDTO(found);
    }
}
