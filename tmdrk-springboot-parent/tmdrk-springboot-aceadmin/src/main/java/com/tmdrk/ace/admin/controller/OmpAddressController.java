package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.service.OmpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * OmpAddressController
 * https://alibaba-easyexcel.github.io/ 官方文档
 * @author Jie.Zhou
 * @date 2021/1/19 16:06
 */
@Controller
public class OmpAddressController {
    @Autowired
    private OmpAddressService ompAddressService;

    @RequestMapping("/parse")
    @ResponseBody
    public String parseAddress(Model model){
        System.out.println("===============");
        ompAddressService.parseAddress();
        return "success";
    }

    @PostMapping("/generate")
    @ResponseBody
    public String generateAddress(@RequestParam("file") MultipartFile file){
        System.out.println("===============");
        ompAddressService.generateAddress(file);
        return "success";
    }

    @GetMapping("/download")
    @ResponseBody
    public String download(){
        System.out.println("===============");
        ompAddressService.download();
        return "success";
    }

    @GetMapping("/cursor")
    @ResponseBody
    public String cursor(){
        System.out.println("===============");
        ompAddressService.selectCursor();
        return "success";
    }
}
