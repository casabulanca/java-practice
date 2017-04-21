package ruiliu2.practice.elasticsearch.demo.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruiliu2.practice.elasticsearch.core.query.Pagination;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;
import ruiliu2.practice.elasticsearch.demo.entities.TransEntity;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoService;

import java.util.List;


/**
 * Created by casa on 2017/4/20.
 */
@Controller
@RequestMapping(value = "/api/es")
public class ESDemoController {

    @Autowired
    private ESDemoService esDemoService;

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取会议属性接口",
            notes = "获取会议属性接口",
            response = TransEntity.class)
    public TransEntity trans(@PathVariable(value = "id") String id) {
        return esDemoService.entity(id);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取会议属性接口",
            notes = "获取会议属性接口",
            response = Boolean.class)
    public boolean index() {
        return esDemoService.index();
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取会议属性接口",
            notes = "获取会议属性接口",
            response = TransEntity.class)
    public TransEntity trans(@RequestBody TransEntity transEntity) {
        return esDemoService.entity(transEntity);
    }

    @RequestMapping(value = "/entities/search", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取会议属性接口",
            notes = "获取会议属性接口",
            response = TransEntity.class)
    public List<TransEntity> transEntities(@RequestBody SearchBody searchBody) {
        return esDemoService.entities(searchBody);
    }

}
