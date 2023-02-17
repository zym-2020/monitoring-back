package nnu.edu.back.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/14:40
 * @Description:
 */
public interface ManageService {
    int checkXMLConfig(String folderName);

    String testByParam(JSONObject jsonObject);

    String testByScript(String deviceId, String schemaId);

    void executeByParam(JSONObject jsonObject);

    void executeByScript(String deviceId, String schemaId);
}
