package ruiliu2.practice.testServer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ruiliu2 on 2017/3/26.
 */
@Controller
@RequestMapping(value = "/api/")
public class TestController {

    @RequestMapping(value = "testDelay/{cost}", method = RequestMethod.GET)
    @ResponseBody
    public String testDelay(@PathVariable(value = "cost") long cost) {
        try {
            Thread.sleep(cost);
            return "hello world";
        } catch (InterruptedException e) {
            return "";
        }
    }
}
