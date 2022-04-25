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
public class ApplyServiceProvide {
    private Long aspId;
    private Long aspSpId;
    private Long aspSid;
    private Long aspCid;
    private  String aspSubmitTime;
    private  Integer aspStatus;
}
