package com.liuxiaoqi.programmer.controller.home;

import com.liuxiaoqi.programmer.entity.admin.User;
import com.liuxiaoqi.programmer.service.admin.LogService;
import com.liuxiaoqi.programmer.service.admin.NewsCategoryService;
import com.liuxiaoqi.programmer.service.admin.NewsService;
import com.liuxiaoqi.programmer.service.admin.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ǰ̨ҳ����ҳ������
 * @author lxq
 *
 */
@RequestMapping("/index")
@Controller
public class IndexController {
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private NewsService newsService;

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;
	/**
	 * ϵͳ��ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 10);
		model.addObject("newsCategoryList", newsCategoryService.findAll());
		model.addObject("newsList", newsService.findList(queryMap));
		model.setViewName("home/index/index");
		return model;
	}
	
	/**
	 * ��ȡ��վ������Ϣ
	 * @return
	 */
	@RequestMapping(value="/get_site_info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSiteInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("type", "success");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 99999);
		retMap.put("totalArticle", newsService.getTotal(queryMap));
		retMap.put("siteDays", getDays("2020-04-05"));
		return retMap;
	}
	
	private long getDays(String data){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = sdf.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = new Date();
		long time = (endDate.getTime() - startDate.getTime())/1000/3600/24;
		return time;
	}

	/**
	 * ��¼���ύ���������
	 * @param user
	 * @param cpacha
	 * @return
	 */
	@RequestMapping(value="/frontlogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> frontlogin(User user, String cpacha, HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null){
			ret.put("type", "error");
			ret.put("msg", "����д�û���Ϣ��");
			return ret;
		}
		if(StringUtils.isEmpty(cpacha)){
			ret.put("type", "error");
			ret.put("msg", "����д��֤�룡");
			return ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "����д�û�����");
			return ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "����д���룡");
			return ret;
		}
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		if(loginCpacha == null){
			ret.put("type", "error");
			ret.put("msg", "�Ự��ʱ����ˢ��ҳ�棡");
			return ret;
		}
		if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "��֤�����");
			logService.add("�û���Ϊ"+user.getUsername()+"���û���¼ʱ������֤�����!");
			return ret;
		}
		User findByUsername = userService.findByUsername(user.getUsername());
		if(findByUsername == null){
			ret.put("type", "error");
			ret.put("msg", "���û��������ڣ�");
			logService.add("��¼ʱ���û���Ϊ"+user.getUsername()+"���û�������!");
			return ret;
		}
		if(!user.getPassword().equals(findByUsername.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "�������");
			logService.add("�û���Ϊ"+user.getUsername()+"���û���¼ʱ�����������!");
			return ret;
		}
		//˵���û������뼰��֤�붼��ȷ
		String menuIds = "";
		if(!StringUtils.isEmpty(menuIds)){
			menuIds = menuIds.substring(0,menuIds.length()-1);
		}
		//�ѽ�ɫ��Ϣ���˵���Ϣ�ŵ�session��
		request.getSession().setAttribute("user", findByUsername);
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ���");
		return ret;
	}

	/**
	 * ǰ̨�˳�ע������
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/frontlogout",method=RequestMethod.GET)
	public String frontlogout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("user", null);

		return "redirect:index";
	}

	@RequestMapping(value="/edit_password",method=RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model){
		model.setViewName("home/index/edit_password");
		return model;
	}
	//�޸�����
	@RequestMapping(value="/edit_password1",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPasswordAct(String newpassword,String oldpassword,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(newpassword)){
			ret.put("type", "error");
			ret.put("msg", "����д�����룡");
			return ret;
		}
		User user = (User)request.getSession().getAttribute("user");
		if(!user.getPassword().equals(oldpassword)){
			ret.put("type", "error");
			ret.put("msg", "ԭ�������");
			return ret;
		}
		user.setPassword(newpassword);
		if(userService.editPassword(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�����޸�ʧ�ܣ�����ϵ����Ա��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�����޸ĳɹ���");
		HttpSession session = request.getSession();
		session.setAttribute("user", null);
		logService.add("�û���Ϊ{"+user.getUsername()+"}�����û��ɹ��޸�����!");
		return ret;
	}

	/**
	 * ע�����û�
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/frontregister",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user, String cpacha ,HttpServletRequest request){
		user.setRoleId(2l);
		user.setPhoto("/News/resources/upload/default.jpg");
		user.setSex(0);
		user.setAge(0);
		user.setAddress("");
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ���û���Ϣ��");
			return ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "����д�û�����");
			return ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "����д���룡");
			return ret;
		}
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		if(loginCpacha == null){
			ret.put("type", "error");
			ret.put("msg", "�Ự��ʱ����ˢ��ҳ�棡");
			return ret;
		}
		if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "��֤�����");
			logService.add("�û���Ϊ"+user.getUsername()+"���û���¼ʱ������֤�����!");
			return ret;
		}

		if(isExist(user.getUsername(), 0l)){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѿ����ڣ����������룡");
			return ret;
		}


		if(userService.add(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�û�ע��ʧ�ܣ�����ϵ����Ա��");
			return ret;
		}

		User findByUsername = userService.findByUsername(user.getUsername());
		//�ѽ�ɫ��Ϣ���˵���Ϣ�ŵ�session��
		request.getSession().setAttribute("user", findByUsername);
		System.out.println(request.getSession().getAttribute("user"));
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ���");
		return ret;
	}
	/**
	 * �жϸ��û����Ƿ����
	 * @param username
	 * @param id
	 * @return
	 */
	private boolean isExist(String username,Long id){
		User user = userService.findByUsername(username);
		if(user == null)return false;
		if(user.getId().longValue() == id.longValue())return false;
		return true;
	}

	/**
	 * �ϴ�ͼƬ
	 * @param photo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		Map<String, String> ret = new HashMap<String, String>();
		if(photo == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ��Ҫ�ϴ����ļ���");
			return ret;
		}
		if(photo.getSize() > 1024*1024*1024){
			ret.put("type", "error");
			ret.put("msg", "�ļ���С���ܳ���10M��");
			return ret;
		}
		//��ȡ�ļ���׺
		String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
		if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��jpg,jpeg,gif,png��ʽ��ͼƬ��");
			return ret;
		}

		String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()){
			//�������ڸ�Ŀ¼���򴴽�Ŀ¼
			savePathFile.mkdir();
		}
		String filename = user.getUsername()+"."+suffix;
		try {
			//���ļ�������ָ��Ŀ¼
			photo.transferTo(new File(savePath+filename));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "�����ļ��쳣��");
			e.printStackTrace();
			return ret;
		}

		user.setPhoto(request.getServletContext().getContextPath() + "/resources/upload/" + filename);
		userService.edit(user);
		ret.put("type", "success");
		ret.put("msg", "��Ƭ�ϴ��ɹ���");
		ret.put("filepath",request.getServletContext().getContextPath() + "/resources/upload/" + filename );
		return ret;
	}

	public static boolean deleteServerFile(String filePath, String fileName){
		boolean delete_flag = false;
		File file = new File(filePath + fileName);
		if (file.exists() && file.isFile() && file.delete())
			delete_flag = true;
		else
			delete_flag = false;
		return delete_flag;
	}

}
