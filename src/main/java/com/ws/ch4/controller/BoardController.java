package com.ws.ch4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ws.ch4.domain.BoardDto;
import com.ws.ch4.domain.PageHandler;
import com.ws.ch4.domain.SearchCondition;
import com.ws.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);  //insert

            if(rowCnt != 1)
                throw new Exception("Modify failed");

            rattr.addFlashAttribute("msg", "Modify_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);   // 실패했을때 데이터를 되돌려준다.
            m.addAttribute("msg", "Modify_ERR");
            return "board";
        }
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);  //insert

            if(rowCnt != 1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);   // 실패했을때 데이터를 되돌려준다.
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }
    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "board"; //읽기는 mode X, 쓰기는 mode = new.
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize,
                         Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");

        try {
            //Model을 사용한 이유
            //Model page,pageSize를 담아주면 redirect할때 뒤에 자동으로 붙는다.
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt!=1){
                throw new Exception("board remove error");
            }
            //Model로 msg를 넘기면 쿼리문에 남아 boardList에 계속 뜨는 문제 발생.
            //RedirectAttribute의 addFlashAttribute 로 넘기면 한번만 뜬다. 쿼리없어짐.
            rattr.addFlashAttribute("msg","DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
        }

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m){
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto); //아래 문장과 동일
            m.addAttribute(boardDto);   // 타입의 첫글자를 소문자로 한 걸로 저장.
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
    }

    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {

            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}