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
public class ApplyPartTimeJob {
    private Long apId;
    private Long apPid;
    private Long apSid;
    private Long apCid;
    private String apSubmitTime;
    private Integer apStatus;
}
