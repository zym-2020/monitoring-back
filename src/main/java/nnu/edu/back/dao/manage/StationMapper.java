package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.Station;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/11:28
 * @Description:
 */
@Repository
public interface StationMapper {
    void addStation(@Param("station") Station station);

    List<Map<String, Object>> getAllStation();

}
