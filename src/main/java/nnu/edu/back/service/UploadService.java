package nnu.edu.back.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/02/16/14:15
 * @Description:
 */
public interface UploadService {
    String uploadPackage(MultipartFile file);

    void uploadParamScript(MultipartFile file, String deviceId, String schemaId);

    String uploadPicture(MultipartFile file);
}
