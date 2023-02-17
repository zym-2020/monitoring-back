package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/10:24
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Col {
    String id;
    String type;
    String key;
    List<String> constraints;
}
