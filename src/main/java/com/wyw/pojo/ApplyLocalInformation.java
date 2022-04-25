package com.wyw.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 鱼酥不是叔
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLocalInformation {
    private Long aliId;
    private Long aliLiId;
    private Long aliSid;
    private Long aliCid;
    private  String aliSubmitTime;
    private  Integer aliStatus;


}
