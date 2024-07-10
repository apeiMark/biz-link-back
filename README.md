<div align="center">
  <img alt="Arco Design Logo" width="200" src="https://avatars.githubusercontent.com/u/64576149?s=200&v=4"/>

<br />

  <h1>企业智联</h1>

<br />

一款在线企业协作与管理平台【分布式服务器】

<br />

</div>

#### 启动指南

```vue
运行顺序：
	运行register-server【Eureka注册中心服务器】
	运行biz-common【全局配置服务器】
	运行gateway-center【网关中心服务器】
	运行feign-server【openfeign联络服务器】
	运行user-server【用户管理服务器】
```

#### Commit规范


```
<type>(<scope>): <subject>（YYYY-MM-DD）
```

- type(必须)【用于说明git commit的类别，只允许使用下面的标识。】

  - feat：新功能（feature）。
  - fix/to：修复bug，可以是QA发现的BUG，也可以是研发自己发现的BUG。
  - fix：产生diff并自动修复此问题。适合于一次提交直接修复问题
    to：只产生diff不自动修复此问题。适合于多次提交。最终修复问题提交时使用fix
    docs：文档（documentation）。
  - style：格式（不影响代码运行的变动）。
  - refactor：重构（即不是新增功能，也不是修改bug的代码变动）。
  - perf：优化相关，比如提升性能、体验。
  - test：增加测试。
  - chore：构建过程或辅助工具的变动。
  - revert：回滚到上一个版本。
  - merge：代码合并。
  - sync：同步主线或分支的Bug。

- scope(可选)

  【scope用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。例如在Angular，可以是location，browser，compile，compile，rootScope， ngHref，ngClick，ngView等。如果你的修改影响了不止一个scope，你可以使用*代替。】

- subject(必须)【subject是commit目的的简短描述，不超过50个字符。】