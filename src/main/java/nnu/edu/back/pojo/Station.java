package nnu.edu.back.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/11:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    String id;
    String name;
    String department;
    String description;
    Double lon;
    Double lat;
    String avatar;
}
