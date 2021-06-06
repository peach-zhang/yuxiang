package com.yuxianglw.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@ExcelTarget("人员名单")
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
      @NotNull(message = "姓名不可为空！")
      @Excel(name = "姓名",width = 10.0)
    private String name;

      /**
     * 性别
     */
      @TableField("SEX")
      @NotNull(message = "姓别不可为空！")
      @Excel(name = "性别",width = 10.0)
    private String sex;

      /**
     * 年龄
     */
      @TableField("AGE")
      @Excel(name = "年龄",width = 10.0)
    private Integer age;

      /**
     * 身份证
     */
      @TableField("IDCARD")
      @NotNull(message = "身份证不可为空！")
      @Excel(name = "身份证",width = 25.0)
    private String idcard;

      /**
     * 手机号
     */
      @TableField("PHONE")
      @NotNull(message = "手机号不可为空！")
      @Excel(name = "手机号",width = 15.0)
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
