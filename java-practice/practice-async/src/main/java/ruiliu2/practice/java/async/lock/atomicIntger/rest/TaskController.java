package ruiliu2.practice.java.async.lock.atomicIntger.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ruiliu2.practice.java.async.lock.atomicIntger.service.TaskService;

import java.util.concurrent.ExecutionException;

/**
 * 任务控制控制器
 * Created by ruiliu2@.com on 16/8/19.
 */
@Controller
@RequestMapping(value = "/api/taskSchedule/")
@Api(value = "任务控制控制器")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "start/{id}/{value}/{duration}", method = RequestMethod.GET)
    @ApiOperation(value = "开始一个任务,修改某个指定id的值", notes = "开始一个任务,修改某个指定id的值", response = Boolean.class)
    @ResponseBody
    public boolean startTask(@PathVariable(value = "id") String id,
                             @PathVariable(value = "value") String value,
                             @PathVariable(value = "duration") String duration) throws ExecutionException, InterruptedException {
        return !(taskService.addTask(id, value, duration).equals(""));
    }

    @RequestMapping(value = "value/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个id的值", notes = "获取某个id的值", response = Boolean.class)
    @ResponseBody
    public String value(@PathVariable(value = "id") String id) {
        return "";
    }

}
