## 开发事项

### 本地启动nacos,版本v3.X最新版本

### seata2.X最新版本

### mysql 8.X最新版本

### redis 7.X最新版本

### xxl-job-admin:3.X最新版本

### JDK25

### 安装SwitchHosts切换开发环境

- 根据自身部署情况,自行修改IP。
- 配置如下：
  127.0.0.1 buer-nacos
  127.0.0.1 buer-gateway
  192.168.52.101 buer-redis
  192.168.52.101 buer-xxl
  192.168.52.101 buer-mysql
  192.168.52.101 buer-sentinel
  192.168.52.101 buer-monitor
  192.168.52.101 buer-seata
  192.168.52.101 buer-rocketmq
- 其中 192.168.52.101 为开发环境IP

### mybatis 相关

- 项目使用mybatis-flex插件实现关系数据库的增删改查。
- 与mybatis-plus 最大的区别在于基本上不需要写xml。
- 支持xml编写sql语句,未保障项目的可维护性、兼容性、性能、尽可能不要手写sql。
- 语法详情见官网 https://mybatis-flex.com/zh/intro/what-is-mybatisflex.html

### 分布式事务

- 不同微服务之间如遇到数据新增、删除、更新操作,必须使用分布式事务。
- 官网 https://seata.apache.org/zh-cn/docs/user/quickstart/

### 分布式事务锁

- 项目使用 redisson 使用分布式事务锁，详情见 buer-common-redis
- 目的：当多个微服务的时候保障对共享资源的操作顺序，解决并发冲突和数据不一致问题。如流水号生成等。

### 框架必须启动的微服务

- buer-auth 鉴权服务
- buer-system 系统服务
- buer-gateway 网关服务
- buer-resource 资源服务

### 代码生成器

- 框架提供代码生成功能，但未提供UI界面。
- 启动方法可以见每个项目下都有 CodeGenerate,默认已经配置好。
- 可根据需要生成代码，生成后的代码自动创建在项目中，无需下载替换，遇到同名文件，不替换。

### 关于 API 文档

- 项目使用 Knife4j 对外展示 API 文档。
- 采用网关集成方式,在 buer-gateway 项目中，无需特殊处理，启动后访问。
- 访问地址...TODO待补充
- 详情见官网 https://doc.xiaominfo.com/docs/quick-start

### 业务相关

- 业务代码尽可能不要侵入框架,必须单独一个微服务，降低项目框架升级最新难度。如公交管理，整体可以是一个微服务

## 框架介绍

### 服务介绍

- buer-auth 鉴权服务，基于Sa-Token
- buer-system 系统管理服务,包含人员、部门、菜单灯
- buer-gateway 网关服务
- buer-resource 资源服务,包含文件、短信等
- buer-flow 流程引擎服务,待完善

### 鉴权介绍

- 项目使用 Sa-Token 鉴权，简单易用。
- 详情见官网 https://sa-token.cc/

## 编译器

### 安装最新版本IDEA

- 版本越高、问题越少

### 安装插件

- Alibaba Java Coding Guidelines 阿里巴巴代码检查器,提到代码质量同时降低代码出错的可能。
  签入代码时请确保所有警告都已经解除。
- MyBatisX 可以在 xml 与 mapper 代码跳转
- TODO:未来需要启用lint

## 开发相关

### 注释

- 不管代码多少,都要写注释,条理清晰
- 开发者遇到库表变更，对应的字段注释要及时更新，做到数据库的注释与库表的注释一致

### 工具类

- 优先使用hutool,hutool没有的可以自行封装

### Entity DTO VO QUERY 规范

- Entity:数据库类型
- 所有前端传给后端需要更新的 用DTO ，如表结构简单可以用Entity代替DTO
- 所有查询返回给前端的，都用VO,不允许将其他对象返回前端
- 所有查询条件用QUERY，如参数2个及以内可以单独声明。

### 枚举与字典的应用场景

#### 常量

- xx.api.constants

#### 字典

- 可以根据项目不同，随便增删改的，如公告类型

#### 枚举

- 不可以随便增删改的，如：状态、性别
- 业务枚举放在xx.api.constants，如：com.buer.system.api.constants
- 公共枚举放在com.buer.common.core.constant
- 具体用法搜索MenuTypeEnum

### 跨微服务赋值

#### 单字段回填

- userLabelFiller.fillField(
  orders,
  Order::getUserId,
  Order::setUserName
  );

#### 多字段回填

- userLabelFiller.fillMulti(
  orders,
  Pair.of(Order::getUserId, (order,user)->{
  order.setUserName(user.getName());
  order.setUserEmail(user.getEmail());
  }),
  Pair.of(Order::getApproverId, (order,user)->{
  order.setApproverName(user.getName());
  order.setApproverEmail(user.getEmail());
  })
  );
