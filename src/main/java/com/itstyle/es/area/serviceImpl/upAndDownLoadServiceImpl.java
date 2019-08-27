package com.itstyle.es.area.serviceImpl;

import com.itstyle.es.area.dao.Result;
import com.itstyle.es.area.service.UpAndDownLoadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class upAndDownLoadServiceImpl implements UpAndDownLoadService {

    /**
     * 上传文件到指定路径下(src\main\resources)
     *
     * @param file
     * @return
     */
    @Override
    public Result upLoad(MultipartFile file) {
        Result result = new Result();
        result.setResult(true);
        String fileName = file.getOriginalFilename();
        //获取当前项目路径+\src\main\resources
        String path = System.getProperty("user.dir") + "\\src\\main\\resources";
        File savefile = new File(path, fileName);
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(savefile));
            out.write(file.getBytes());
            out.flush();
            out.close();
            result.setMsg("上传成功");
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("上传失败");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("上传失败");
            return result;
        }
    }


    @Override
    public Result downLoad(HttpServletResponse response, String fileName) {
        Result result = new Result();
        result.setResult(true);
        //指定要下载的文件目录
        //暂时写死只能下载resources里的文件
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        File file = new File(path + fileName);
        if (file.exists()) {
            System.out.println(file.getName());
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                response.setContentType("application/x-msdownload;");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                response.setHeader("Content-Length", String.valueOf(file.length()));
                bis = new BufferedInputStream(new FileInputStream(file));
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bis.close();
                bos.close();
                result.setMsg("下载成功");
                result.setResult(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setResult(false);
                result.setMsg("下载失败");
                return result;
            }
        }

        return result;
    }

//    public static void main(String[] args) {
//
//        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\application.properties";
//        File file = new File(path);
//        if (file.exists()) {
//            BufferedInputStream bis = null;
//            BufferedOutputStream bos = null;
//            try {
//                bis = new BufferedInputStream(new FileInputStream(file));
//                byte[] buff = new byte[2048];
//                File file1 = new File("application.properties");
//                OutputStream out = new FileOutputStream(file1);
//                bos = new BufferedOutputStream(out);
//                int bytesRead;
//                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//                    bos.write(buff, 0, bytesRead);
//                    bos.flush();
//                    System.out.println();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}






