package com.risen.helper.util;

import com.risen.helper.constant.Symbol;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 14:26
 */
public class DownLoadUtil {

    public static void downloadZip(HttpServletResponse response, String filePath, String zipFileName) {
        StringBuilder filePathInfo = new StringBuilder(filePath);
        filePathInfo.append(Symbol.SYMBOL_SLASH);
        filePathInfo.append(zipFileName);

        File zipFile = new File(filePathInfo.toString());

        response.setHeader("content-type", "application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFileName.getBytes("utf-8"), "ISO8859-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            bufferedInputStream = new BufferedInputStream(new FileInputStream(zipFile));
            byte[] buf = new byte[1024 * 10];
            int read = 0;
            while ((read = bufferedInputStream.read(buf, 0, 1024 * 10)) != -1) {
                outputStream.write(buf, 0, read);
            }
            outputStream.flush();
            closeAllStream(outputStream, bufferedInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 删除压缩包
            File file = new File(filePathInfo.toString());
            file.delete();
            closeAllStream(outputStream, bufferedInputStream);
        }
    }

    private static void closeAllStream(OutputStream outputStream, BufferedInputStream bufferedInputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
