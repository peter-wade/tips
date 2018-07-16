package com.phfund.aplus.cms.oms.modular.article.controller;


import com.phfund.aplus.cms.oms.editor.util.ActionEnter;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ldb on 2017/4/9.
 */
@RestController
public class UeditorController {
    private static final Logger LOG = LoggerFactory.getLogger(UeditorController.class);
    @RequestMapping(value="/static/ueditor/config")
    public void config(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
//        map.put("savePath", "");
//        map.put("rootPath", "D:\filepath1");
//        Uploader up=new Uploader(request,map);
      //  up.doExec();
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error("",e);
        }

    }
}
