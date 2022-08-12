package com.risen.helper.file;

import com.risen.helper.config.FileConfig;
import com.risen.helper.config.SwitchConfig;
import com.risen.helper.constant.Symbol;
import com.risen.helper.exception.BusinessException;
import com.risen.helper.util.LogUtil;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/5 14:53
 */
@Component
public class FileUploadUtil implements InitializingBean {

    private MinioClient minioClient;

    private FileConfig fileConfig;

    private SwitchConfig switchConfig;

    @Override
    public void afterPropertiesSet() {
        if(switchConfig.getFileOpenWitch()) {
            if (ObjectUtils.isEmpty(minioClient)) {
                try {
                    if(fileConfig.getAuth()) {
                        minioClient = new MinioClient(fileConfig.getEndpoint(), fileConfig.getPort(), fileConfig.getAccessKey(), fileConfig.getSecretKey());
                    }
                    else{
                        minioClient = new MinioClient(fileConfig.getEndpoint());
                    }
                } catch (Exception e) {
                    LogUtil.error("init MinioClient error:{}", e.toString());
                }
            }
        }
    }

    public FileUploadUtil(FileConfig fileConfig,SwitchConfig switchConfig) {
        this.fileConfig = fileConfig;
        this.switchConfig=switchConfig;
    }


    private boolean createBucketIfNotExists() {
        synchronized (minioClient) {
            try {
                if (!minioClient.bucketExists(fileConfig.getBucketName())) {
                    try {
                        minioClient.makeBucket(fileConfig.getBucketName());
                    } catch (Exception e) {
                        LogUtil.error("makeBucket error:{}", e.getMessage());
                        return false;
                    }
                }

              return true;
            } catch (Exception e) {
                LogUtil.error("bucketExists error:{}", e.toString());
            }
            return false;
        }
    }


    private String getFileUrl(String saveFileName) {
        synchronized (minioClient) {
            try {
                return minioClient.getObjectUrl(fileConfig.getBucketName(), saveFileName);
            } catch (Exception e) {
                throw new BusinessException("获取图片地址失败", e);
            }
        }
    }

    public String uploadImage(MultipartFile file, String fileName) {
        if (createBucketIfNotExists()) {
            synchronized (minioClient) {
                try {
                    PutObjectOptions options = new PutObjectOptions(file.getSize(), -1);
                    String saveFileName = getSaveFileName(file, fileName);
                    minioClient.putObject(fileConfig.getBucketName(), saveFileName, file.getInputStream(), options);
                    return getFileUrl(saveFileName);
                } catch (Exception e) {
                    throw new BusinessException("上传图片失败", e);
                }
            }

        } else {
            throw new BusinessException("上传图片失败，桶配置有问题");
        }
    }


    private String getSaveFileName(MultipartFile file, String fileName) {
        return String.format("%s.%s", fileName, getFileSuffix(file));
    }

    public String getFileSuffix(MultipartFile file) {
        String fileAllName = file.getResource().getFilename();
        return fileAllName.substring(fileAllName.indexOf(Symbol.SYMBOL_POINT)+1);
    }


}
