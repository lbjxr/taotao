package com.taotao.service;

import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传服务
 */
@Service
public class PictureServiceImpl implements PictureService {

    //加载ftp参数
    @Value("$(FTP_ADDRESS)")
    private String FTP_ADDRESS;
    @Value("$(FTP_PORT)")
    private Integer FTP_PORT;
    @Value("$(FTP_USERNAME)")
    private String FTP_USERNAME;
    @Value("$(FTP_PASSWORD)")
    private String FTP_PASSWORD;
    @Value("$(FTP_BASE_PATH)")
    private String FTP_BASE_PATH;
    @Value("$(IMAGES_BASE_URL)")
    private String IMAGES_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {

        Map resultMap = new HashMap();
        try {
            //生成一个新的文件名
            //获取原始文件名
            String oldName = uploadFile.getOriginalFilename();

            //生成新的文件名
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));

            String imagePath = new DateTime().toString("yyyy/MM/dd");
            //图片上传
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS,FTP_PORT,FTP_USERNAME,FTP_PASSWORD,FTP_BASE_PATH,
                    imagePath, newName,uploadFile.getInputStream());

            //返回结果
            if(!result){
                resultMap.put("error", 1);
                resultMap.put("message", "图片上传失败");
                return resultMap;
            }

            resultMap.put("error", 0);
            resultMap.put("url", IMAGES_BASE_URL + imagePath + "/" + newName);
            return resultMap;
        } catch (IOException e) {
            resultMap.put("error", 1);
            resultMap.put("message", "上传文件发生异常");
            return resultMap;
        }
    }
}
