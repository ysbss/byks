package com.wyw.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author 鱼酥不是叔
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class FileCompany {
    private String fFileID;
    private String fFileName;
    private String fFileStorePath;
    private Long fFileCid;
    private String fFileFlag;
}
