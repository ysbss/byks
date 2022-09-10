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
public class ServiceProvide {
    private Long spId;
    private String spName;
    private Integer spKind;
    private String spSpecificKind;
    private Long spCid;
    private String spExpense;
    private String spPhoneNumber;
    private String spContactPerson;
    private String spSubmitTime;
    private Integer spSpcFlag;
    private Integer spBrowseNum;
    private Integer spAppointmentNum;

}
