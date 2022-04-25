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
public class PartTimeJob {
    private Long pId ;
    private Long cId ;
    private String pName;
    private String pPosition;
    private String pSalary;
    private Integer pNum ;
    private String pEducationalDegree;
    private String pExperience;
    private String pAge;
    private String pPhoneNumber;
    private String pContactPerson;
    private String pSubmitTime;
    private Integer spFlag;
    private Integer pBrowseNum;
    private Integer pAppointmentNum;
}
