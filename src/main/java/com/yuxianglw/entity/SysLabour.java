package com.yuxianglw.entity;

import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工 
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class SysLabour implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
      @TableField("ID")
    private String id;

      /**
     * 姓名
     */
      @TableField("NAME")
    private String name;

      /**
     * 性别
     */
      @TableField("SEX")
    private String sex;

      /**
     * 年龄
     */
      @TableField("AGE")
    private Integer age;

      /**
     * 身份证
     */
      @TableField("IDCARD")
    private String idcard;

      /**
     * 手机号
     */
      @TableField("PHONE")
    private String phone;

      /**
     * 所属人
     */
      @TableField("BELONG")
    private String belong;

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
