package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录背景图片
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-01
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("SYS_BACKGROUND_IMAGE")
public class SysBackgroundImage implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId("ID")
      private String id;

      /**
     * 背景图片地址
     */
      @TableField("URL")
    private String url;

      /**
     * 名言警句
     */
      @TableField("TXT")
    private String txt;

      /**
     * 是否删除
     */
      @TableField("DEL_FLAG")
    @TableLogic
    private String delFlag;

      /**
     * 乐观锁
     */
      @TableField("REVISION")
    @Version
    private Integer revision;

      /**
     * 创建人
     */
      @TableField("CREATED_BY")
    private String createdBy;

      /**
     * 创建时间
     */
      @TableField("CREATED_TIME")
      @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createdTime;

      /**
     * 更新人
     */
      @TableField("UPDATED_BY")
    private String updatedBy;

      /**
     * 更新时间
     */
      @TableField("UPDATED_TIME")
      @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updatedTime;


}
