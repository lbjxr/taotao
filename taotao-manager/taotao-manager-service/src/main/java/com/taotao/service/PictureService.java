package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PictureService {

    Map uploadPicture(MultipartFile uploadFile) throws IOException;
}
