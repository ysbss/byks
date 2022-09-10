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
public class WebAdvice {
    private String  waId;
    private String  waKind;
    private String  waSource;
    private String  waSourceId;
    private String  waSourceName;
    private String  waSubmitTime;
    private String  waAdvice;
    private String  waFlag;

}
