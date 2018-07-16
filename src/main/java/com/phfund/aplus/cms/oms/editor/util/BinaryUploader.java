package com.phfund.aplus.cms.oms.editor.util;  
import com.baidu.ueditor.PathFormat;  
import com.baidu.ueditor.define.AppInfo;  
import com.baidu.ueditor.define.BaseState;  
import com.baidu.ueditor.define.FileType;  
import com.baidu.ueditor.define.State;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;  
import org.springframework.web.multipart.MultipartHttpServletRequest;  
  
import javax.servlet.http.HttpServletRequest;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.Arrays;  
import java.util.List;  
import java.util.Map;  
  @Component
public class BinaryUploader {  
	private static  String  imagephysicalPath;
	
	private static String  imagePath;
	
	
  
    public  final static State save(HttpServletRequest request,  
            Map<String, Object> conf) {  
    	imagePath=getImagephysicalPath();
        if (!ServletFileUpload.isMultipartContent(request)) {  
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);  
        }  
  
        try {  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());  
            if(multipartFile==null){  
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);  
            }  
  
            String savePath = (String) conf.get("savePath");  
            //String originFileName = fileStream.getName();  
            String originFileName = multipartFile.getOriginalFilename();  
            String suffix = FileType.getSuffixByFilename(originFileName);  
  
            originFileName = originFileName.substring(0,  
                    originFileName.length() - suffix.length());  
            savePath = savePath + suffix;  
  
            long maxSize = ((Long) conf.get("maxSize")).longValue();  
  
            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {  
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);  
            }  
  
            savePath = PathFormat.parse(savePath, originFileName);  
  
          //  String physicalPath = (String) conf.get("rootPath") + savePath;  
            String physicalPath =  imagephysicalPath+savePath;
  
            //InputStream is = fileStream.openStream();  
            InputStream is = multipartFile.getInputStream();  
            State storageState = StorageManager.saveFileByInputStream(is,  
                    physicalPath, maxSize);  
            is.close();  
  
            if (storageState.isSuccess()) {  
                storageState.putInfo("url", PathFormat.format(savePath));  
                storageState.putInfo("type", suffix);  
                storageState.putInfo("original", originFileName + suffix);  
            }  
  
            return storageState;  
        // } catch (FileUploadException e) {  
        //  return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);  
        } catch (IOException e) {  
        }  
        return new BaseState(false, AppInfo.IO_ERROR);  
    }  
  
    private static boolean validType(String type, String[] allowTypes) {  
        List<String> list = Arrays.asList(allowTypes);  
  
        return list.contains(type);  
    }

	public static String getImagephysicalPath() {
		return imagephysicalPath;
	}
	@Value("${imagephysicalPath}")
	public void setImagephysicalPath(String imagephysicalPath) {
		BinaryUploader.imagephysicalPath = imagephysicalPath;
	}

}  