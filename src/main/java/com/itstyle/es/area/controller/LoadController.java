package com.itstyle.es.area.controller;


import com.itstyle.es.area.dao.Result;
import com.itstyle.es.area.service.UpAndDownLoadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/LoadController")
public class LoadController {

    @Resource
    private UpAndDownLoadService upAndDownLoadService;


    @ApiOperation("上传")
    @PostMapping("/upload")
    @ResponseBody
    public Result upLoadFile(HttpServletRequest httpServletRequest, MultipartFile file) {
        Result result = new Result();
        if (file == null) {
            result.setResult(false);
            result.setMsg("无上传内容");
            return result;
        }
        result = upAndDownLoadService.upLoad(file);
        return result;
    }

    @ApiOperation("下载")
    @GetMapping  ("/download")
    @ResponseBody
    public Result downLoadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam String fileName) {
        Result result = new Result();
        if (fileName == ""||fileName == null){
            result.setResult(false);
            result.setMsg("文件名不能为空");
            return result;
        }
        result = upAndDownLoadService.downLoad(response,fileName);
        return result;
    }
}
