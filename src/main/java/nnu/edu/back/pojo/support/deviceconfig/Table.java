package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/10:20
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Table {
    String name;
    String type;
    String database;
    Structure structure;
    List<String> constraints;
}
