# follow-springioc-aop
自定义IOC和AOP 迷你版

## 分析

1. Bean对象创建
   - 有无value属性值
   - beanId   接口的名字 首字母小写
2. 依赖注入维护
3. 声明式事务控制
   - 是否实现接口





### 1.读取指定包下的类，判断有无加注解，

``` xml
    <dependency>
      <groupId>org.reflections</groupId>
      <artifactId>reflections</artifactId>
      <version>0.9.10</version>
    </dependency>
//Reflections通过扫描classpath，索引元数据，并且允许在运行时查询这些元数据。
```

### 2.初始化

使用反射，然后判断是否有接口，有接口（只有一个）使用接口的名称首字母小写，没有接口使用类名首字母小写。存放在beanMap中

### 3.依赖注入

遍历map，判断里面的属性是否有注解(@Autowired) ,就给这个属性设值。

### 4.事务

遍历map找到Transactional的注解，然后配置代理类
