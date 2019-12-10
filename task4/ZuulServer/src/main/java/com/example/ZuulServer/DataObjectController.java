package com.example.ZuulServer;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class DataObjectController {
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataObject findById(
            @PathVariable long id, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("got id: " + id);
        if (req.getHeader("Test") != null) {
            res.addHeader("Test", req.getHeader("Test"));
        }
        return new DataObject(id, RandomStringUtils.randomAlphanumeric(10));
    }
}
