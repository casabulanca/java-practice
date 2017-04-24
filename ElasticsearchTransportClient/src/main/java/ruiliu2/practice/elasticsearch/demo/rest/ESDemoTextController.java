package ruiliu2.practice.elasticsearch.demo.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;
import ruiliu2.practice.elasticsearch.demo.entities.TransEntity;
import ruiliu2.practice.elasticsearch.demo.entities.TransText;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoService;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoTextService;

import java.util.List;


/**
 * 测试控制器
 * Created by ruiliu2 on 2017/4/20.
 */
@Controller
@RequestMapping(value = "/api/es")
public class ESDemoTextController {

    @Autowired
    private ESDemoTextService  esDemoTextService;

    @RequestMapping(value = "/entityText/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据ID获取转写实体",
            notes = "根据ID获取转写实体",
            response = TransText.class)
    public TransText trans(@PathVariable(value = "id") String id) {
        return esDemoTextService.entity(id);
    }

    @RequestMapping(value = "/entityText/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "创建index",
            notes = "创建index",
            response = Boolean.class)
    public boolean index() {
        return esDemoTextService.index();
    }

    @RequestMapping(value = "/entityText", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传转写实体",
            notes = "上传转写实体",
            response = TransText.class)
    public TransText trans(@RequestBody TransText transEntity) {
        return esDemoTextService.entity(transEntity);
    }

    @RequestMapping(value = "/entitiesText/search", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询转写实体",
            notes = "查询转写实体",
            response = TransText.class)
    public List<TransText> transEntities(@RequestBody SearchBody searchBody) {
        return esDemoTextService.entities(searchBody);
    }

    @RequestMapping(value = "/entityText", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "修改转写实体",
            notes = "修改转写实体",
            response = TransText.class)
    public String update(@RequestBody TransText transEntity) {
        return esDemoTextService.update(transEntity);
    }

    @RequestMapping(value = "/entityText/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除转写实体",
            notes = "删除转写实体",
            response = TransText.class)
    public String delete(@PathVariable(value = "id") String id) {
        return esDemoTextService.delete(id);
    }

}
