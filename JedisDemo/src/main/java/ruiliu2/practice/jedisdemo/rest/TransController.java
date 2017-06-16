package ruiliu2.practice.jedisdemo.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ruiliu2.practice.jedisdemo.service.TransService;

import java.util.List;

/**
 * Created by ruiliu2 on 2017/4/25.
 */
@Controller
@RequestMapping(value = "/api/trans/")
@Api
public class TransController {

    @Autowired
    private TransService transService;

    @RequestMapping(value = "start", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "开始转写",
            notes = "开始转写",
            response = String.class)
    public String startTrans() {
        return transService.start();
    }

    @RequestMapping(value = "end", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "结束转写",
            notes = "结束转写",
            response = String.class)
    public String end() {
        return transService.end();
    }

    @RequestMapping(value = "original/{key}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "保存原始结果",
            notes = "保存原始结果",
            response = String.class)
    public List<Lattice> original(@PathVariable(value = "key") String key) {
        return transService.original(key);
    }

    @RequestMapping(value = "pushEdit/{concurrency}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "压测编辑结果的缓存",
            notes = "压测编辑结果的缓存",
            response = Boolean.class)
    public boolean pushTest(@PathVariable(value = "concurrency") int concurrency) {
        return transService.pushEdit(concurrency);
    }

    @RequestMapping(value = "pushOriginal1/{concurrency}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "压测原始结果的缓存",
            notes = "压测原始结果的缓存",
            response = Boolean.class)
    public boolean pushOriginal(@PathVariable(value = "concurrency") int concurrency) {
        return transService.pushOriginal(concurrency);
    }
}
