package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/17/10:43
 * @Description:
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/initDevice", method = RequestMethod.POST)
    public JsonResult initDevice(@RequestBody JSONObject jsonObject) {
        String folderName = jsonObject.getString("folderName");
        String name = jsonObject.getString("name");
        Double lon = jsonObject.getDouble("lon");
        Double lat = jsonObject.getDouble("lat");
        String stationId = jsonObject.getString("stationId");
        String description = jsonObject.getString("description");
        String avatar = jsonObject.getString("avatar");
        return ResultUtils.success(deviceService.initDevice(folderName, name, lon, lat, stationId, description, avatar));
    }
}
