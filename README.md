# HelperLib
## 简介
&emsp;&emsp;集成Lib是本人基于工作当中一些比较常用的一些工具，进行一些抽取和整合，纯属个人娱乐，如果你觉得框架的某些代码有帮助到你，请你star支持一下，非常感谢！

## 目前集成内容
* RetrofitUtil（Rxjava结合Retrofit进行一些封装）
* RxUtils（线程调度工具）
* RegexUtils（正则表达工具）
* PermissionsTool（权限申请工具，缺点无回调）

## 1. 快速上手

### 1.1初始化项目
	//初始化项目在Applicatin里面调用
	 HelperConfig.init(this);

### 1.2 网络请求
 
	//请求案例  
	UserApi userApi = new RetrofitUtil.Builder("http://xxx.xxx.xx.xx:8011/")
						.addCustomConverter(T)//添加插值器，对数据解析进行拦截处理
                        .addCustomInterceptor(T,E)//添加拦截器，对请求，和接收进行处理
                        .addSSL()//添加https验证
                        .addSSL("SSL的ca证书字符串")//添加https验证
                        .addCookiePersistence()//添加持久化
                        .build(UserApi.class);
            
            userApi.login(mobile，password)
                   .compose(RxUtils.schedulersTransformer())
                   .subscribe(entity->{
                          Log.e("tag",entity);
                       },ex->{
                           Log.e("tag",ex.toString());
                     });

### 1.3 权限申请
	PermissionsTool.with(this)
                    .addPermission( "android.permission.READ_EXTERNAL_STORAGE")
                    .addPermission( "android.permission.WRITE_EXTERNAL_STORAGE" )
                    .initPermission();

### 继续更新中...


## About us
&emsp;&emsp;本人喜欢专研新技术，主要从事安卓，业余时间搞搞PythonWeb(个人博客正在建站中)，小程序等。对于项目我会一直更新维护，如果你感兴趣，请star一下。

</br>
</br>


