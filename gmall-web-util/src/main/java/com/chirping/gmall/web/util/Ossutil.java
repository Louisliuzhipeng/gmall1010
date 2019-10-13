package com.chirping.gmall.web.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 刘志鹏
 * @date $date$
 */
public class Ossutil {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private final static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private final static String accessKeyId = "LTAI4FcFr5tfvJoJpCbZ9vAq";
    private final static String accessKeySecret = "HjwYieAiZJwyQJWpcJeErP3uzSODCZ";

    public static String saveimg(String type, String fileName, InputStream inputStream) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //图片名称
            fileName = type + "/" + UUID.randomUUID() + fileName;
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
            ossClient.putObject("gmall1010", fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "上传失败";
        }
        return null;
    }
}
