package ruiliu2.practice.java.async.lock.atomicIntger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import ruiliu2.practice.java.async.lock.atomicIntger.lockPool.LockPool;

import java.util.concurrent.ExecutionException;


/**
 * 任务调度服务
 * Created by ruiliu2@.com on 16/8/19.
 */
@Service
public class TaskService {

    @Autowired
    private LockPool lockPool;

    @CachePut(value = "taskCache", key = "#id")
    public String addTask(String id, String value, String duration) throws InterruptedException, ExecutionException {

        System.out.println(String.format("Caching value: %s, duration: %s", value, duration));
        Thread.sleep(1000 * Long.valueOf(duration));
        return value;

    }
}
