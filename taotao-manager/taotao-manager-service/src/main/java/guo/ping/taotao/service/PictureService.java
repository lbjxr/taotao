package guo.ping.taotao.service;

import guo.ping.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 图片文件上传
 */
public interface PictureService {

    Map uploadPicture(MultipartFile picFile);
}
