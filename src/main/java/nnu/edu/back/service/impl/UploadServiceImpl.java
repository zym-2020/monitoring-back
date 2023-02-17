package nnu.edu.back.service.impl;

import nnu.edu.back.common.utils.FileUtil;
import nnu.edu.back.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/14:16
 * @Description:
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Value("${tempPath}")
    String tempPath;

    @Value("${binPath}")
    String binPath;

    @Value("${picturePath}")
    String picturePath;

    @Override
    public String uploadPackage(MultipartFile file) {
        long timestamp = System.currentTimeMillis();
        String path = tempPath + "package" + timestamp + ".zip";
        FileUtil.uploadFile(file, path);
        FileUtil.unpack(path, tempPath + "package" + timestamp);
        return "package" + timestamp;
    }

    @Override
    public void uploadParamScript(MultipartFile file, String deviceId, String schemaId) {
        long timestamp = System.currentTimeMillis();
        String path = tempPath + "param" + timestamp + ".zip";
        FileUtil.uploadFile(file, path);
        File f = new File(binPath + deviceId + "/param");
        if (!f.exists()) {
            f.mkdirs();
        }
        FileUtil.unpack(path, binPath + deviceId + "/param/" + schemaId);
    }

    @Override
    public String uploadPicture(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String path = picturePath + uuid + suffix;
        FileUtil.uploadFile(file, path);
        return uuid + suffix;
    }
}
