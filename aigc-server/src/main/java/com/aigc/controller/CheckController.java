package com.aigc.controller;

import com.aigc.result.Result;
import com.aigc.service.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ：jiang
 * @date ：2024/4/22 15:37
 * @description ：AIGC检测控制器
 */
@RequestMapping("/check")
@RestController
@Slf4j
@CrossOrigin
public class CheckController {

    @Autowired
    private CheckService checkService;

    /**
     * 文本检测
     * @param file
     * @return
     */
    @PostMapping("/text")
    public Result checkText(@RequestParam("file") MultipartFile file,@RequestParam("model") String model) throws IOException {
        return checkService.checkText(file,model);
    }

    /**
     * 图片检测
     * @return
     */
    @PostMapping("/image")
    public Result checkImage(@RequestParam("file") MultipartFile file) throws IOException {
        return checkService.checkImage(file);
    }

    /**
     * 视频检测
     * @return
     */
    @PostMapping("/video")
    public Result checkVideo(){
        return Result.success();
    }
}
