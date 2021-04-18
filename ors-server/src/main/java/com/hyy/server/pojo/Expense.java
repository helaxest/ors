package com.hyy.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author helaxest
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_expense")
@ApiModel(value="Expense对象", description="")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "报销单号")
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "类别")
    @ApiModelProperty(value = "类别")
    private String category;

    @Excel(name = "标题")
    @ApiModelProperty(value = "标题")
    private String title;

    @Excel(name = "部门名")
    @ApiModelProperty(value = "部门名")
    private String depName;

    @Excel(name = "金额")
    @ApiModelProperty(value = "金额")
    private Integer money;

    @Excel(name = "内容")
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @Excel(name = "申请人的id")
    @ApiModelProperty(value = "申请人的id")
    private Integer applyId;

    @Excel(name = "申请人姓名")
    @ApiModelProperty(value = "申请人姓名")
    private String applyName;

    @Excel(name = "处理人的id")
    @ApiModelProperty(value = "处理人的id")
    private Integer dealId;

    @Excel(name = "处理人姓名")
    @ApiModelProperty(value = "处理人姓名")
    private String dealName;

    @ApiModelProperty(value = "处理方式")
    private String dealWay;

    @ApiModelProperty(value = "处理结果")
    private String dealResult;


    @ApiModelProperty(value = "附件")
    private String attachment;

    @Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "创建日期")
    private LocalDate createDate;

    @Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "更新日期")
    private LocalDate updateDate;



}
