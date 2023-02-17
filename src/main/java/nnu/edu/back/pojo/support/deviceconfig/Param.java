package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/19:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    String name;
    Boolean sys;
    String value;
}
