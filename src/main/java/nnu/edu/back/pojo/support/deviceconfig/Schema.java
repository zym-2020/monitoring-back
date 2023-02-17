package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/10:18
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schema {
    String type;
    String source;
    String id;
    Table table;
    Files files;
    Path path;
}
