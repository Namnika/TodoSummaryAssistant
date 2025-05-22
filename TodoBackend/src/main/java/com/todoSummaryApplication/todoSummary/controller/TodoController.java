package com.todoSummaryApplication.todoSummary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

import com.todoSummaryApplication.todoSummary.dto.TodoRequestDto;
import com.todoSummaryApplication.todoSummary.model.Todo;
import com.todoSummaryApplication.todoSummary.dto.TodoResponseDto;
import com.todoSummaryApplication.todoSummary.repository.TodoRepository;
import com.todoSummaryApplication.todoSummary.service.CohereService;
import com.todoSummaryApplication.todoSummary.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private CohereService cohereService;

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    // GET ALL TODOS
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        try {
            List<TodoResponseDto> todos = todoService.getAllTodos();
            return ResponseEntity.ok(todos);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    // ADD TODOS
    @PostMapping
    public ResponseEntity<TodoResponseDto> addTodo(@RequestBody TodoRequestDto dto) {
        try {
            TodoResponseDto todo = todoService.addTodo(dto);
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // UPDATE TODOS
    @PutMapping("/update/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto dto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo Not Found!"));
        try {
            TodoResponseDto todoResponse = todoService.updateTodo(id, dto);
            return ResponseEntity.ok(todoResponse);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // DELETE TODOS
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable Long id) {
        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo Not Found!"));
            todoRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Todo deleted Successfully!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Todo Not Found!");
            return ResponseEntity.badRequest().body(error);
        }

    }

    // Send All Todo Summaries using LLM
    @PostMapping("/summarize")
    public ResponseEntity<String> generateSummary() {
        List<Todo> todos = todoRepository.findAll();

        if (todos.isEmpty()) {
            throw new RuntimeException("No Todo Items to Summarize!");
        }

        // Combining all todo items into a single string
        String combinedText = todos.stream()
                .map(Todo::getTodoItem)
                .collect(Collectors.joining("\n"));

        // Calling Cohere API to generate summary
        String summary = cohereService.generateSummary(combinedText);

        // send to slack
        sendToSlack(summary);

        return ResponseEntity.ok("Summary Generated and sent to Slack Successfully!");
    }

    private void sendToSlack(String summary) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> payload = new HashMap<>();

        payload.put("text", summary);
        restTemplate.postForEntity(slackWebhookUrl, payload, String.class);
    }
}
