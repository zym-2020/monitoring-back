package nnu.edu.back.service;

import nnu.edu.back.pojo.Station;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/12:18
 * @Description:
 */
public interface StationService {
    String addStation(Station station);

    List<Map<String, Object>> getAllStation();


}
