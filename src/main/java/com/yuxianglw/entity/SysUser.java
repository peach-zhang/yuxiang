package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("SYS_USER")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 用户主键
     */
        @TableId("ID")
      private String id;

      /**
     * 登录用户名
     */
      @TableField("USER_NAME")
    private String userName;

      /**
     * 中文名
     */
      @TableField("REAL_NAME")
    private String realName;

      /**
     * 密码
     */
      @TableField("PASS_WORD")
    private String passWord;

      /**
     * 密码盐
     */
      @TableField("SALT")
    private String salt;

      /**
     * 头像
     */
      @TableField("AVATAR")
    private String avatar;

      /**
     * 性别
     */
      @TableField("SEX")
    private String sex;

      /**
     * 手机号
     */
      @TableField("PHONE")
    private String phone;

      /**
     * 邮箱
     */
      @TableField("EMAIL")
    private String email;

      /**
     * 上级ID
     */
      @TableField("SUPERIOR_ID")
    private String superiorId;

      /**
     * 状态
     */
      @TableField("STATUS")
    private String status;

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
