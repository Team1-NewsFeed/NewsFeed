package com.sparta.newsfeed.controller;


import com.sparta.newsfeed.dto.boardDto.BoardRequestDto;
import com.sparta.newsfeed.dto.boardDto.BoardResponseDto;
import com.sparta.newsfeed.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/create")
    @Operation(summary = "개시물 생성", tags = {"게시물"})
    @Parameter(name = "contents",description = "개시판 내용")
    public String create_board(
            HttpServletRequest servletRequest, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.create_board(servletRequest,boardRequestDto);
    }

    /*@PostMapping("/board/create/m") // Multimedia의 m

    @Operation(summary = "게시물 + 미디어 생성", tags = {"게시물"})
    @Parameters({
            @Parameter(name = "image",description = "이미지 삽입시"),
            @Parameter(name = "movie",description = "동영상 삽입시"),
            @Parameter(name = "board",description =
                    "개시판 내용('json' 으로 넣을것 자동 변환해둠 " +
                            "예시 { \"contents\": \"string\" })")
    })
    public String create_m_board(
            HttpServletRequest servletRequest,
            @RequestPart(required = false) MultipartFile image,
            @RequestPart(required = false) MultipartFile movie,
            @RequestPart String board) {
        return boardService.create_m_board(servletRequest, image, movie, board);
    }*/

    @GetMapping("/board/{page}/{view}")
    @Operation(summary = "개시물 전체 조회", tags = {"게시물"})
    @Parameters({
            @Parameter(name = "page", description = "페이지 위치 값 1부터 시작"),
            @Parameter(name = "view",
                    description = "1,2 좋아요 정렬 3,4, 선택한 날자별 정렬"),
            @Parameter(name = "start", description = "2024-06-01T00:00:00"),
            @Parameter(name = "end", description = "2024-06-01T00:00:00")
    })
    public List<BoardResponseDto> get_all_board(
            HttpServletRequest servletRequest,
            @PathVariable int page,
            @PathVariable int view,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return boardService.get_all_board(servletRequest, page - 1, view, start, end).getContent();
    }


    @GetMapping("/board/view/{boardId}")
    @Operation(summary = "개시물 특정 조회", tags = {"게시물"})
    @Parameter(name = "id",description = "조회할 id값")
    public BoardResponseDto get_board(@PathVariable long boardId) {
        return boardService.get_board(boardId);
    }


    @GetMapping("/board/view/{boardId}/like")
    @Operation(summary = "개시물 좋아요", tags = {"게시물"})
    @Parameter(name = "id",description = "조회할 id값")
    public BoardResponseDto get_board_like(HttpServletRequest servletRequest,@PathVariable long boardId) {
        return boardService.get_board_like(servletRequest,boardId);
    }


    @GetMapping("/board/view/{boardId}/nolike")
    @Operation(summary = "개시물 좋아요 지우기", tags = {"게시물"})
    @Parameter(name = "id",description = "조회할 id값")
    public BoardResponseDto get_board_nolike(HttpServletRequest servletRequest,@PathVariable long boardId) {
        return boardService.get_board_nolike(servletRequest,boardId);
    }

    @DeleteMapping("/board/delete")
    @Parameter(name = "id",description = "삭제할 id값")
    @Operation(summary = "개시물 삭제", tags = {"게시물"})
    public String delete_board(HttpServletRequest servletRequest ,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.delete_board(servletRequest,boardRequestDto);
    }

    @PatchMapping("/board/update")
    @Operation(summary = "개시물 수정", tags = {"게시물"})
    @Parameters({
            @Parameter(name = "id",description = "수정할 id값"),
            @Parameter(name = "contents",description = "개시판 내용")
    })
    public String update_board(HttpServletRequest servletRequest ,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update_board(servletRequest , boardRequestDto);
    }


    /*@PatchMapping("/board/update/m") // Multimedia의 m
    
    @Operation(summary = "게시물 + 미디어 수정", tags = {"게시물"})
    @Parameters({
            @Parameter(name = "image",description = "이미지 수정시"),
            @Parameter(name = "movie",description = "동영상 수정시"),
            @Parameter(name = "board",description =
                    "개시판 수정시 내용('json' 으로 넣을것 자동 변환해둠 " +
                            "예시 { id:1 , contents: string })")
    })
    public String update_m_board(
            HttpServletRequest servletRequest,
            @RequestPart(required = false) MultipartFile image,
            @RequestPart(required = false) MultipartFile movie,
            @RequestPart String board) {
        return boardService.update_m_board(servletRequest, image, movie, board);

    }*/
}
