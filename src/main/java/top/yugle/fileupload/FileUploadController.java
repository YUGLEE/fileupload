package top.yugle.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author YUGLE
 */
@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/");
        String format = sdf.format(new Date());
        String path = realPath + format;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder,newName));
            String s =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+format+newName;
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
