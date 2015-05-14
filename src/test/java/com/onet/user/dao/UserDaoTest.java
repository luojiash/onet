package com.onet.user.dao;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onet.article.comment.po.CommentPO;
import com.onet.article.main.po.ArticlePO;
import com.onet.article.main.po.CategoryPO;
import com.onet.article.share.po.SharePO;
import com.onet.common.manager.impl.ObjectConvertManagerImpl;
import com.onet.common.util.Json;
import com.onet.common.util.SqlProviderUtil;
import com.onet.note.po.DirPO;
import com.onet.note.po.NotePO;
import com.onet.tag.po.TagPO;
import com.onet.user.po.UserPO;
import com.onet.user.po.SettingPO;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ObjectConvertManagerImpl objectConvertManager;
	
	@Test
	public void testInsertUser() {
		UserPO userPO = new UserPO();
		userPO.setUserName("lojashg");
		userDao.insertUser(userPO);
	}
	@Test
	public void testUpdateUser() {
		/*UserPO userPO = userDao.queryUserPoById(1L);
		userPO.setAvailable(true);
		userPO.setRegisterDate(new Date());
		userDao.updateUser(userPO);*/
		UserPO userPO2 = userDao.queryUserPoById(2L);
		userPO2.setActive(false);
		Calendar calendar = Calendar.getInstance();
		calendar.set(1992, 3, 13);
		userPO2.setRegisterDate(calendar.getTime());
		userDao.updateUser(userPO2);
	}
	@Test
	public void testQueryUser() {
		/*UserQueryRequest request = new UserQueryRequest();
		int count = userDao.queryUserCountByRequest(request);
		System.out.println(count);
		
		request.getOrdering().setField("userName");
		request.getOrdering().setSequence(Sequence.ASC);
		request.setSearchWord("ssssss");
		List<UserPO> pos = userDao.queryUserListByRequest(request);
		System.out.println(Json.toJson(pos));*/
		UserPO po= userDao.queryUserPoByUserName(null);
		System.out.println(Json.toJson(po));
		/*UserPO userPO2 = userDao.queryUserPoById(null);
		System.out.println(Json.toJson(userPO2));*/
	}
	@Test
	public void getFields(){
		/*System.out.println(SqlProviderUtil.getTableField(UserPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(UserPO.class, null));*/
		/*System.out.println(SqlProviderUtil.getTableField(NotePO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(NotePO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(NotePO.class, new String[]{"id","userid","username","createTime"} ));
		*//*System.out.println(SqlProviderUtil.getTableField(TagPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(TagPO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(TagPO.class, new String[]{"id","name","userId"} ));*/
		
		/*System.out.println(SqlProviderUtil.getTableField(CategoryPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(CategoryPO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(CategoryPO.class, new String[]{"id","userid"} ));*/
		
		/*System.out.println(SqlProviderUtil.getTableField(ArticlePO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(ArticlePO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(ArticlePO.class, new String[]{"id","userid","username","createTime"} ));
		*/
		/*System.out.println(SqlProviderUtil.getTableField(UserPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(UserPO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(UserPO.class, new String[]{"id","registerDate","userName"} ));*/
		
		/*System.out.println(SqlProviderUtil.getTableField(SharePO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(SharePO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(SharePO.class, new String[]{"id","userid"} ));*/
		
		/*System.out.println(SqlProviderUtil.getTableField(CommentPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(CommentPO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(CommentPO.class, new String[]{"id","pid","articleId","","userid"} ));
		*/
		System.out.println(SqlProviderUtil.getTableField(SettingPO.class, null));
		System.out.println(SqlProviderUtil.getInsertFields(SettingPO.class, null));
		System.out.println(SqlProviderUtil.getUpdateSql(SettingPO.class, new String[]{"id","pid","articleId","","userid"} ));
	}
}
