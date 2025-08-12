# Spring Boot 定时打点工具

这是一个基于Spring Boot AOP的时间记录工具，可以自动记录方法执行时间，支持分段计时和自定义配置。

## 功能特性

- 🕐 **自动计时**: 通过`@TimeRecord`注解自动记录方法执行时间
- 📊 **分段计时**: 支持在方法内部记录多个时间点
- 🏷️ **自定义名称**: 可以为方法指定自定义名称用于日志输出
- ⚠️ **超时警告**: 可配置超时阈值，超过时显示警告
- 🔧 **灵活配置**: 支持启用/禁用、显示详细日志等配置

## 使用方法

### 1. 基本用法

```java
@TimeRecord
public void myMethod() {
    // 你的业务逻辑
}
```

### 2. 自定义名称

```java
@TimeRecord(name = "用户登录方法")
public void login() {
    // 登录逻辑
}
```

### 3. 禁用分段计时

```java
@TimeRecord(enablePhase = false)
public void simpleMethod() {
    // 只记录总耗时，不记录分段
}
```

### 4. 分段计时

```java
@TimeRecord(name = "复杂业务方法")
public void complexBusiness() {
    // 第一阶段
    TimeRecorderUtil.recordPhase("数据准备");
    // ... 业务逻辑
    
    // 第二阶段
    TimeRecorderUtil.recordPhase("核心处理");
    // ... 业务逻辑
    
    // 第三阶段
    TimeRecorderUtil.recordPhase("结果输出");
    // ... 业务逻辑
}
```

## 配置说明

在`application.yml`中配置：

```yaml
time-recorder:
  enabled: true                    # 是否启用
  enable-phase-default: true       # 分段计时默认状态
  show-details: true               # 是否显示详细日志
  warn-threshold: 1000             # 超时警告阈值(毫秒)
```

## 测试运行

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 运行测试

```bash
mvn exec:java -Dexec.mainClass="com.sundwich.Main"
```

或者直接运行Main类。

### 3. 测试场景

项目包含以下测试场景：

- **简单计时测试**: 测试基本的计时功能
- **分段计时测试**: 测试分段计时和详细日志
- **自定义名称测试**: 测试自定义方法名称
- **超时警告测试**: 测试超时警告功能
- **嵌套方法测试**: 测试嵌套方法调用的计时

## 输出示例

```
=== 开始测试定时打点工具 ===

测试简单计时功能 - 不启用分段计时
简单计时测试完成
14:30:15.123 [main] INFO  c.s.t.aspect.TimeRecorderAspect - [TIME-RECORD] TestClass.testSimpleMethod 总耗时: 200ms

--- 分隔线 ---

开始测试分段计时功能
分段计时测试完成
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - [TIME-RECORD] 分段计时测试 总耗时: 180ms
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ┌───── 方法 TestClass.testPhaseMethod 详细计时 ─────
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ├ 自定义名称: 分段计时测试
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ├ 1.[开始]: 50ms
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ├ 2.[初始化阶段]: 50ms
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ├ 3.[数据处理阶段]: 100ms
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - ├ 4.[结果输出阶段]: 30ms
14:30:15.456 [main] INFO  c.s.t.aspect.TimeRecorderAspect - └───── 共 4 个计时段 ─────
```

## 注意事项

1. 确保Spring Boot AOP依赖已添加
2. 分段计时使用ThreadLocal，支持多线程环境
3. 超时警告阈值可根据业务需求调整
4. 建议在生产环境中适当调整日志级别

## 扩展功能

可以基于此工具扩展更多功能：

- 时间统计报表
- 性能监控告警
- 分布式链路追踪
- 性能数据持久化
