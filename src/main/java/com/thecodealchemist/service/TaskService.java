package com.thecodealchemist.service;

import com.thecodealchemist.entity.TaskEntity;
import com.thecodealchemist.model.TaskDetail;
import com.thecodealchemist.model.TaskPostRequest;
import com.thecodealchemist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public int addAll(List<TaskPostRequest> taskPostRequest){
        List<TaskEntity> tasks = taskPostRequest.stream().map(taskRequest -> {
            TaskEntity en = new TaskEntity();
            en.setCreatedBy(taskRequest.getCreatedBy());
            en.setTitle(taskRequest.getTitle());
            en.setPriority(taskRequest.getPriority());
            en.setDescription(taskRequest.getDescription());
            return en;
        }).collect(Collectors.toList());

        return taskRepository.saveAll(tasks).size();
    }

    public List<TaskDetail> fetchAll() {
        List<TaskEntity> allTasks = taskRepository.findAll();

        return allTasks.stream()
                .map(entity->
                        new TaskDetail(
                                entity.getTitle(),
                                entity.getPriority(),
                                entity.getCreatedBy(),
                                entity.getDescription()))
                .collect(Collectors.toList());
    }

    public List<TaskDetail> fetchByCreatedBy(String user) {
        List<TaskEntity> tasksByUser = taskRepository.findAllByCreatedBy(user);

        return tasksByUser.stream()
                .map(entity->
                        new TaskDetail(
                                entity.getTitle(),
                                entity.getPriority(),
                                entity.getCreatedBy(),
                                entity.getDescription()))
                .collect(Collectors.toList());
    }

    public List<TaskDetail> subset(String orderBy, String direction, int page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));

        return taskRepository.findAll(pageable).stream()
                .map(entity->
                        new TaskDetail(
                                entity.getTitle(),
                                entity.getPriority(),
                                entity.getCreatedBy(),
                                entity.getDescription()))
                .collect(Collectors.toList());
    }
}