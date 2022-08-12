package com.risen.helper.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/5 23:49
 */
public interface FileType {
    //bmp、png、jpg、tiff、gif、pcx、tga、exif、fpx、svg、psd、cdr、pcd、dxf、ufo、eps、ai、raw
    Set<String> imageType = new HashSet<String>() {{
        add("jpg");
        add("png");
    }};
}
