package com.ws.ch4.controller;

import com.ws.ch4.domain.CommentDto;
import com.ws.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController //  @Controller + @ResponseBody + @...
public class CommentController {
    @Autowired
    CommentService commentService;


//    {
//        "pcno":0,
//            "comment" : "Hello",
//            "commenter" : "asdf"
//    }
    // 댓글을 수정하는 메서드드
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno,@RequestBody CommentDto commentDto, HttpSession session) {
        //        String commenter = (String)session.getAttribute("id");    //로그인시
        String commenter = "asdf";
        commentDto.setCommenter(commenter);
        commentDto.setCno(cno);
        System.out.println("commentDto = " + commentDto);

        try {
            if(commentService.modify(commentDto) != 1)
                throw new Exception("Write Failed!");
            return new ResponseEntity<String>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }
//    PostMan -> JSON test
//    {
//        "pcno":0
//        "comment" : "Hi"
//    }

    // 댓글을 등록하는 메서드
    @PostMapping("/comments")   //ch4/comments?bno=2374 POST    //@RequestBody -> JSON 데이터를 dto에 넣는다.
    public ResponseEntity<String> write(@RequestBody CommentDto commentDto, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");    //로그인시
        String commenter = "asdf";
        commentDto.setCommenter(commenter);
        commentDto.setBno(bno);
        System.out.println("commentDto = " + commentDto);

        try {
            if(commentService.write(commentDto) != 1)
                throw new Exception("Write Failed!");
            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("comments/{cno}")
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
//        String commenter = (String)session.getAttribute("id");    //로그인시
        String commenter = "asdf";
        try {
            int rowCnt = commentService.remove(cno, bno, commenter);
            if(rowCnt != 1)
                throw new Exception("Delete Failed!");
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }
    //Restful uri에서 값을 가져올때는 @PathVariable붙이고
    //Qeury에서 가져올땐 사용 안해도 됨.

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
   @GetMapping("/comments")    // /comments?bno=2374
   public ResponseEntity<List<CommentDto>> list(Integer bno){
       List<CommentDto> list = null;
       try {
           list = commentService.getList(bno);
           System.out.println("commentService = " + commentService);
           //Entity - 내용물
           // (ex.  ResponseEntity -> Response의 내용물
           return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);    //Response의 내용물에 상태코드 추가(200)
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<List<CommentDto>>(list, HttpStatus.BAD_REQUEST);   // 400
       }
   }
}
