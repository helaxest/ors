package com.hyy.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Description
 *  用户登录
 * 专门传递前端的输入用户名和密码,因为Admin中还有许多用不上的参数
 * @author helaxest
 * @date 2021/03/15  10:56
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象",description = "")//swagger2的注解 生成接口文档
public class AdminLoginParam {
    @ApiModelProperty(value = "用户名",required = true)//required = true表示输入必填
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
