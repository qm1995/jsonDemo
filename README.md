# jsonDemo
### 该项目是一个spring boot中自定义解析json参数的project
#### 项目运行
对于含有@RequestJson注解的，解析的都是json参数，即前端传过来的参数都是json，否则会造成解析不了。注意事项在RequestJsonFilter里面  
运行环境 jdk1.8
#### 项目初衷
因为有时候接触的几个项目有时候后端要接收前端传来的json参数，原spring 虽然提供了@RequestBody注解来封装json数据，但局限性也挺大的，对参数要么适用jsonObject或者javabean类，或者string，
* 若使用jsonObject 接收，对于json里面的参数，还要进一步获取解析，很麻烦
* 若使用javabean来接收,若接口参数不一样，那么每一个接口都得对应一个javabean
* 若使用string 来接收，那么也得需要自己解析json参数  
所以琢磨了一个和get/post form-data提交方式一样，直接在controller层接口写参数名即可接收对应参数值
#### 项目功能
* 支持Collection<JavaBean>
* 支持普通参数名解析，即八种基本类型及包装类型，日期类型
* 支持JavaBean 接收参数

#### 项目结构  
>核心类
* RequestJsonFilter  
* RequestJsonHandler  
* Converter接口及实现类  
* RequestJson注解  
* JsonUtil  
* WebConfigure
* 谷歌第三方Gson  
>辅助测试类  
* 两个model类 student 和classes  
* 一个JsonController  
博客地址  
https://www.cnblogs.com/qm-article/p/10199622.html


