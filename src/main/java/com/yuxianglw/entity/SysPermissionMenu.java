package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限菜单表
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("SYS_PERMISSION_MENU")
public class SysPermissionMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId("ID")
      private String id;

      /**
     * 权限ID
     */
      @TableField("PERMISSION_ID")
    private String permissionId;

      /**
     * 菜单ID
     */
      @TableField("MENU_ID")
    private String menuId;

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
