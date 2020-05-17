package com.zhiming.travel.picture.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author HuangZhiMing
 */
@Data
public class PictureDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    private Long pictureId;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String url;

    /**
     * 图片宽度（单位：像素）
     */
    private Integer width;

    /**
     * 图片高度（单位：像素）
     */
    private Integer height;

    /**
     * 图片大小（单位：KB）
     */
    private Long size;

    /**
     * 状态（0-正常 1-封禁）
     */
    private Integer status;

    /**
     * 是否被删除（0-否 1-是）
     */
    private Integer isDelete;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}