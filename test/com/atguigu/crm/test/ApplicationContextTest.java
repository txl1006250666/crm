package com.atguigu.crm.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.service.AuthorityService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.ReportService;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.utils.Page;

public class ApplicationContextTest {

	private ApplicationContext ctx = null;
	/*private UserMapper userMapper = null;
	private SalesChanceMapper salesChanceMapper = null;
	private CustomerService customerService = null;
	private RoleService roleService = null;
	private AuthorityService authorityService = null;
	private RoleMapper roleMapper = null;*/
	private ReportService reportService = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("spring-context.xml");
		/*userMapper = ctx.getBean(UserMapper.class);
		salesChanceMapper = ctx.getBean(SalesChanceMapper.class);
		customerService = ctx.getBean(CustomerService.class);
		roleService = ctx.getBean(RoleService.class);
		authorityService = ctx.getBean(AuthorityService.class);
		roleMapper = ctx.getBean(RoleMapper.class);*/
		reportService = ctx.getBean(ReportService.class);
	};
	
	private DateFormat dateFormat = null;
	
	{
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Test
	public void testReportServiceGetCustomerForMoneyPage() throws Exception{
		Map<String, Object> params = new HashMap<>();
		params.put("LIKES_custName", "");
		Date minOrderDate = dateFormat.parse("1900-1-1");
		params.put("GTD_minOrderDate", minOrderDate);
		params.put("LTD_maxOrderDate", new Date());
		
		Page<Map<String, Object>> page = reportService.getPages(params, 1);
		
		System.out.println(page.getTotalElements());
		System.out.println(page.getContent());
	}
	
	/*@Test
	public void testRoleMapperUpdate(){
		Role role = new Role();
		role.setId(2L);
		role.getAuthorities().add(new Authority(16L));
		role.getAuthorities().add(new Authority(17L));
		role.getAuthorities().add(new Authority(19L));
		role.getAuthorities().add(new Authority(20L));
		
		roleMapper.update(role);
	}
	
	@Test
	public void testAuthrityServiceGetParentAuthorities(){
		List<Authority> parentAuthorities = authorityService.getParentAuthorities();
		
		for(Authority pa: parentAuthorities){
			System.out.println(pa.getDisplayName() + "(" + pa.getId() + ")");
			
			for(Authority sub: pa.getSubAuthorities()){
				System.out.println("\t" + sub.getDisplayName() + "(" + sub.getId() + ")");
			}
			
			System.out.println();
		}
	}
	
	@Test
	public void testRoleServiceGetById(){
		Role role = roleService.getById(3L);
		System.out.println(role.isEnabled());
	}
	
	@Test
	public void testCustomerServiceFinish(){
		SalesChance salesChance = new SalesChance();
		salesChance.setContact("AAA");
		salesChance.setContactTel("13456789900");
		salesChance.setCustName("BBB");
		
		customerService.finish(salesChance);
	}
	
	@Test
	public void testGetSalesChance(){
		SalesChance salesChance = salesChanceMapper.getSalesChance(150L);
		System.out.println(salesChance);
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements2(){
		Map<String, Object> params = new HashMap<String, Object>();
		User createBy = new User();
		createBy.setId(24L);
		params.put("createBy", createBy);
		
		int status = 1;
		params.put("status", status);
		
		params.put("custName", "%��%");
		
		long result = salesChanceMapper.getTotalElements2(params);
		System.out.println(result);
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements(){
		User createBy = new User();
		createBy.setId(24L);
		int status = 1;
		
		long result = salesChanceMapper.getTotalElements(createBy, status);
		System.out.println(result);
	}
	
	@Test
	public void testUserMapper(){
		User user = userMapper.getByName("admin");
		System.out.println(user.getPassword());
		System.out.println(user.getRole().getName());
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}
*/
}
