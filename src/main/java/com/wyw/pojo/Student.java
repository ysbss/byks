package com.wyw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 鱼酥不是叔
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Student {
    private Long sId;
    private String sName;
    private String sSchool;
    private Integer sAge;
    private Integer sSex;
    private String sPassword;
    private String sIdentityNum;
    private Integer sEducationalDegree;
    private String sPhoneNumber;
    private String sMajor;
    private String sRegisterTime;
    private String sFlag;
    private String sEmail;
}
