package com.mymy.mymy.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mymy.mymy.Dao.Dao;
import com.mymy.mymy.Dao.ReplyDao;
import com.mymy.mymy.Dao.SnsUserData;
import com.mymy.mymy.Dao.memberCheckDao;
import com.mymy.mymy.Dto.Dto;
import com.mymy.mymy.Dto.ReplyDto;

@Controller
public class Handler
{
	@Autowired
	private SqlSession sqlSession;
	// file attachement server path 
	private static final String FILE_SERVER_PATH =  "/Users/helloworld/eclipse-workspace/myMvc_Review/src/main/webapp/resources/assets/img";
	private String fileName;
	private String userName;
	
	@RequestMapping("/home")
	public String home() 
	{
		return "home";
	}
	@RequestMapping("/image")
	public String image(HttpServletRequest req , Model model) {
		System.out.println("Content_view_Start!");
		int bId = 223;
		System.out.println("bId ===>"+ bId);
		Dao dao = sqlSession.getMapper(Dao.class);
		ArrayList<Dto> dtos= dao.ContentView(bId);
		model.addAttribute("dtos",dtos);
		return "imageShowExample";
	}
	//댓글 갯수 count 
	@RequestMapping(value = "replyCount" , method = RequestMethod.GET)
	@ResponseBody
	public String replyCount(HttpServletRequest req , Model model) {
		System.out.println("reply count start! =======> ");
		String bId = req.getParameter("bId");
		ReplyDao dao = sqlSession.getMapper(ReplyDao.class);
		
		return "";
	}
	//파일이 없는경우 통신 
	@RequestMapping(value="/Dowrite",method = RequestMethod.POST)
	@ResponseBody
	public String Dowrite(HttpServletRequest req, Model model)
	{
		System.out.println("Dowrite Start! ==============");
		//view => paramter => handler 
		String bTitle = req.getParameter("bTitle");
		System.out.println(bTitle);
		String bName =req.getParameter("bName");
		System.out.println(bName);
		String bContent = req.getParameter("bContent");
		System.out.println(bContent);
		//mybatis sqlSession (AutoWired)
		Dao dao = sqlSession.getMapper(Dao.class);
		dao.NofileWrite(bTitle,bName,bContent);
		return "redirect:BoardList";
		
		
	}
	@RequestMapping(value="/boardWrite_ex",method = RequestMethod.GET)
	public String boardWrite_ex() {
		return "boardWrite_ex";
	}
	//!!vue 에서 이미지 첨부 기능 cors 오류로 인해 잠시 보류 jsp 로 해당 기능만 대체해버림 
	@RequestMapping(value = "/doWriteF", method = RequestMethod.POST)
	public String board_write_view(@RequestParam("FILE_IMAGE") MultipartFile file, ModelAndView mv,HttpServletRequest req, Model model) throws IOException {
		//file 이 있는경우 
		if(!file.getOriginalFilename().isEmpty()) {
			file.transferTo(new File(FILE_SERVER_PATH, file.getOriginalFilename()));
			String fileName = file.getOriginalFilename();
			String f = fileName;
		
			System.out.println("fileName ====================>"+f);
			Dao dao = sqlSession.getMapper(Dao.class);
			System.out.println(file);
			String BTITLE = req.getParameter("BTITLE");
			String BNAME = req.getParameter("BNAME");
			String BCONTENT = req.getParameter("BCONTENT");
		    dao.Dowrite(BTITLE, BNAME, BCONTENT,f);
			model.addAttribute("msg", "File uploaded successfully.");
			//file 이 없는경우 
		}else {
			Dao dao = sqlSession.getMapper(Dao.class);
			//String f = fileName;
			String BTITLE = req.getParameter("BTITLE");
			String BNAME = req.getParameter("BNAME");
			String BCONTENT = req.getParameter("BCONTENT");
		    dao.NofileWrite(BTITLE, BNAME, BCONTENT);
			
		}
		return "redirect:http://localhost:8080/home?pageNum=1";
	}
	
	
	
	//파일 첨부가 있는경우 통신
	@RequestMapping(value = "/doWriteWithFile", method = RequestMethod.POST)
	@ResponseBody
	public String doWriteWithFile(MultipartFile file ,ModelAndView mv,
				HttpServletRequest req, Model model) {
		String fileName = req.getParameter("selectedFile");
		System.out.println("selectedFile =========>"+file);
		System.out.println("selectedFile =========>"+fileName);
		
		return "";
	}
	
	//paging ==> get List 
	@RequestMapping("/BoardList")
	@ResponseBody
	public ArrayList<Dto> BaordList(HttpServletRequest req ,Model model)
	{
		//1) DataBase -> Data -> model -> return View 
		Dao dao = sqlSession.getMapper(Dao.class);   
		//String testPageNum = req.getParameter("pageNum");
		int listCount = boardCount();
		//int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		System.out.println("testPageNum ======> "+ pageNum);
		System.out.println("pageNum =====> " + pageNum);
		double itemPerPage = 5.0;
		int itemFirstNum=((int)itemPerPage)*(pageNum-1)+1;  // 첫 번째 item 번호 (페이지별)
		int pageLastNum=(int)Math.ceil(listCount/itemPerPage);  // 마지막 페이지 수
		int LastPageItemNum=listCount-(pageLastNum-1)*((int)itemPerPage);  // 마지막 페이지 마지막item수
		int itemLastNum=0;
		if(pageNum<pageLastNum){
			itemLastNum=((int)itemPerPage)*pageNum;
		}else {
			itemLastNum=LastPageItemNum+(((int)itemPerPage)*(pageNum-1));
		}
		System.out.println("itemFirstNum : "+itemFirstNum);
		System.out.println("pageLastNum : "+pageLastNum);
		System.out.println("LastPageItemNum : "+LastPageItemNum);
		System.out.println(itemLastNum);
		ArrayList<Dto> dtos = dao.PagingList(itemFirstNum,itemLastNum);
		//dtos.add(pageLastNum);
		
		model.addAttribute("dtos",dtos);
		
		return dtos;
	}
	
	//모든 게시물 갯수조회 
	public int boardCount() {
		Dao dao = sqlSession.getMapper(Dao.class);
		int bCnt = dao.listCount();
		System.out.println("boardCount ====> " + bCnt);
		System.out.println(bCnt);
		return bCnt;
	}
	//클릭한게시물조회하기  vue 로 reuturn 할때 json 방식으로 해야함 
	@RequestMapping("/Board_view")
	@ResponseBody
	public ArrayList<Dto> ContentView(HttpServletRequest req , Model model)
	{
		System.out.println("Content_view_Start!");
		int bId = Integer.parseInt(req.getParameter("bId"));
		System.out.println("bId ===>"+ bId);
		Dao dao = sqlSession.getMapper(Dao.class);
		ArrayList<Dto> dtos= dao.ContentView(bId);
		//dao.upHit(bId);
		//JSONParser parser = new JSONParser();
		//Object obj = parser.parse( jsonStr );
		
		return dtos;
		
	}  
	
	
	/*
	@RequestMapping("/BoardList")
	public String BaordList(Model model)
	{
		//1) DataBase -> Data -> model -> return View 
		Dao dao = sqlSession.getMapper(Dao.class);   
		ArrayList<Dto>dtos = dao.BoardList();
		model.addAttribute("dtos",dtos);
	
		return "BoardList";
	}
	*/
	
	
	@RequestMapping("/BoardSearch")
	@ResponseBody
	public ArrayList<Dto> BaordSearch(HttpServletRequest req, Model model) {
		String bTitle = req.getParameter("bTitle");
		System.out.println("bTitle =====>" + bTitle);
		Dao dao = sqlSession.getMapper(Dao.class);   
		ArrayList<Dto>dtos = dao.BoardSearch(bTitle);
		System.out.println(dtos);
		System.out.println("dtos ==== > 데이터 검색 ");
		model.addAttribute("dtos",dtos);
		
		return dtos;
	}
	//글 작성 ==> 권한이 있는 ( 로그인 한 유저한테만 ) 
	@RequestMapping("/BoardWrite_View")
	public String BoardWrite_View(HttpServletRequest req , Model model )
	{
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null || session.getAttribute("user_id").equals(""))
		{
			return "doLogin";
			
		}
	
		return "BoardWrite_View";
	}
	
	//reply do 
	@RequestMapping(value ="/writeReply", method = RequestMethod.GET)
	@ResponseBody
	public String writeReply(HttpServletRequest req, Model model) {
		System.out.println("writeReply_start");
		String bId = req.getParameter("bId");
		String Reply_content = req.getParameter("Reply_content");
		String Reply_user = req.getParameter("Reply_user");
		System.out.println("bId=======> reply " + bId);
		System.out.println("Reply_content===========> reply"+ Reply_content);
		System.out.println("Reply_user =============> reply "+ Reply_user);
	    ReplyDao dao = sqlSession.getMapper(ReplyDao.class);
		dao.doReply(Reply_user,Reply_content , bId);
		System.out.println("댓글이 입력 됨");
		
		
		return "";
		
	}
	// kakao 로그인 정보 저장 
	@RequestMapping(value="/insertuserData" , method = RequestMethod.GET)
	@ResponseBody
	public String insertuserData(HttpServletRequest req , Model model) {
		System.out.println("카카오 로그인 정보 전달!");
		String nickname = req.getParameter("nickname");
		System.out.println(nickname);
		String profile_image = req.getParameter("profile_image");
		System.out.println(profile_image);
		String thumbnail_image = req.getParameter("thumbnail_image");
		System.out.println(thumbnail_image);
		String email = req.getParameter("email");
		System.out.println(email);
		String birthday = req.getParameter("birthday");
		System.out.println(birthday);
		String gender = req.getParameter("gender");
		System.out.println(gender);
		String tokenNumber = req.getParameter("tokenNumber");
		System.out.println("tokenNumber ======>" + tokenNumber);
		SnsUserData dao = sqlSession.getMapper(SnsUserData.class);
		dao.insertSnSUserData(nickname,profile_image,thumbnail_image,birthday,gender,tokenNumber,email);
		System.out.println("process completed");
		userName = nickname;
		return userName;
		
	}
	// kakao 로그인을 했을때 , 로그인한 유저의 데이터를 조회하기 
	@RequestMapping(value="/selectUserData")
	@ResponseBody
	public ArrayList<Dto> selectUserData(HttpServletRequest req, Model model ){
		String tokens = req.getParameter("tokens");
		System.out.println("nickname===> user Data 조회 " + tokens);
		SnsUserData dao = sqlSession.getMapper(SnsUserData.class);
		
		ArrayList<Dto> dtos= dao.selectUserData(tokens);
		System.out.println("process completed ===> select user Data");
		
		return dtos;
		
	}
	//댓글 list 조회하기 
	@RequestMapping(value="/getReply")
	@ResponseBody
	public ArrayList<ReplyDto> getReply(HttpServletRequest req ,Model model){
		String bId = req.getParameter("bId");
		System.out.println("getReply =========> " + bId);
		ReplyDao dao = sqlSession.getMapper(ReplyDao.class);
		ArrayList<ReplyDto> dtos = dao.getReply(bId);
		System.out.println("process success! =====> get Reply ");
		return dtos;
		
	}
	
	//카카오 로그아웃후 , 토큰 삭제 
	@RequestMapping(value="/deleteToken")
	@ResponseBody
	public String deleteToken(HttpServletRequest req, Model model) {
		System.out.println("토큰삭제");
		System.out.println("deleteToken start!");
		String tokenNumber = req.getParameter("tokenNumber");
		System.out.println("tokenNumber ====>"+tokenNumber);
		SnsUserData dao = sqlSession.getMapper(SnsUserData.class);
		dao.tokenDelete(tokenNumber);
		
		return "";
		
	}
	
	
	//해당 게시물 삭제하기 (클릭한 게시물을 삭제하고 싶은것 )
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(HttpServletRequest req , Model model)
	{
		System.out.println("delete___start!!___");
		String bId = req.getParameter("bId");
		Dao dao = sqlSession.getMapper(Dao.class);
		dao.delete(Integer.parseInt(bId));
		return "redirect:BoardList";
	}
	//클릭한 , 해당 게시물의 수정 부분으로 가기 
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest req , Model model)
	{
		
		int bId = Integer.parseInt(req.getParameter("bId"));
		Dao dao = sqlSession.getMapper(Dao.class);
		ArrayList<Dto> dto = dao.ContentView(bId);
		model.addAttribute("modify_view", dto);
		return "modify_view";
		
		
		
	}
	@RequestMapping(value="/modify",method = RequestMethod.GET)
	@ResponseBody
	public String modify(HttpServletRequest req, Model model)
	{
		System.out.println("modify====> start!!");
		/*
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null || session.getAttribute("user_id").equals(""))
		{
			return "doLogin";
			
		}
		*/
		
		String bId = req.getParameter("bId");
		String bName = req.getParameter("bName");
		String bTitle = req.getParameter("bTitle");
		String bContent = req.getParameter("bContent");
		Dao dao = sqlSession.getMapper(Dao.class);
		dao.modify(Integer.parseInt(bId),bName,bTitle,bContent);
		return "redirect:BoardList";
	}
	//login page show
	@RequestMapping("/doLogin")
	public String doLogin()
	{
		
		return "doLogin";
	}
	@RequestMapping(value="/doCheckUser",method = RequestMethod.POST)
	public String doCheckUser(HttpServletRequest req, Model model)
	{
		String user_id = req.getParameter("user_id");
		String user_passowrd = req.getParameter("user_password");
		System.out.println(user_id+","+user_passowrd);
		HttpSession session =req.getSession();
		System.out.println("check__1");
		memberCheckDao mDao = sqlSession.getMapper(memberCheckDao.class);
		System.out.println("check__2");
		int checkCount = mDao.UserCheckLogin(user_id,user_passowrd);
		System.out.println("checkCount ---------");
		System.out.println(checkCount);
		
		if(checkCount == 1)
		{
			//login 에 성공 한 경우 => session 에 해당유저의 id 를 담아줌 
			session.setAttribute("user_id", user_id);
			System.out.println(user_id);
		}
		else 
		{
			System.out.println("로그인 fail");
			String msg = "계정을 확인해주세요.";
			model.addAttribute("계정을 확인해주세요",msg);
			model.addAttribute("0",0);
			return "redirect:doLogin";
		}
		
		//성공시 게시판 목록으로 redirect 
		return "redirect:BoardList";
		
	}
	
	//logout url 
	// => logout == 해당 유저의 session끊어주는 행위 
	@RequestMapping("/doLogout")
	public String doLogout(HttpServletRequest req, Model model)
	{
		HttpSession session = req.getSession();
		session.invalidate();
		model.addAttribute("logout","Y");
		
		
		return "redirect:BoardList";
		
	}
	
	
	
}
