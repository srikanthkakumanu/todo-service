package todo.service;

import todo.dto.TodoDTO;

import java.util.List;
import java.util.UUID;

public interface ToDoService {

    TodoDTO save (TodoDTO dto);
    TodoDTO delete (UUID id);
    List<TodoDTO> findAll ();
    TodoDTO findById (UUID id);
    TodoDTO findByTitle (String title);
}
