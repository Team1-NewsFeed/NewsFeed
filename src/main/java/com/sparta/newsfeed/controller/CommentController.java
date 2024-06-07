package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    @Operation(summary = "댓글 생성")
    @Parameters({
            @Parameter(name = "boardId",description = "개시판 번호"),
            @Parameter(name = "content",description = "댓글 내용")
    })
    public String create_comment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create_comment(boardId,commentRequestDto);
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    @Operation(summary = "댓글 수정")
    @Parameters({
            @Parameter(name = "boardId",description = "개시판 번호"),
            @Parameter(name = "commentId",description = "댓글 번호"),
            @Parameter(name = "content",description = "댓글 내용")
    })
    public String update_comment(
            @PathVariable Long commentId,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.update_comment(boardId,commentId, commentRequestDto);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    @Operation(summary = "댓글 삭제")
    @Parameters({
            @Parameter(name = "boardId",description = "개시판 번호"),
            @Parameter(name = "commentId",description = "댓글 번호")
    })
    public String delete_comment(@PathVariable long commentId, @PathVariable long boardId) {
        return commentService.delete(commentId);
    }
}
