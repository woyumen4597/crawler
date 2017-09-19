Crawer [![Build Status](https:/github.com/woyumen4597/crawer)](https:/github.com/woyumen4597/crawer)
======
_Pixiv API for Java (without Auth supported)_


* [2016/09/19] 增加pixivision特辑搜索 增加App主模块
* [2017/09/17] 增加搜索功能(仅查询信息(无法下载(403forbidden-.-)))
* [2017/09/16] 增加illust查询(通过id查询同画师的作品,并下载指定数量的图片) 合并user detail illust功能
* [2017/09/14] 增加r18功能(增加Header实现)(Cookie) 添加查询用户信息(通过id)
* [2017/09/13] 增加Rank功能(mode和content类型)(webmagic Needed)

Use Maven for installing:


Requirements: [webmagic](https://webmagic.io)

### Example:
  //获得用户id 27517的信息
  @Test
	public void test1() throws Exception {
		App app = new App();
		Detail detail = app.user("27517").detail();
		System.out.println(detail);
	}
  //获得每日排行榜的图片并下载
  * # mode: [day, week, month, male, female, rookie, orignal] 
  * day -> daily 
  * week-> weekly 
  * month -> monthly 
  * male -> male 
  * female -> female 
  * rookie ->rookie
  * original -> original
  
   #content 
   {illust ->illust ugoira -> ugoira manga -> manga}
  public void rank(String mode, String basePath, int number, String content)
 	@Test
	public void test2() throws Exception {
		App app = new App();
		app.rank().rank("daily");
	}
  //获得画师id为475415的作品下载5张到D:\\webmagic(文件夹需先行建立)
	@Test
	public void test3() throws Exception {
		App app = new App();
		app.user().illust("4755415", 5, "D:\\webmagic");
	}
  //功能同上
	@Test
	public void test4() throws Exception {
		App app = new App();
		app.user("4755415").illust(5, "D:\\webmagic");
	}
  //获得搜索关键词的信息
  // @param word 搜索关键字
	//* @param mode {safe：普通,r18:r18}
	//* @param sort {date_d:按最新排序,date:按最旧排序}
  public List<Search> search(String word,String mode, String order);
  
	@Test
	public void test5() throws Exception {
		App app = new App();
		List<Search> list = app.search().search("水着", "safe", "date_d");
		for (Search search2 : list) {
			System.out.println(search2);
		}
	}
  //获得pixivsion特辑id的图片
	@Test
	public void test6() {
		App app = new App();
		app.pixivision().pixivision("2810");
	}


## License

Feel free to use, reuse and abuse the code in this project.
