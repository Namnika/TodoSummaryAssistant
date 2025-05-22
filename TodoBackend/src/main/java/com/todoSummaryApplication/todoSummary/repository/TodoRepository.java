package com.todoSummaryApplication.todoSummary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todoSummaryApplication.todoSummary.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
