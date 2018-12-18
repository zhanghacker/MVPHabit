# MVPHabit [![](https://www.jitpack.io/v/zhanghacker/HelperHabit.svg)](https://www.jitpack.io/#zhanghacker/HelperHabit)

## 简介
&emsp;&emsp;**基于MVP模式开发框架，整合Rxjava+Retrofit+Dagger+Butterknife等主流模块，加入中一些比较常用的工具类，进行一些抽取和整合**。有人会说有市面上已经MVPArms框架封装不是挺好的，我只能告诉你，那个太冗余了，很多没必要的东西太多，本框架主要是轻量级，少冗余，可扩展性高一些。demo给出相应的架构例子，可自行下载查看。如果你觉得框架的某些代码有帮助到你，请你star支持一下，作者会一直维护，非常感谢！

## 1. 准备工作
### 1.1 依赖Library
远程依赖

在根目录的build.gradle中加入

       allprojects {
            repositories {
                ...
                maven { url 'https://www.jitpack.io' }
            }
        } 
在主项目app的build.gradle中依赖
    
        dependencies {
                    //x.x.x自行更换最新版本
        	        implementation 'com.github.zhanghacker:MVPHabit:x.x.x'
        	}
或本地依赖

下载例子程序，在主项目app的build.gradle中依赖例子程序中的MVPLib：

        dependencies {	
            ...
            implementation project(':MVPLib')
        }
>如果不是远程依赖，而是下载的例子程序，那么还需要将例子程序中的config.gradle放入你的主项目根目录中，然后在根目录build.gradle的第一行加入：

       apply from: "config.gradle" 
## 2. 快速上手

### 2.1 初始化项目
	//初始化项目在Applicatin里面调用
	 HelperConfig.init(this)
	 //文件名称："MVPHabit-Exception-"+time + ".txt"  文件名称
     //默认路径：SDCard/Android/data/你的应用包名/cache/目录 ->默认的扑捉异常路径
	 .addCrashException()
	 //自定义文件或路径
	 //T->file或crashDirPath
	 //M->捕捉回调方法
	 .addCrashException(T,M);
### 2.2 第一个Activity
>&emsp;&emsp; MVP大家应该比较熟悉，我们把MP合并P，只用到VP。正常的MVP要有TestActivity、Testcontract、Testpresenter；如果你集成dagger要多两个Testcompanet、TestModule(瞄一眼上面个个类名称，等下下面例子会用到哦)。**建议使用：dagger**，接下来例子也是按这个来举例。</br>

注意：如果你使用dagger，请自行在app的buidle.gradle添加
>annotationProcessor 'com.google.dagger:dagger-compiler:2.17'
#### 2.2.1 继承Base
TestActivityr继承BaseDaggerActivity</br>

	public class TestActivity extends BaseDaggerActivity<TestPresenter> implements TestContract.View {
		......
	}

**BaseDaggerActivity是一个抽象类，需要传入一个泛型参数，就是上面TestPresenter参数**</br></br>
重写BaseDaggerActivity三个抽象方法，还有几个可选方法

	@Override
    public int getContentLayout() {
		//必须实现方法
        return R.layout.activity_test;
    }

    @Override
    protected void initInjector() {
        super.initInjector();
		//可选实现方法，如果有传入TestPresenter就可以在此方法注入注解
        DaggerTestComponent.builder()
                .testModule(new TestModule(this))
                .build().inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
		//必须实现方法
    }

    @Override
    public void initData() {
		//必须实现方法
    }
TestPresenter继承BasePresenter</br>

	public class TestPresenter extends BasePresenter implements TestContract.Presenter {

    	private TestContract.View mView;

    	@Inject
    	public TestPresenter(TestContract.View view) {
        	this.mView = view;
    	}

    	@Override
    	public void getData() {
    	}
	}
>Fragment的使用和Activity的使用方法差不多，如果不懂详细请参考demo。
#### 2.3 Testpresenter中的网络请求方法
	//mCompositeDisposable管理生命周期，只要添加进去，其它交给BaseDaggerActivity去处理
    mCompositeDisposable.add(SingletonRxRetrofit.getInstance()
                .getApi().login("15880858837")
                .compose(RxUtils.schedulersTransformer())
                .subscribe((String entity) -> mView.loadDataSuccess(entity),
                        ex -> mView.loadDataSuccess(ex.toString())))
#### 2.4 绑定控件和点击事件（非必要，如果你使用butterknife，不然就正常使用）

	@BindView(R.id.button)
    Button mButton;
    @BindView(R.id.textView)
    TextView mTextView;

	@OnClick(R.id.button)
    public void onViewClicked() {
    }
>无需再去 unbinder =ButterKnife.bind(this, view）、 unbinder.unbind();

注意：使用如果你使用butterknife，请自行在app的buidle.gradle添加
>annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
### 2.5 网络工具的配置
	//请求案例
	//使用有种方式：
	//1.如果有使用注解框架 比如：dagger框架，写在全局application的Module使用设置成单例
	//2.自行写一个单例工具类
	UserApi userApi = RetrofitUtil.Builde.create("http://xxx.xxx.xx.xx:8011/")
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

## 3. 辅助工具内容
* SP（SharedPreferences工具）
* T(Toast吐司工具)
* RegexUtils（正则表达工具）
* PermissionsTool（权限申请工具）
* SizeUtils.(尺寸相关工具)
* KeyboardUtils(软键盘工具)
* EncodeUtils(编码工具)
* EncryptUtils(解密解密工具)
* ImageUtils(图片操作工具)
* CrashUtils(异常捕捉工作)
## 混淆
请参考MVPLib目录下的proguard-rules.pro文件，包含MVPHabit中依赖的所有第三方library。

## 可参考博文
* [Android - Dagger2使用详解](https://www.jianshu.com/p/2cd491f0da01)

### 继续更新中...



## About us
&emsp;&emsp;喜欢尝鲜，主要从事安卓，业余时间搞搞PythonWeb(个人博客正在建站中)，小程序等。对于项目我会一直更新维护，如果你感兴趣，请star一下。

</br>
</br>


