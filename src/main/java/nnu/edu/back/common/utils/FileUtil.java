package nnu.edu.back.common.utils;

import net.lingala.zip4j.ZipFile;
import nnu.edu.back.common.exception.MyException;
import nnu.edu.back.common.result.ResultEnum;
import nnu.edu.back.pojo.support.deviceconfig.DeviceXmlConfig;
import nnu.edu.back.pojo.support.deviceconfig.*;
import nnu.edu.back.pojo.support.param.ParamScript;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/9:34
 * @Description:
 */
public class FileUtil {
    public static DeviceXmlConfig parseXML(String filePath) {

        try {
            SAXReader saxReader = new SAXReader();
            DeviceXmlConfig deviceXmlConfig = new DeviceXmlConfig();
            Document document = saxReader.read(new File(filePath));
            Element rootElement = document.getRootElement();
            deviceXmlConfig.setId(rootElement.attributeValue("id"));
            deviceXmlConfig.setName(rootElement.attributeValue("name"));
            deviceXmlConfig.setLon(Double.parseDouble(rootElement.attributeValue("lon")));
            deviceXmlConfig.setLat(Double.parseDouble(rootElement.attributeValue("lat")));
            deviceXmlConfig.setStation(rootElement.attributeValue("station"));
            deviceXmlConfig.setDepartment(rootElement.attributeValue("department"));
            Element des = rootElement.element("description");
            deviceXmlConfig.setDescription(des.getStringValue());
            deviceXmlConfig.setSchemas(new ArrayList<>());

            Element schemas = rootElement.element("schemas");
            Iterator iterator = schemas.elementIterator();
            while (iterator.hasNext()) {
                Element schemaElement = (Element) iterator.next();
                Schema schema = new Schema();
                schema.setId(schemaElement.attributeValue("id"));
                schema.setSource(schemaElement.attributeValue("source"));
                schema.setType(schemaElement.attributeValue("type"));
                List<Element> elements = schemaElement.elements();
                for (Element e : elements) {
                    if (e.getName().equals("table")) {
                        Table table = new Table();
                        table.setName(e.attributeValue("name"));
                        table.setType(e.attributeValue("type"));
                        table.setDatabase(e.attributeValue("database"));
                        Structure structure = new Structure(new ArrayList<>());
                        Element structureElement = e.element("structure");
                        List<Element> cols = structureElement.elements();
                        for (Element colElement : cols) {
                            Col col = new Col();
                            col.setId(colElement.attributeValue("id"));
                            col.setType(colElement.attributeValue("type"));
                            col.setKey(colElement.element("key").getStringValue());
                            col.setConstraints(new ArrayList<>());
                            if (colElement.element("constraints") != null) {
                                List<Element> constraints = colElement.element("constraints").elements();
                                for (Element constraintElement : constraints) {
                                    col.getConstraints().add(constraintElement.getStringValue());
                                }
                            }
                            structure.getCols().add(col);
                        }
                        table.setStructure(structure);
                        table.setConstraints(new ArrayList<>());
                        if (e.element("constraints") != null) {
                            List<Element> constraintList = e.element("constraints").elements();
                            for (Element element : constraintList) {
                                table.getConstraints().add(element.getStringValue());
                            }
                        }
                        schema.setTable(table);
                    } else if (e.getName().equals("files")) {
                        Files files = new Files();
                        files.setSuffix(e.element("suffix").getStringValue());
                        files.setTimeScale(e.element("timeScale").getStringValue());
                        files.setTimeInterval(e.element("timeInterval").getStringValue());
                        schema.setFiles(files);
                    } else if (e.getName().equals("path")) {
                        Path path = new Path();
                        path.setMain(e.element("main").getStringValue());
                        path.setType(e.attributeValue("type"));
                        path.setScript(e.attributeValue("script"));
                        path.setTestParams(new ArrayList<>());
                        path.setExecuteParams(new ArrayList<>());
                        if (e.element("test").element("input") != null) {
                            List<Element> params = e.element("test").element("input").element("params").elements();
                            for (Element paramElement : params) {
                                path.getTestParams().add(new Param(paramElement.getStringValue(), Boolean.valueOf(paramElement.attributeValue("sys")), paramElement.attributeValue("value")));
                            }
                        }
                        if (e.element("execute").element("input") != null) {
                            List<Element> params = e.element("execute").element("input").element("params").elements();
                            for (Element paramElement : params) {
                                path.getExecuteParams().add(new Param(paramElement.getStringValue(), Boolean.valueOf(paramElement.attributeValue("sys")), paramElement.attributeValue("value")));
                            }
                        }
                        schema.setPath(path);
                    }
                }
                deviceXmlConfig.getSchemas().add(schema);
            }
            return deviceXmlConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ParamScript parseParamXML(String filePath) {
        try {
            SAXReader saxReader = new SAXReader();
            ParamScript paramScript = new ParamScript();
            Document document = saxReader.read(new File(filePath));
            Element rootElement = document.getRootElement();
            paramScript.setScript(rootElement.attributeValue("script"));
            paramScript.setMain(rootElement.element("main").getStringValue());
            paramScript.setResult(rootElement.element("result").getStringValue());
            paramScript.setTest(new ArrayList<>());
            paramScript.setExecute(new ArrayList<>());
            if (rootElement.element("test") != null) {
                List<Element> elements = rootElement.element("test").elements();
                for (Element element : elements) {
                    Param param = new Param();
                    param.setName(element.getStringValue());
                    param.setSys(Boolean.valueOf(element.attributeValue("sys")));
                    param.setValue(element.attributeValue("value"));
                    paramScript.getTest().add(param);
                }
            }
            if (rootElement.element("execute") != null) {
                List<Element> elements = rootElement.element("execute").elements();
                for (Element element : elements) {
                    Param param = new Param();
                    param.setName(element.getStringValue());
                    param.setSys(Boolean.valueOf(element.attributeValue("sys")));
                    param.setValue(element.attributeValue("value"));
                    paramScript.getExecute().add(param);
                }
            }
            return paramScript;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> paramParamResult(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                map.put(tempString.substring(0, tempString.lastIndexOf(" ")), tempString.substring(tempString.lastIndexOf(" ")));
            }
            reader.close();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void uploadFile(MultipartFile multipartFile, String path) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            outputStream = new FileOutputStream(path);
            int len;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
    }

    public static void unpack(String destination, String to) {
        File file = new File(destination);
        if (!file.exists()) {
            throw new MyException(ResultEnum.DEFAULT_EXCEPTION);
        }
        ZipFile zipFile = new ZipFile(destination);
        try {
            zipFile.extractAll(to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int moveFile(String destination, String to) {
        File folder = new File(destination);
        File toFolder = new File(to);
        if (!folder.exists() || !folder.isDirectory()) {
            return -1;
        }
        if (toFolder.exists()) {
            deleteFolderAndFile(to);
        }
        toFolder.mkdirs();
        folder.renameTo(toFolder);
        return 0;
    }

    public static boolean deleteFolderAndFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return false;
        } else {
            if(file.isFile()) {
                return deleteFile(path);
            } else {
                return deleteDirectory(path);
            }
        }
    }

    private static boolean deleteFile(String path) {
        File file = new File(path);
        if(file.exists() && file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    private static boolean deleteDirectory(String path) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for(File f : files) {
            if(f.isFile()) {
                flag = deleteFile(f.getAbsolutePath());
                if(!flag) break;
            } else {
                flag = deleteDirectory(f.getAbsolutePath());
                if(!flag) break;
            }
        }
        if(!flag) return false;

        if(dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
