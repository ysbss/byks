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
public class ApplyServiceProvide {
    private Long aspId;
    private Long aspSpId;
    private Long aspSid;
    private Long aspCid;
    private  String aspSubmitTime;
    private  Integer aspStatus;
}
