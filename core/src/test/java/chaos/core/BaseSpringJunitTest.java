package chaos.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by 王健 on 2016-12-13.
 * qq:1413221142
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-context-core.xml", "classpath:spring-mvc.xml"})


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
//@Transactional(transactionManager="transactionManager", readOnly = true)

//@ContextHierarchy({
//        @ContextConfiguration(name = "parent", locations = "classpath:spring-context-core.xml"),
//        @ContextConfiguration(name = "child", locations = "classpath:spring-mvc-core.xml")
//})
@ContextConfiguration(locations = "classpath:spring-context-core.xml")
//public class BaseSpringJunitTest{
public class BaseSpringJunitTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected MockHttpServletRequest request;

    @Autowired
    protected MockHttpServletResponse response;

    protected MockMvc mockMvc;


    @Before
    public void _before() throws Exception {
        System.out.println("BaseSpringJunitTest....before.....");
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())  //默认每次执行请求后都做的动作
                .build();


    }

    @After
    public void _after() throws Exception {
        System.out.println("BaseSpringJunitTest....after......");
    }

    @Test
    public void springTest() {

    }
}
