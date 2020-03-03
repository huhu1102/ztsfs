package com.zt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zt.common.Utils;
import com.zt.dao.PositionDao;
import com.zt.po.Menu;
import com.zt.po.Position;
import com.zt.service.MenuService;


//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import org.springframework.web.context.WebApplicationContext;

//import com.zt.dao.AuthorRepository;
//import com.zt.dao.BookRepository;
//import com.zt.dao.MenuDao;
//import com.zt.dao.MenumateDao;
//import com.zt.dao.PositionDao;

//import com.zt.po.Author;
//import com.zt.po.Book;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class Ztsfs1ApplicationTests {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//   @Autowired
//    PositionDao psDao;
//   @Autowired
//   MenuDao mDao;
//   @Autowired
//   MenumateDao maDao;
//   /**
//    * 测试controller 类模仿 http发送请求
//    * 
//    */
//   @Autowired
//   private WebApplicationContext webApplicationContext;
//   private MockMvc mockMvc;
 
   @Autowired
   private MenuService menuservice;
//   @Autowired
//   private AuthorRepository authorRepository;
   
   
   
//   @Test
//   public void init() {
//       Author lewis = new Author();
//        lewis.setName("Lewis");
//       Author mark = new Author();
//       mark.setName("Mark");
//       Author peter = new Author();
//       peter.setName("Peter");
//       Book springboot = new Book("Spring Boot in Action");
//        Set<Author> at=  springboot.getAuthors();
//       springboot.getAuthors().addAll(Arrays.asList(lewis, peter));
//
//       Book spring = new Book("Spring in Action");
//       spring.getAuthors().addAll(Arrays.asList(lewis, mark));
//
//
//       bookRepository.save(Arrays.asList(spring, springboot));
//   }
//	@Test
//	public void contextLoads() {
//	}
//    @Test
//    public void tsst1() {
//      Position p=new Position();
//      p.setHasChildren(false);
//      p.setName("综合主管");
//      p.setSpecification("总管综合部门");
//      Position p1=new Position();
//      p1.setHasChildren(false);
//      p1.setName("财务");
//      p1.setSpecification("财务部门");
//      
//      Menumate meta=new Menumate();
//      meta.setKeepAlive(false);
//      meta.setRequireAuth(false);
//      Menumate meta1=new Menumate();
//      meta1.setKeepAlive(false);
//      meta1.setRequireAuth(false);
//      Menu m=new Menu();
//      m.setChildren(null);
//      m.setComponent("home");
//      m.setMeta(meta);
//      Menu m1=new Menu();
//      m1.setChildren(null);
//      m1.setComponent("home");
//      m1.setMeta(meta1);
//      List<Position> me=  m.getPost();
//      m.getPost().addAll(Arrays.asList(p,p1));
//      p.getMenuOperation().addAll(Arrays.asList(m,m1));
//       maDao.save(meta);
//       mDao.save(Arrays.asList(m));	 
//      
//    	
//    }
//   
//   @Before
//   public void setUp() throws Exception{
       //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
//       mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
//   }
//   @Test
//   public void getHello() throws Exception{

       /**
        * 1、mockMvc.perform执行一个请求。
        * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
        * 3、ResultActions.param添加请求传值
        * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
        * 5、ResultActions.andExpect添加执行完成后的断言。
        * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
        *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
        * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
        */

//       MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/emp/exception")
//               .param("name","lvgang")
//               .accept(MediaType.ALL_VALUE))
              // .andExpect(MockMvcResultMatchers.status().isOk())             //等同于Assert.assertEquals(200,status);
              // .andExpect(MockMvcResultMatchers.content().string("hello lvgang"))    //等同于 Assert.assertEquals("hello lvgang",content);
//               .andDo(MockMvcResultHandlers.print())
//               .andReturn();
//       int status=mvcResult.getResponse().getStatus();                 //得到返回代码
//       String content=mvcResult.getResponse().getContentAsString();    //得到返回结果
//
//       Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
//       Assert.assertEquals("{\"code\":\"0x10002\",\"msg\":\"用户名错误或不存在\"}",content);            //断言，判断返回的值是否正确
//   }
	
//	  @Test
//	  public void dateTest() {
//		  SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		  Calendar c = Calendar.getInstance();
//		  System.out.println("当前日期:"+sf.format(c.getTime()));
//		  c.add(Calendar.DAY_OF_MONTH, 3);
//		  System.out.println("增加一天后日期:"+sf.format(c.getTime()));
//		 Position p= psDao.findById(1);
//		 System.out.println(p);
//	  }
//	  @Test
//	  public void dateTest() {
//		 Set<Menu> list=  menuservice.getAllMenuDataes();
//		logger.info(list.toString());
//	  }
//	  
   @Test
	  public void dateTest() {
	String  s= Utils.currentNo("0002", 8, 15L);
     System.out.println(s);
	  }
//	  

}
