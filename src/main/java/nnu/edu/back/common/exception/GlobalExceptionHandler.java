package nnu.edu.back.common.exception;

import nnu.edu.back.common.result.JsonResult;
import nnu.edu.back.common.result.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/11:02
 * @Description:
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public JsonResult myExceptionHandler(HttpServletRequest req, MyException e) {
        return ResultUtils.fail(e.getCode(), e.getMsg());
    }
}
