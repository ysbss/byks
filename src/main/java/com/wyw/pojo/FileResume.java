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
public class FileResume {
    private String fFileID;
    private String fFileName;
    private String fFileStorePath;
    private Long fFileSid;
    private Long fFilePid;
    private String fFileFlag;
}
