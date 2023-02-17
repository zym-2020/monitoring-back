package nnu.edu.back.dao.main;

import nnu.edu.back.pojo.support.deviceconfig.Col;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/15:49
 * @Description:
 */
@Repository
public interface PGManageMapper {
    void createTable(@Param("columns") List<Col> columns, @Param("constraints") List<String> constraints, @Param("tableName") String tableName);

    void deleteTable(@Param("tableName") String tableName);
}
