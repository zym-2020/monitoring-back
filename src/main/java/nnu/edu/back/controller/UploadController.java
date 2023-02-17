package nnu.edu.back.controller;

import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import nnu.edu.back.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/14:11
 * @Description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/uploadPackage", method = RequestMethod.POST)
    public JsonResult uploadPackage(@RequestParam MultipartFile file) {
        return ResultUtils.success(uploadService.uploadPackage(file));
    }

    @RequestMapping(value = "/uploadParamScript", method = RequestMethod.POST)
    public JsonResult uploadParamScript(@RequestParam MultipartFile file, @RequestParam String deviceId, @RequestParam String schemaId) {
        uploadService.uploadParamScript(file, deviceId, schemaId);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    public JsonResult uploadPicture(@RequestParam MultipartFile file) {
        return ResultUtils.success(uploadService.uploadPicture(file));
    }
}
