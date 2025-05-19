package com.aigc;

import com.aigc.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ：jiang
 * @date ：2024/4/26 17:17
 * @description ：TODO
 */
@SpringBootTest
public class idWorkTest {
    @Autowired
    private IdWorker idWorker;
    /**
     * @desc
     */
    @Test
    public void test(){
        System.out.println(idWorker.nextId());
    }
}
