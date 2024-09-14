package com.xlf.schedule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统信息DTO
 * <p>
 * 该类用于定义系统信息DTO;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoDTO {
    private String systemName;
    private String systemChineseName;
    private String systemVersion;
    private String systemAuthor;
    private String systemAuthorEmail;
    private String systemAuthorUrl;
    private String systemLicense;
    private String systemLicenseUrl;
    private String systemDisclaimer;
    private String systemChineseDisclaimer;
    private String systemAbout;
    private String systemChineseAbout;
    private String systemLicenseStatement;
    private String systemChineseLicenseStatement;
}
