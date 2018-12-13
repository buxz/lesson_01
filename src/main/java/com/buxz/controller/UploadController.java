package com.buxz.controller;

import com.buxz.entity.UserEntity;
import com.buxz.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传
 *
 */
@Controller
@RequestMapping("/upload/")
public class UploadController {


    /**
     * 初始文件上传页面
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(){
        return "upload";
    }

    /**
     * 单文件上传
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "one",method = RequestMethod.POST)
    public String one(HttpServletRequest request, MultipartFile file){
        try {
            excuteUpload(request,file);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 单文件上传
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "multi",method = RequestMethod.POST)
    public String multi(HttpServletRequest request, MultipartFile[] file){
        try {
            for (MultipartFile multipartFile : file) {
                excuteUpload(request, multipartFile);
            }
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 校验文件路径
     * @param filePath
     * @return 存在返回true，不存在时直接创建，创建失败返回false
     */
    boolean checkFilePath(String filePath) throws IOException {
        File tempFile = new File(filePath);
        // 文件不存在
        if (tempFile.exists()){
            return true;
        }else {
            if (tempFile.getParentFile().exists()){ // 文件目录存在，直接创建文件
                return tempFile.createNewFile();
            }else {  // 文件目录不存在，先见目录再建文件
                return tempFile.getParentFile().mkdirs() &&  tempFile.createNewFile();
            }
        }
    }

    /**
     * 校验文件夹路径
     * @param folderPath 待校验文件夹路径
     * @return 存在返回true，不存在时直接创建，创建失败返回false
     */
    boolean checkFolderPath(String folderPath) throws IOException {
        File file = new File(folderPath);
        // 文件夹存在，直接返回true 否则 新建文件夹路径
        return file.exists() || file.mkdirs();
    }


    /**
     * 执行文件上传操作
     * @param file 待上传存储文件
     * @param request  用于获取文件路径
     */
    private void excuteUpload(HttpServletRequest request, MultipartFile file) throws IOException {
        // 上传目录地址
        String uploadDir = request.getSession().getServletContext().getRealPath("/")+"upload/";
        // 如果目录不存在就新建目录
        File tempDir = new File(uploadDir);
        if (!tempDir.exists())
            if (!tempDir.mkdirs()){
                System.out.println("文件路径创建失败--"+uploadDir);
            }
        // 文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件名
        String fileName = UUID.randomUUID() + suffix;
        // 服务端保存的文件对象
        File serverFile = new File(uploadDir + fileName);
        file.transferTo(serverFile);
    }


}
