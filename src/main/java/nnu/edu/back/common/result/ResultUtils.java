package nnu.edu.back.common.result;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/11:01
 * @Description:
 */
public class ResultUtils {
    public static JsonResult success(Object obj) {
        return new JsonResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), obj, null);
    }

    public static JsonResult success() {
        return success(null);
    }

    public static JsonResult fail(ResultEnum resultEnum) {
        return new JsonResult(resultEnum.getCode(), resultEnum.getMsg(), null, null);
    }
    public static JsonResult fail(Integer code, String msg) {
        return new JsonResult(code, msg, null, null);
    }
}
