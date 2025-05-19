package com.aigc.service;


import com.aigc.result.Result;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ：jiang
 * @date ：2024/4/22 15:50
 * @description ：AIGC检测接口
 */
public interface CheckService {
    /**
     * 文本检测
     * @param file
     * @return
     */
    Result checkText(MultipartFile file,String value) throws IOException;

    /**
     * 图片检测
     * @param file
     * @return
     */
    Result checkImage(MultipartFile file) throws IOException;
}
