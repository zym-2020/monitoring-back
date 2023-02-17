package nnu.edu.back.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/17/10:44
 * @Description:
 */
public interface DeviceService {
    String initDevice(String folderName, String name, Double lon, Double lat, String stationId, String description, String avatar);
}
