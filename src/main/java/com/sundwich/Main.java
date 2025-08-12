package com.sundwich;

import com.sundwich.timerecorder.annotation.TimeRecord;
import com.sundwich.timerecorder.util.TimeRecorderUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class Main {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        
        // 获取测试类实例并执行测试
        TestClass testClass = context.getBean(TestClass.class);
        
        System.out.println("=== 开始测试定时打点工具 ===\n");
        
        // 测试简单计时
        testClass.testSimpleMethod();
        
        System.out.println("\n--- 分隔线 ---\n");
        
        // 测试分段计时
        testClass.testPhaseMethod();
        
        System.out.println("\n--- 分隔线 ---\n");
        
        // 测试自定义名称
        testClass.testCustomNameMethod();
        
        System.out.println("\n--- 分隔线 ---\n");
        
        // 测试超时警告
        testClass.testSlowMethod();
        
        System.out.println("\n--- 分隔线 ---\n");
        
        // 测试嵌套方法调用
        testClass.testNestedMethod();
        
        System.out.println("\n=== 测试完成 ===");
        
        // 关闭应用
        context.close();
    }
    
    @TimeRecord(name = "主方法测试")
    public void testMainMethod() {
        System.out.println("这是主方法中的测试");
        TimeRecorderUtil.recordPhase("主方法阶段1");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        TimeRecorderUtil.recordPhase("主方法阶段2");
    }
}