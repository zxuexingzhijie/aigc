package com.aigc.config;

import com.aigc.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：jiang
 * @date ：2024/4/16 20:41
 * @description ：定义公共配置类
 */
@Configuration
public class CommonConfig {

    /**
     * 雪花算法Bean，保证生成的ID唯一
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        // 基于运维人员对机房和机器的编号规划自行约定
        /**
         * 参数1：机器ID
         * 参数2：机房ID
         */
        return new IdWorker(1l,2l);
    }


}
