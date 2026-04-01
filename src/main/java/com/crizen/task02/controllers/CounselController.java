package com.crizen.task02.controllers;

import com.crizen.task02.vo.CounselCommentVO;
import com.crizen.task02.vo.CounselVO;
import com.crizen.task02.mapper.CounselMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
// @RequestMapping: 이 컨트롤러의 모든 메서드는 기본적으로 "/counsel"로 시작하는 URL을 처리합니다.
@RequestMapping("/counsel")
public class CounselController {

    // @Autowired: 스프링이 알아서 CounselMapper 객체를 생성해 여기에 쏙 넣어줍니다(의존성 주입). (지우면 NullPointerException 발생)
    @Autowired
    private CounselMapper counselMapper; // MyBatis 매퍼 주입 (DB 통신용)

    // [핵심 유틸] 현재 로그인한 유저 정보를 가져오는 공통 메서드
    private UserVO getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserVO) auth.getPrincipal();
    }

    // 1. 게시글 목록 화면 (검색VO를 파라미터로 받음)
    // URL: /counsel/list 에 GET 방식으로 접근할 때 실행됩니다. (POST 등 다른 방식 금지)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(CounselVO searchVO, Model model) { // Model: 데이터를 화면(JSP)으로 넘겨주기 위한 가방 역할을 합니다.
        // counselMapper의 getCounselList()를 실행해 DB에서 목록을 가져온 뒤, "list"라는 이름표를 붙여 Model에 담습니다.
        model.addAttribute("list", counselMapper.getCounselList(searchVO));
        // viewResolver 설정에 의해 /WEB-INF/views/counsel/list.jsp 파일로 이동합니다.
        return "counsel/list";
    }

    // 2. 글쓰기 화면 이동
    // 단순히 글쓰기 폼을 보여주는 역할만 하므로 파라미터가 없습니다.
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeForm() {
        return "counsel/write";
    }

    // 3. 글쓰기 처리 (DB Insert 후 목록으로 리다이렉트)
    // 폼에서 전송버튼을 누르면 POST 방식으로 데이터가 넘어옵니다. 파라미터로 선언된 CounselVO에 폼 데이터가 자동으로 담깁니다.
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writeAction(CounselVO vo) {
        // 화면에서 누가 입력하든, 실제 로그인한 사람의 ID로 덮어씌움
        vo.setCounsel_writer(getLoginUser().getUser_id());

        // Mapper를 호출해 DB에 글을 저장합니다.
        counselMapper.insertCounsel(vo);

        // "redirect:": 저장이 끝난 후 "/counsel/list" URL로 브라우저를 강제 이동(새로고침) 시킵니다.
        // (만약 redirect를 안 쓰면, 사용자가 F5(새로고침)를 누를 때마다 똑같은 글이 계속 작성되는 대참사가 발생합니다!)
        return "redirect:/counsel/list";
    }

    // 4. 상세 화면 (게시글 정보 + 댓글 목록 동시에 조회)
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    // @RequestParam("seq_counsel"): URL에 있는 ?seq_counsel=1 값을 int형 seq_counsel 변수에 넣어줍니다.
    public String view(@RequestParam("seq_counsel") int seq_counsel, Model model) {
        // 1. 선택한 번호에 해당하는 게시글 상세 정보를 가져와 "counsel"이라는 이름으로 담습니다.
        model.addAttribute("counsel", counselMapper.getCounsel(seq_counsel));
        // 2. 선택한 글 번호에 해당하는 댓글 목록들도 가져와 "comments"라는 이름으로 담습니다.
        model.addAttribute("comments", counselMapper.getCommentList(seq_counsel));
        return "counsel/view"; // counsel/view.jsp 화면 출력
    }

    // 5. 글 수정 화면 띄우기 (본인 또는 관리자만 접근 가능)
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateForm(@RequestParam("seq_counsel") int seq_counsel, Model model, RedirectAttributes rttr) {
        UserVO loginUser = getLoginUser();
        CounselVO counsel = counselMapper.getCounsel(seq_counsel);

        // 관리자이거나, 글 작성자인지 확인
        boolean isAdmin = "ROLE_ADMIN".equals(loginUser.getRole());
        boolean isAuthor = counsel.getCounsel_writer().equals(loginUser.getUser_id());

        // 권한이 없으면 수정 화면 진입 차단 및 에러 메시지 전송
        if (!isAdmin && !isAuthor) {
            rttr.addFlashAttribute("errorMessage", "자신이 등록한 게시물만 수정할 수 있습니다!");
            return "redirect:/counsel/view?seq_counsel=" + seq_counsel;
        }

        // 권한이 있다면 수정 화면에 기존 데이터를 뿌려주기 위해 Model에 담음
        model.addAttribute("counsel", counsel);
        return "counsel/update";
    }

    // 5-1. DB 수정 처리
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(CounselVO vo) {
        // [요구사항] 변경 시 로그인한 사람을 수정자(modify_id)로 등록
        vo.setModify_id(getLoginUser().getUser_id());

        counselMapper.updateCounsel(vo); // DB 수정

        // 수정한 뒤에는 방금 수정한 그 글로 다시 돌아가기
        return "redirect:/counsel/view?seq_counsel=" + vo.getSeq_counsel();
    }

    // 6. 글 삭제 처리(본인 글만 삭제 로직)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteAction(@RequestParam("seq_counsel") int seq_counsel, RedirectAttributes rttr) {
        UserVO loginUser = getLoginUser();
        CounselVO targetPost = counselMapper.getCounsel(seq_counsel);

        // 관리자(ROLE_ADMIN)이거나, 글 작성자(counsel_writer)가 로그인한 유저와 같은지 확인
        boolean isAdmin = "ROLE_ADMIN".equals(loginUser.getRole());
        boolean isAuthor = targetPost.getCounsel_writer().equals(loginUser.getUser_id());

        // 일반 사용자인데 본인 글이 아니라면? (삭제 거부)
        if (!isAdmin && !isAuthor) {
            // rttr을 쓰면 리다이렉트 후에도 화면에 에러 메시지를 딱 1번 전달할 수 있습니다.
            rttr.addFlashAttribute("errorMessage", "자신이 등록한 게시물만 삭제할 수 있습니다!");
            return "redirect:/counsel/view?seq_counsel=" + seq_counsel;
        }

        // 검증 통과 시 삭제 실행
        counselMapper.deleteCounsel(seq_counsel);
        return "redirect:/counsel/list"; // 삭제했으니 다시 목록 화면으로
    }

    // 7. 댓글 작성 처리
    @RequestMapping(value = "/comment/write", method = RequestMethod.POST)
    public String commentWrite(CounselCommentVO vo) {
        // 댓글 작성자 로그인 유저로 강제 세팅
        vo.setComment_writer(getLoginUser().getUser_id());

        counselMapper.insertComment(vo); // DB에 댓글 Insert
        // 댓글을 쓴 뒤에 원래 보던 게시글 상세 화면을 그대로 유지해야 하므로 부모 글 번호를 달고 리다이렉트합니다.
        return "redirect:/counsel/view?seq_counsel=" + vo.getSeq_counsel();
    }
}