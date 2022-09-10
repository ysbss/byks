package com.wyw.pojo;

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
public class Company {
    private Long cId;
    private String cName;
    private String cAddress;
    private String cPassword;
    private String cDescription;
    private String cRegisterTime;
    private String cFlag;
    private String cEmail;
}
