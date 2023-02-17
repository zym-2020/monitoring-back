package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/10:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {
    String type;
    String script;
    List<Param> testParams;
    List<Param> executeParams;
    String main;
}
