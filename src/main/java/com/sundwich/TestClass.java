package com.sundwich;

import com.sundwich.timerecorder.annotation.TimeRecord;
import com.sundwich.timerecorder.util.TimeRecorderUtil;
import org.springframework.stereotype.Component;

@Component
public class TestClass {

    /**
     * 测试简单计时功能
     */
    @TimeRecord(enablePhase = false)
    public void testSimpleMethod() {
        System.out.println("测试简单计时功能 - 不启用分段计时");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("简单计时测试完成");
    }

    /**
     * 测试分段计时功能
     */
    @TimeRecord(name = "分段计时测试")
    public void testPhaseMethod() {
        System.out.println("开始测试分段计时功能");
        
        TimeRecorderUtil.recordPhase("初始化阶段");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        TimeRecorderUtil.recordPhase("数据处理阶段");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        TimeRecorderUtil.recordPhase("结果输出阶段");
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("分段计时测试完成");
    }

    /**
     * 测试自定义名称功能
     */
    @TimeRecord(name = "自定义名称测试方法")
    public void testCustomNameMethod() {
        System.out.println("测试自定义名称功能");
        TimeRecorderUtil.recordPhase("自定义阶段1");
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        TimeRecorderUtil.recordPhase("自定义阶段2");
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("自定义名称测试完成");
    }

    /**
     * 测试超时警告功能
     */
    @TimeRecord(name = "超时警告测试")
    public void testSlowMethod() {
        System.out.println("测试超时警告功能 - 这个方法会执行较长时间");
        TimeRecorderUtil.recordPhase("慢速阶段1");
        try {
            Thread.sleep(1500); // 超过默认的1000ms警告阈值
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        TimeRecorderUtil.recordPhase("慢速阶段2");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("超时警告测试完成");
    }

    /**
     * 测试嵌套方法调用
     */
    @TimeRecord(name = "嵌套方法测试")
    public void testNestedMethod() {
        System.out.println("开始嵌套方法测试");
        TimeRecorderUtil.recordPhase("外层方法开始");
        
        // 调用另一个带注解的方法
        innerMethod();
        
        TimeRecorderUtil.recordPhase("外层方法结束");
        System.out.println("嵌套方法测试完成");
    }

    /**
     * 内部方法，用于测试嵌套调用
     */
    @TimeRecord(name = "内部方法")
    private void innerMethod() {
        System.out.println("执行内部方法");
        TimeRecorderUtil.recordPhase("内部方法阶段1");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        TimeRecorderUtil.recordPhase("内部方法阶段2");
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
