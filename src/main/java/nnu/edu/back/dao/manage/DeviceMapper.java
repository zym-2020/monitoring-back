package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/13:07
 * @Description:
 */
@Repository
public interface DeviceMapper {
    void addDevice(@Param("device")Device device);

}
