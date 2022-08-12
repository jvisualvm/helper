package com.risen.helper.generator.service;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.risen.helper.constant.Symbol;
import com.risen.helper.generator.dto.GeneratorCodeRequestDTO;
import com.risen.helper.util.DownLoadUtil;
import com.risen.helper.util.MybatisPlusGeneratorUtil;
import com.risen.helper.util.SystemTypeUtil;
import com.risen.helper.util.ZipUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 13:17
 */
@Service
@AllArgsConstructor
public class MybatisPlusGeneratorService {

    public void createCode(GeneratorCodeRequestDTO requestInfo, HttpServletResponse response) {
        NamingStrategy naming = NamingStrategy.underline_to_camel;
        if (!requestInfo.getNaming()) {
            naming = NamingStrategy.no_change;
        }
        String path = MybatisPlusGeneratorUtil.PATH;
        MybatisPlusGeneratorUtil.create(requestInfo.getUrl(), requestInfo.getUserName(),
                requestInfo.getPassword(), requestInfo.getSchema(),
                requestInfo.getTable(), requestInfo.getTablePrefix(),
                path, requestInfo.getPackageName(),
                naming, requestInfo.getAuthor());
        try {
            //对生成的代码进行压缩
            File zipFile = new File(MybatisPlusGeneratorUtil.ZIP_FILEPATH);
            if (!zipFile.exists()) {
                zipFile.mkdir();
            }
            zipFile = new File(MybatisPlusGeneratorUtil.ZIP_FINAL_FILEPATH);
            OutputStream out = new FileOutputStream(zipFile);
            ZipUtil.toZip(path, out, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownLoadUtil.downloadZip(response, MybatisPlusGeneratorUtil.ZIP_FILEPATH, MybatisPlusGeneratorUtil.ZIP_FILENAME);
        //最后需要删除文件夹
        deleteSurplusFile();
    }

    private void deleteSurplusFile() {
        if (SystemTypeUtil.isWindowsSystem()) {
            String winPath = MybatisPlusGeneratorUtil.BASE_PATH.replace(Symbol.SYMBOL_SLASH, Symbol.SYMBOL_RE_SLASH);
            FileUtils.deleteQuietly(new File(winPath));
        } else if (SystemTypeUtil.isLinuxSystem()) {
            FileUtils.deleteQuietly(new File(MybatisPlusGeneratorUtil.BASE_PATH));
        }
    }


}
