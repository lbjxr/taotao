package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {

    List<TbContent> getContentList(long contentCid);
}
