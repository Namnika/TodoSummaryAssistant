package com.todoSummaryApplication.todoSummary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoSummaryApplication.todoSummary.dto.TodoRequestDto;
import com.todoSummaryApplication.todoSummary.dto.TodoResponseDto;
import com.todoSummaryApplication.todoSummary.repository.TodoRepository;
import com.todoSummaryApplication.todoSummary.model.Todo;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	// GET ALL TODOS
	public List<TodoResponseDto> getAllTodos() {
		List<Todo> todos = todoRepository.findAll();
		return todos.stream().map(this::mapToResponseTodo).collect(Collectors.toList());
	}

	// ADD TODOS (also add Cohere summary)
	public TodoResponseDto addTodo(TodoRequestDto dto) {
		Todo todo = new Todo();
		todo.setTodoItem(dto.getTodoItem());

		Todo saved = todoRepository.save(todo);
		return mapToResponseTodo(saved);
	}

	// UPDATE TODOS
	public TodoResponseDto updateTodo(Long id, TodoRequestDto dto) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo Not Found!"));

		todo.setTodoItem(dto.getTodoItem());

		Todo saved = todoRepository.save(todo);

		return mapToResponseTodo(saved);
	}

	// DELETE TODOS
	public void deleteTodo(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo Not Found!"));

		todoRepository.delete(todo);
	}

	// Mapping todo responses from Todo to TodoResponseDto
	public TodoResponseDto mapToResponseTodo(Todo todo) {
		TodoResponseDto dto = new TodoResponseDto();
		dto.setId(todo.getId());
		dto.setTodoItem(todo.getTodoItem());

		return dto;

	}
}
