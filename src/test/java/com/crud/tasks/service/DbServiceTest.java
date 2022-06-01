package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Test
    void testGetTask() throws TaskNotFoundException {
        //Given
        Task task = new Task("test title", "test content");
        dbService.saveTask(task);
        Long id = task.getId();

        //When
        Task savedTask = dbService.getTask(id);

        //Then
        assertEquals("test title", dbService.getTask(id).getTitle());
        assertEquals("test content", dbService.getTask(id).getContent());

        //Cleanup
        dbService.deleteTask(id);
    }

    @Test
    void testSaveTask() throws TaskNotFoundException {
        //Given
        Task task = new Task("test title", "test content");

        //When
        dbService.saveTask(task);
        Long id = task.getId();

        //Then
        assertEquals("test title", dbService.getTask(id).getTitle());
        assertEquals("test content", dbService.getTask(id).getContent());

        //Cleanup
        dbService.deleteTask(id);
    }

    @Test
    void testDeleteTask() throws TaskNotFoundException {
        //Given
        Task task = new Task("test title", "test content");
        dbService.saveTask(task);
        Long id = task.getId();

        //When
        dbService.deleteTask(id);

        //Then
        assertFalse(dbService.ifTaskExist(id));
    }
}