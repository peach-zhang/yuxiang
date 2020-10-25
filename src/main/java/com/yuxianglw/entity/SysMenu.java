package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单 
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("SYS_MENU")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId("ID")
      private String id;

      /**
     * 标题
     */
      @TableField("TITLE")
    private String title;

      /**
     * 地址
     */
      @TableField("URL")
    private String url;

      /**
     * 父级菜单 父级菜单的id
     */
      @TableField("PARENT")
    private String parent;

      /**
     * 图标
     */
      @TableField("ICON")
    private String icon;

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
    private LocalDateTime updatedTime;


}
