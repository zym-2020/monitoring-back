package nnu.edu.back.pojo.support.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.pojo.support.deviceconfig.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/22:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamScript {
    List<Param> test;
    List<Param> execute;
    String main;
    String result;
    String script;
}
