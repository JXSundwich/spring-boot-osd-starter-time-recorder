package com.sundwich.timerecorder.util;

import com.sundwich.timerecorder.aspect.TimeRecorderAspect;

/**
 * @author sundwich
 * @date 2025/08/12
 */
public class TimeRecorderUtil {

    /**
     * 方法内打点计时
     * @param phaseName 阶段标识名称
     */
    public static void recordPhase(String phaseName) {
        TimeRecorderAspect.recordPhase(phaseName);
    }
}