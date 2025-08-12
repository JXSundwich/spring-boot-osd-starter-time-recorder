package com.sundwich.timerecorder.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.sundwich.timerecorder.annotation.TimeRecord;
import com.sundwich.timerecorder.properties.TimeRecorderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StopWatch;

import java.util.Stack;


/**
 * @author sundwich
 * @date 2025/08/12
 */

@Aspect
@ConditionalOnProperty(prefix = "time-recorder", name = "enable", havingValue = "true")
public class TimeRecorderAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeRecorderAspect.class);
    private final TimeRecorderProperties properties;

    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<Stack<StopWatch>> stopWatchStack = ThreadLocal.withInitial(Stack::new);
    private static final ThreadLocal<Stack<String>> methodNameStack = ThreadLocal.withInitial(Stack::new);
    private static final ThreadLocal<Stack<String>> customNameStack = ThreadLocal.withInitial(Stack::new);

    @Autowired
    public TimeRecorderAspect(TimeRecorderProperties properties) {
        this.properties = properties;
    }

    @Around("@annotation(timeRecord)")
    public Object recordTime(ProceedingJoinPoint joinPoint, TimeRecord timeRecord) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        String methodName = getFullMethodName(joinPoint);
        String customName = timeRecord.name().isEmpty() ? methodName : timeRecord.name();
        boolean enablePhase = timeRecord.enablePhase() && properties.isEnablePhaseDefault();

        if (!enablePhase) {
            // 简单计时模式
            StopWatch stopWatch = new StopWatch(methodName);
            stopWatch.start();
            try {
                return joinPoint.proceed();
            } finally {
                stopWatch.stop();
                printSimpleResult(stopWatch, customName);
            }
        } else {
            // 分段计时模式
            methodNameStack.get().push(methodName);
            customNameStack.get().push(customName);
            stopWatchStack.get().push(new StopWatch(methodName));
            stopWatchStack.get().peek().start("开始");

            try {
                return joinPoint.proceed();
            } finally {
                StopWatch current = stopWatchStack.get().pop();
                String currentMethod = methodNameStack.get().pop();
                String currentCustomName = customNameStack.get().pop();
                current.stop();

                printPhaseResult(currentMethod, currentCustomName, current);

                // 清理线程上下文
                if (stopWatchStack.get().isEmpty()) {
                    stopWatchStack.remove();
                    methodNameStack.remove();
                    customNameStack.remove();
                }
            }
        }
    }

    /**
     * 在方法内部记录时间点
     */
    public static void recordPhase(String phaseName) {
        Stack<StopWatch> stack = stopWatchStack.get();
        if (!stack.isEmpty()) {
            StopWatch current = stack.peek();
            current.stop();
            current.start(phaseName);
        }
    }

    /**
     * 获取完整方法签名
     */
    private String getFullMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName() +
                "." + joinPoint.getSignature().getName() +
                "()";
    }

    /**
     * 打印简单计时结果
     */
    private void printSimpleResult(StopWatch stopWatch, String customName) {
        long time = stopWatch.getTotalTimeMillis();
        String message = String.format("[TIME-RECORD] %s 总耗时: %dms", customName, time);
        logBasedOnThreshold(time, message);
    }

    /**
     * 打印分段计时结果
     */
    private void printPhaseResult(String methodName, String customName, StopWatch stopWatch) {
        long totalTime = stopWatch.getTotalTimeMillis();

        String totalMessage = String.format("[TIME-RECORD] %s 总耗时: %dms", customName, totalTime);
        logBasedOnThreshold(totalTime, totalMessage);

        if (properties.isShowDetails() && stopWatch.getTaskCount() > 1) {
            logger.info("┌───── 方法 {} 详细计时 ─────", methodName);
            logger.info("├ 自定义名称: {}", customName);
            for (int i = 0; i < stopWatch.getTaskInfo().length; i++) {
                StopWatch.TaskInfo task = stopWatch.getTaskInfo()[i];
                logger.info("├ {}.[{}]: {}ms",
                        i + 1, task.getTaskName(), task.getTimeMillis());
            }
            logger.info("└───── 共 {} 个计时段 ─────", stopWatch.getTaskCount());
        }
    }

    private void logBasedOnThreshold(long time, String message) {
        if (time > properties.getWarnThreshold()) {
            logger.warn("⚠️ " + message);
        } else {
            logger.info(message);
        }
    }
}
