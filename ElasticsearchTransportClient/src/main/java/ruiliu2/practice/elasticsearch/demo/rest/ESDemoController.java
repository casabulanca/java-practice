package ruiliu2.practice.elasticsearch.demo.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;
import ruiliu2.practice.elasticsearch.demo.entities.TransEntity;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoService;

import java.util.List;


/**
 * 测试控制器
 * Created by ruiliu2 on 2017/4/20.
 */
@Controller
@RequestMapping(value = "/api/es")
public class ESDemoController {

    @Autowired
    private ESDemoService esDemoService;

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据ID获取转写实体",
            notes = "根据ID获取转写实体",
            response = TransEntity.class)
    public TransEntity trans(@PathVariable(value = "id") String id) {
        return esDemoService.entity(id);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "创建index",
            notes = "创建index",
            response = Boolean.class)
    public boolean index() {
        return esDemoService.index();
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传转写实体",
            notes = "上传转写实体",
            response = TransEntity.class)
    public TransEntity trans(@RequestBody TransEntity transEntity) {
        return esDemoService.entity(transEntity);
    }

    @RequestMapping(value = "/entities/search", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询转写实体",
            notes = "查询转写实体",
            response = TransEntity.class)
    public List<TransEntity> transEntities(@RequestBody SearchBody searchBody) {
        return esDemoService.entities(searchBody);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "修改转写实体",
            notes = "修改转写实体",
            response = TransEntity.class)
    public String update(@RequestBody TransEntity transEntity) {
        return esDemoService.update(transEntity);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除转写实体",
            notes = "删除转写实体",
            response = TransEntity.class)
    public String delete(@PathVariable(value = "id") String id) {
        return esDemoService.delete(id);
    }

}
