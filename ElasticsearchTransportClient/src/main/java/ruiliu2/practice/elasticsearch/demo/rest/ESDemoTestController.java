package ruiliu2.practice.elasticsearch.demo.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ruiliu2.practice.elasticsearch.demo.entities.TestObject;
import ruiliu2.practice.elasticsearch.demo.entities.TransText;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoTestService;
import ruiliu2.practice.elasticsearch.demo.services.ESDemoTextService;

/**
 * Created by casa on 2017/5/12.
 */
@Controller
@RequestMapping(value = "/api/test")
public class ESDemoTestController {

    @Autowired
    private ESDemoTestService esDemoTestService;

    @RequestMapping(value = "/testObj", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传转写实体",
            notes = "上传转写实体",
            response = TransText.class)
    public TestObject trans(@RequestBody TestObject obj) {
        return esDemoTestService.obj(obj);
    }

    @RequestMapping(value = "/testObj/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "创建index",
            notes = "创建index",
            response = Boolean.class)
    public boolean index() {
        return esDemoTestService.index();
    }
}
