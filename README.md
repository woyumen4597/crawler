Crawer [![Build Status](http://123.206.130.92:8080)](http://123.206.130.92:8080)
======
_Pixiv API for Java (without Auth supported)_


* [2016/09/19] 增加pixivision特辑搜索 增加App主模块
* [2017/09/17] 增加搜索功能(仅查询信息(无法下载(403forbidden-.-)))
* [2017/09/16] 增加illust查询(通过id查询同画师的作品,并下载指定数量的图片) 合并user detail illust功能
* [2017/09/14] 增加r18功能(增加Header实现)(Cookie) 添加查询用户信息(通过id)
* [2017/09/13] 增加Rank功能(mode和content类型)(webmagic Needed)

_写在前面_

由于P站最近疑被墙(或者出现故障) 建议修改hosts文件(可以直接复制目录下的hosts文件到C:/windows/System32/drivers/etc/hosts 覆盖即可)


_Use Maven for installing:_


Requirements: [webmagic](https://webmagic.io)

### Docs:
 
 ~~~
  //获得每日排行榜的图片并下载
  * # mode: [day, week, month, male, female, rookie, orignal] 
  * day -> daily 
  * week-> weekly 
  * month -> monthly 
  * male -> male 
  * female -> female 
  * rookie ->rookie
  * original -> original
  
  * content {illust ->illust ugoira -> ugoira manga -> manga}
  public void rank(String mode, String basePath, int number, String content)
  ~~~
 
 ~~~	
  //下载画师的作品到指定目录
  @param user_id 画师id
  @param number 下载图片数量
  @param basePath 下载目录(需先行建立)
  public void illust(String user_id, int number, String basePath)
 ~~~

 ~~~
  //获得搜索关键词的信息
  @param word 搜索关键字
  @param mode {safe：普通,r18:r18}
  @param sort {date_d:按最新排序,date:按最旧排序}
   public List<Search> search(String word,String mode, String order);
  ~~~
 ~~~
  //获得pixivsion特辑id的图片
  public void pixivision(String id,String basePath,int number);
~~~

### Examples:
 
 	在[test](https://github.com/woyumen4597/crawer)包里

## License

Feel free to use, reuse and abuse the code in this project.
