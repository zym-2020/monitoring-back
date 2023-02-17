package nnu.edu.back.pojo.support.deviceconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nnu.edu.back.pojo.support.deviceconfig.Schema;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/10:12
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceXmlConfig {
    String id;
    String name;
    Double lon;
    Double lat;
    String station;
    String department;
    String description;
    List<Schema> schemas;
}
