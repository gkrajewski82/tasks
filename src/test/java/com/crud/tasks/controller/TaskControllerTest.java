package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void testShouldGetTasks() throws Exception {
        //Given
        List<Task> tasks = List.of(new Task(1L, "test title", "test content"));             //Czy ja to potrzebuję, czy wystarczy w metodach List.of() zamiast tasks, bo też działa?
        List<TaskDto> tasksDto = List.of(new TaskDto(1L, "test title", "test content"));
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);          //to taskDto jako ostatnia zwracana wartość jest porównywana w asercjach?

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content", Matchers.is("test content")));
    }

    @Test
    void testShouldGetTask() throws Exception {
        Task task = new Task(1L, "test title", "test content");
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");
        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test content")));
    }

    @Test
    void testShouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "test title", "test content");

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testShouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");
        Task task = new Task(1L, "test title", "test content");
        Task savedTask = new Task(1L, "test title", "test content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);               // any(TaskDto.class)?
        when(dbService.saveTask(task)).thenReturn(savedTask);               // any(Task.class)?
        when(taskMapper.mapToTaskDto(savedTask)).thenReturn(taskDto);       // any(Task.class)?

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
        //.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
        //.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
        //.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test content")));

    }

    @Test
    void testShouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");
        Task task = new Task(1L, "test title", "test content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);    // any(TaskDto.class)?
        when(dbService.saveTask(task)).thenReturn(task);         // any(Task.class)?

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);  //czy taskDto?

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test content")));
    }
}