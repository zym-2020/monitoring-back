package nnu.edu.back.dao.manage;

import nnu.edu.back.pojo.support.deviceconfig.Col;
import nnu.edu.back.pojo.support.deviceconfig.Structure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/15/16:33
 * @Description:
 */
@Repository
public interface TableManageMapper {
    List<Map<String, Object>> findAllTablesInfo();

    void addRecord(@Param("id") String id, @Param("tableName") String tableName, @Param("type") String type, @Param("databaseType") String databaseType);

    void delRecord(@Param("id") String id);
}
