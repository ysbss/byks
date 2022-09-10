package com.wyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author WYW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Admin {
    @TableId(type=IdType.AUTO)
    private  Long aId;
    private String aName;
    private String aPassword;
    private String aEmail;
}
