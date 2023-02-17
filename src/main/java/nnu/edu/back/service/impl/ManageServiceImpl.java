package nnu.edu.back.service.impl;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.common.utils.ProcessUtil;
import nnu.edu.back.pojo.support.deviceconfig.DeviceXmlConfig;
import nnu.edu.back.pojo.support.deviceconfig.Param;
import nnu.edu.back.pojo.support.deviceconfig.Path;
import nnu.edu.back.pojo.support.deviceconfig.Schema;
import nnu.edu.back.pojo.support.param.ParamScript;
import nnu.edu.back.service.ManageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/14:40
 * @Description:
 */
@Service
public class ManageServiceImpl implements ManageService {
    @Value("${pythonCMD}")
    String pythonCMD;

    @Value("${tempPath}")
    String tempPath;

    @Value("${binPath}")
    String binPath;

    @Value("pgUser")
    String pgUser;

    @Value("pgPassword")
    String pgPassword;

    @Value("pgHost")
    String pgHost;

    @Value("pgPort")
    String pgPort;



    @Override
    public int checkXMLConfig(String folderName) {
        File file = new File(tempPath + folderName + "/config.xml");
        if (!file.exists()) {
            throw new MyException(ResultEnum.NO_OBJECT);
        }
        if (FileUtil.parseXML(tempPath + folderName + "/config.xml") != null) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String testByParam(JSONObject jsonObject) {
        String schemaId = jsonObject.getString("schemaId");
        String deviceId = jsonObject.getString("deviceId");
        String filePath = binPath + deviceId + "/config.xml";
        if (!new File(filePath).exists()) {
            throw new MyException(-99, "请检查deviceId");
        }
        DeviceXmlConfig deviceXmlConfig = FileUtil.parseXML(filePath);
        List<Param> params = null;
        Path path = null;
        for (Schema schema : deviceXmlConfig.getSchemas()) {
            if (schema.getId().equals(schemaId)) {
                params = schema.getPath().getTestParams();
                path = schema.getPath();
                break;
            }
        }
        if (params == null || path == null) {
            throw new MyException(-99, "请检查schemaId");
        }
        String cmdStr = "";
        for (Param param : params) {
            cmdStr = cmdStr + " " + paramMatch(jsonObject, param);
        }
        List<String> commands = new ArrayList<>();
        commands.add("cmd");
        commands.add("/c");
        switch (path.getScript()) {
            case "python":
                cmdStr = pythonCMD + " " + binPath + deviceId + "/script/" + schemaId + "/" + path.getMain() + " test" + cmdStr;
                commands.add(cmdStr);
                break;
        }
        try {
            Process process = ProcessUtil.exeProcess(commands);
            String result = ProcessUtil.readProcessString(process.getInputStream());
            int state = process.exitValue();
            if (state == 0) {
                return result;
            } else {
                throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
    }

    private String paramMatch(JSONObject jsonObject, Param param) {
        if (param.getSys()) {
            switch (param.getValue()) {
                case "pgUser":
                    return pgUser;
                case "pgPassword":
                    return pgPassword;
                case "pgHost":
                    return pgHost;
                case "pgPort":
                    return pgPort;
                default:
                    throw new MyException(-99, "请检查配置文件param参数");
            }
        } else {
            return jsonObject.getString(param.getName());
        }
    }

    private String paramMatch(String textPath, Param param) {
        Map<String, String> map = FileUtil.paramParamResult(textPath);
        if (param.getSys()) {
            switch (param.getValue()) {
                case "pgUser":
                    return pgUser;
                case "pgPassword":
                    return pgPassword;
                case "pgHost":
                    return pgHost;
                case "pgPort":
                    return pgPort;
                default:
                    throw new MyException(-99, "请检查配置文件param参数");
            }
        } else {
            return map.get(param.getName());
        }
    }

    @Override
    public String testByScript(String deviceId, String schemaId) {
        String paramPath = binPath + deviceId + "/param/" + schemaId + "/param.conf";
        ParamScript paramScript = FileUtil.parseParamXML(paramPath);
        if (paramScript == null) {
            throw new MyException(-99, "请检查param.conf文件");
        }
        String main = paramScript.getMain();
        List<String> commands = new ArrayList<>();
        commands.add("cmd");
        commands.add("/c");
        switch (paramScript.getScript()) {
            case "python":
                commands.add("cd " + binPath + deviceId + "/param/" + schemaId + " & " + pythonCMD + " " + main + " test");
                break;
        }
        String result = "";
        try {
            Process process = ProcessUtil.exeProcess(commands);

            int state = process.waitFor();
            if (state == 0) {
                String resultPath = binPath + deviceId + "/param/" + schemaId + "/" + paramScript.getResult();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", deviceId);
                jsonObject.put("schemaId", schemaId);
                for (Param param : paramScript.getTest()) {
                    if (!param.getSys()) {
                        jsonObject.put(param.getName(), paramMatch(resultPath, param));
                    }
                }
                result += testByParam(jsonObject);
                return result;
            } else {
                throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }

    }

    @Override
    public void executeByParam(JSONObject jsonObject) {
        String schemaId = jsonObject.getString("schemaId");
        String deviceId = jsonObject.getString("deviceId");
        String filePath = binPath + deviceId + "/config.xml";
        if (!new File(filePath).exists()) {
            throw new MyException(-99, "请检查deviceId");
        }
        DeviceXmlConfig deviceXmlConfig = FileUtil.parseXML(filePath);
        List<Param> params = null;
        Path path = null;
        for (Schema schema : deviceXmlConfig.getSchemas()) {
            if (schema.getId().equals(schemaId)) {
                params = schema.getPath().getTestParams();
                path = schema.getPath();
                break;
            }
        }
        if (params == null || path == null) {
            throw new MyException(-99, "请检查schemaId");
        }
        String cmdStr = "";
        for (Param param : params) {
            cmdStr = cmdStr + " " + paramMatch(jsonObject, param);
        }
        List<String> commands = new ArrayList<>();
        commands.add("cmd");
        commands.add("/c");
        switch (path.getScript()) {
            case "python":
                cmdStr = pythonCMD + " " + binPath + deviceId + "/script/" + schemaId + "/" + path.getMain() + " execute" + cmdStr;
                commands.add(cmdStr);
                break;
        }
        try {
            Process process = ProcessUtil.exeProcess(commands);
            int state = process.exitValue();
            if (state != 0) {
                throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
    }

    @Override
    public void executeByScript(String deviceId, String schemaId) {
        String paramPath = binPath + deviceId + "/param/" + schemaId + "/param.conf";
        ParamScript paramScript = FileUtil.parseParamXML(paramPath);
        if (paramScript == null) {
            throw new MyException(-99, "请检查param.conf文件");
        }
        String main = paramScript.getMain();
        List<String> commands = new ArrayList<>();
        commands.add("cmd");
        commands.add("/c");
        switch (paramScript.getScript()) {
            case "python":
                commands.add("cd " + binPath + deviceId + "/param/" + schemaId + " & " + pythonCMD + " " + main + " execute");
                break;
        }
        try {
            Process process = ProcessUtil.exeProcess(commands);
            int state = process.waitFor();
            if (state == 0) {
                String resultPath = binPath + deviceId + "/param/" + schemaId + "/" + paramScript.getResult();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", deviceId);
                jsonObject.put("schemaId", schemaId);
                for (Param param : paramScript.getTest()) {
                    if (!param.getSys()) {
                        jsonObject.put(param.getName(), paramMatch(resultPath, param));
                    }
                }
                executeByParam(jsonObject);
            } else {
                throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
    }
}
