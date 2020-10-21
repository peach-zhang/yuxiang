package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志 
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-19
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("SYS_LOG")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
      @TableId("ID")
    private String id;

      /**
     * 类型
     */
      @TableField("TYPE")
    private String type;

      /**
     * 操作人ID
     */
      @TableField("USER_ID")
    private String userId;

      /**
     * 用户名
     */
      @TableField("USER_NAME")
    private String userName;

      /**
     * IP地址
     */
      @TableField("IP")
    private String ip;

      /**
     * 方法
     */
      @TableField("METHOD")
    private String method;

      /**
     * 参数
     */
      @TableField("PARAM")
    private String param;

      /**
     * 结果集
     */
      @TableField("RESULT")
    private String result;

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
