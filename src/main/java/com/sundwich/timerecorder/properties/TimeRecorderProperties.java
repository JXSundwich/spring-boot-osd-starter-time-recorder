package com.sundwich.timerecorder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sundwich
 * @date 2025/08/12
 */
@ConfigurationProperties(prefix = "time-recorder")
public class TimeRecorderProperties {
    /**
     * 是否启用时间记录器
     */
    private boolean enabled = true;

    /**
     * 分段计时功能默认状态
     */
    private boolean enablePhaseDefault = true;

    /**
     * 是否输出详细分段日志
     */
    private boolean showDetails = true;

    /**
     * 超时警告阈值（毫秒），超过此值显示警告
     */
    private long warnThreshold = 1000;


    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnablePhaseDefault() {
        return enablePhaseDefault;
    }
     public void setEnablePhaseDefault(boolean enablePhaseDefault) {
        this.enablePhaseDefault = enablePhaseDefault;
    }
    public boolean isShowDetails() {
        return showDetails;
    }
    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }
    public long getWarnThreshold() {
        return warnThreshold;
    }
    public void setWarnThreshold(long warnThreshold) {
        this.warnThreshold = warnThreshold;
    }
}
