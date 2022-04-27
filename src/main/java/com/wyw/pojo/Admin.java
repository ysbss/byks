package com.wyw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WYW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private  Long aId;
    private String aName;
    private String aPassword;
    private String aEmail;
}
