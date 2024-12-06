package com.itheima.Servlet.cart.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 用户表
*  
*/
@Data
public class User implements Serializable {

    /**
    * 用户ID
    */
     
     
    private Long id;
    /**
    * 用户账号信息
    */
     
     
     
     
    private String userName;
    /**
    * 用户密码md5加密
    */
     
     
     
     
    private String password;
    /**
    * 关联qq
    */
     
     
     
    private String qqId;
    /**
    * 用户昵称
    */
     
     
     
    private String nickname;
    /**
    * 头像照片链接
    */
     
     
     
    private String avatarImg;
    /**
    * 性别（男，女）
    */
     
    private Object sex;
    /**
    * 创建时间
    */
     
    private Date createTime;
    /**
    * 更新时间
    */
     
    private Date updateTime;


}
