package nnu.edu.back.controller;

import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.pojo.Station;
import nnu.edu.back.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/12:17
 * @Description:
 */
@RequestMapping("/station")
@RestController
public class StationController {
    @Autowired
    StationService stationService;

    @RequestMapping(value = "/addStation", method = RequestMethod.POST)
    public JsonResult addStation(@RequestBody Station station) {
        return ResultUtils.success(stationService.addStation(station));
    }

    @RequestMapping(value = "/getAllStation", method = RequestMethod.GET)
    public JsonResult getAllStation() {
        return ResultUtils.success(stationService.getAllStation());
    }

}
