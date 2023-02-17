package nnu.edu.back.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/15/16:24
 * @Description:
 */
@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    ManageService manageService;

    @RequestMapping(value = "/checkConfig", method = RequestMethod.POST)
    public JsonResult checkConfig(@RequestBody JSONObject jsonObject) {
        return ResultUtils.success(manageService.checkXMLConfig(jsonObject.getString("folderName")));
    }

    /**
    * @Description:传参的形式执行测试函数
    * @Author: Yiming
    * @Date: 2023/2/16
    */

    @RequestMapping(value = "/testByParam", method = RequestMethod.POST)
    public JsonResult testByParam(@RequestBody JSONObject jsonObject) {
        return ResultUtils.success(manageService.testByParam(jsonObject));
    }

    /**
    * @Description:脚本的形式执行测试函数
    * @Author: Yiming
    * @Date: 2023/2/16
    */

    @RequestMapping(value = "/testByScript", method = RequestMethod.POST)
    public JsonResult testByScript(@RequestBody JSONObject jsonObject) {
        return ResultUtils.success(manageService.testByScript(jsonObject.getString("deviceId"), jsonObject.getString("schemaId")));
    }


    @RequestMapping(value = "/executeByParam", method = RequestMethod.POST)
    public JsonResult executeByParam(@RequestBody JSONObject jsonObject) {
        manageService.executeByParam(jsonObject);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/executeByScript", method = RequestMethod.POST)
    public JsonResult executeByScript(@RequestBody JSONObject jsonObject) {
        manageService.executeByScript(jsonObject.getString("deviceId"), jsonObject.getString("schemaId"));
        return ResultUtils.success();
    }
}
