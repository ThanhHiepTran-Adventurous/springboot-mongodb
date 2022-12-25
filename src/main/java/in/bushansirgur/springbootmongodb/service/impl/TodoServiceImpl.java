package in.bushansirgur.springbootmongodb.service.impl;

import in.bushansirgur.springbootmongodb.exception.TodoCollectionException;
import in.bushansirgur.springbootmongodb.model.TodoDTO;
import in.bushansirgur.springbootmongodb.repository.TodoRepository;
import in.bushansirgur.springbootmongodb.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if(todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll();
        if(todos.size() > 0) {
            return todos;
        }else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
         Optional<TodoDTO> optionalTodoDTO =  todoRepository.findById(id);
         if(!optionalTodoDTO.isPresent()){
             throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
         }else {
             return optionalTodoDTO.get();
         }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
         Optional<TodoDTO> todoWithId = todoRepository.findById(id);
         Optional<TodoDTO> todoWithSameName = todoRepository.findByTodo(todo.getTodo());
         if(todoWithId.isPresent()) {

             if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                 throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
             }


              TodoDTO todoUpdate = todoWithId.get();

              todoUpdate.setTodo(todo.getTodo());
              todoUpdate.setDescription(todo.getDescription());
              todoUpdate.setCompleted(todo.getCompleted());
              todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
              todoRepository.save(todoUpdate);
         }else {
             throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
         }

    }

    @Override
    public void deleteTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
            todoRepository.deleteById(id);
        }
    }
}
