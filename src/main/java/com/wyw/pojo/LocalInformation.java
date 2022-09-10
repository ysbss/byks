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
public class LocalInformation {
    private Long lId;
    private String lName;
    private Integer lKind;
    private String lSpecificKind;
    private Long lCid;
    private String lPhoneNumber;
    private String lContactPerson;
    private String lSubmitTime;
    private Integer lSpcFlag;
    private Integer lBrowseNum;
    private Integer lAppointmentNum;


}
