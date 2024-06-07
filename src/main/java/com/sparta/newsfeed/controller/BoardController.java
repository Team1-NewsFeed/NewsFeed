package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.BoardRequestDto;
import com.sparta.newsfeed.dto.BoardResponseDto;
import com.sparta.newsfeed.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/create")
    @Operation(summary = "개시물 생성")
    @Parameter(name = "contents",description = "개시판 내용")
    public String create_board(
            HttpServletRequest servletRequest, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.create_board(servletRequest,boardRequestDto);
    }

    @PostMapping("/board/create/m") // Multimedia의 m
    @Operation(summary = "게시물 + 미디어 생성")
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
    }

/*    @GetMapping("/board/all")
    @Operation(summary = "개시물 전체 조회")
    public List<BoardResponseDto> get_all_board(HttpServletRequest servletRequest) {
        return boardService.get_all_board(servletRequest);
    }*/

    @GetMapping("/board/one")
    @Operation(summary = "개시물 특정 조회")
    @Parameter(name = "id",description = "조회할 id값")
    public BoardResponseDto get_board(
            HttpServletRequest servletRequest,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.get_board(servletRequest ,boardRequestDto);
    }

    @DeleteMapping("/board/delete")
    @Parameter(name = "id",description = "삭제할 id값")
    @Operation(summary = "개시물 삭제")
    public String delete_board(HttpServletRequest servletRequest ,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.delete_board(servletRequest,boardRequestDto);
    }

    @PatchMapping("/board/update")
    @Operation(summary = "개시물 수정")
    @Parameters({
            @Parameter(name = "id",description = "수정할 id값"),
            @Parameter(name = "contents",description = "개시판 내용")
    })
    public String update_board(HttpServletRequest servletRequest ,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update_board(servletRequest , boardRequestDto);
    }

    @PatchMapping("/board/update/m") // Multimedia의 m
    @Operation(summary = "게시물 + 미디어 수정")
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
    }
}
