package nnu.edu.back.service.impl;

import nnu.edu.back.dao.manage.StationMapper;
import nnu.edu.back.pojo.Station;
import nnu.edu.back.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/04/12:19
 * @Description:
 */
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    StationMapper stationMapper;

    @Override
    public String addStation(Station station) {
        String uuid = UUID.randomUUID().toString();
        station.setId(uuid);
        stationMapper.addStation(station);
        return uuid;
    }

    @Override
    public List<Map<String, Object>> getAllStation() {
        return stationMapper.getAllStation();
    }

}
