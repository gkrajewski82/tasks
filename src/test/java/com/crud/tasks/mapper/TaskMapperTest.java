package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, task.getId());
        assertEquals("test title", task.getTitle());
        assertEquals("test content", task.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test title", "test content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("test title", taskDto.getTitle());
        assertEquals("test content", taskDto.getContent());
    }

    @Test
    void testMapToTaskList() {
        //Given
        List<Task> tasks = List.of(new Task(1L, "test name", "test content"));

        //When
        List<TaskDto> tasksDto = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertEquals(1, tasksDto.size());
    }
}