package com.alice.blog.module.demo.controller;

import com.alice.blog.module.demo.detail.FeignTestDTO;
import com.alice.blog.module.demo.detail.FeignTestDetailDTO;
import com.alice.support.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/10/27 0027$ 10:43$
 */
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class DemoController {

    @PostMapping(value = "/demo")
    public ResponseInfo<String> demoTest(@RequestBody FeignTestDTO feignTestDTO) {
        List<FeignTestDetailDTO> feignTestDetailDTOList = feignTestDTO.getFeignTestDetailDTOList();
//        for (FeignTestDetailDTO feignTestDetailDTO : feignTestDetailDTOList) {
//            System.out.println(feignTestDetailDTO);
//        }
        System.out.println("blog服务访问成功！");
        return ResponseInfo.success("blog服务访问成功！");
    }

}
