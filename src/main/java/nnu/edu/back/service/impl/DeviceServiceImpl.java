package nnu.edu.back.service.impl;

import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.dao.main.PGManageMapper;
import nnu.edu.back.dao.manage.DeviceMapper;
import nnu.edu.back.dao.manage.TableManageMapper;
import nnu.edu.back.pojo.Device;
import nnu.edu.back.pojo.support.deviceconfig.DeviceXmlConfig;
import nnu.edu.back.pojo.support.deviceconfig.Schema;
import nnu.edu.back.pojo.support.deviceconfig.Table;
import nnu.edu.back.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/17/10:44
 * @Description:
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Value("${tempPath}")
    String tempPath;

    @Value("${binPath}")
    String binPath;

    @Autowired
    TableManageMapper tableManageMapper;

    @Autowired
    PGManageMapper pgManageMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public String initDevice(String folderName, String name, Double lon, Double lat, String stationId, String description, String avatar) {
        String path = tempPath + folderName + "/config.xml";
        DeviceXmlConfig deviceXmlConfig = FileUtil.parseXML(path);
        if (deviceXmlConfig == null) {
            throw new MyException(-99, "配置文件解析错误");
        }
        String id = deviceXmlConfig.getId();
        String to = binPath + id;
        if (FileUtil.moveFile(tempPath + folderName, to) == -1) {
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }

        List<Map<String, Object>> list = tableManageMapper.findAllTablesInfo();

        for (Schema schema : deviceXmlConfig.getSchemas()) {
            if (schema.getSource().equals("database")) {
                for (Map<String, Object> map : list) {
                    if (map.get("datasourceType").equals(schema.getTable().getType()) && map.get("tableName").equals(schema.getTable().getDatabase())) {
                        throw new MyException(-99, "数据库名" + map.get("tableName") + "重名");
                    }
                }
            }
        }

        List<Schema> finishedSchemas = new ArrayList<>();
        for (Schema schema : deviceXmlConfig.getSchemas()) {
            if (schema.getSource().equals("database")) {
                Table table = schema.getTable();
                if (table.getType().equals("postgresql")) {
                    try {
                        pgManageMapper.createTable(table.getStructure().getCols(), table.getConstraints(), table.getName());
                    } catch (Exception e) {
                        for (Schema s : finishedSchemas) {
                            pgManageMapper.deleteTable(s.getTable().getName());
                            tableManageMapper.delRecord(s.getId());
                        }
                        throw new MyException(-99, "请检查配置文件schema" + schema.getId() + " structure标签");
                    }
                    try {
                        tableManageMapper.addRecord(schema.getId(), table.getName(), schema.getType(), table.getType());
                    } catch (Exception e) {
                        pgManageMapper.deleteTable(table.getName());
                        for (Schema s : finishedSchemas) {
                            pgManageMapper.deleteTable(s.getTable().getName());
                            tableManageMapper.delRecord(s.getId());
                        }
                        throw new MyException(-99, "请检查配置文件schema" + schema.getId());
                    }
                    finishedSchemas.add(schema);
                }
            }
        }
        Device device = new Device(deviceXmlConfig.getId(), name, lon, lat, stationId, description, avatar);
        deviceMapper.addDevice(device);
        return device.getId();
    }
}
