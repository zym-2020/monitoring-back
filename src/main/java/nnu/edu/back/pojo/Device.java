package nnu.edu.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/13:00
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    String id;
    String name;
    Double lon;
    Double lat;
    String stationId;
    String description;
    String avatar;
}
