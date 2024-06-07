package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    //댓글 생성
    @Transactional
    public String create_comment(Long boardId, CommentRequestDto CommentRequestDto){
        Board board = getBoard(boardId);

        Comment comment = new Comment(CommentRequestDto,board);
        board.getCommentList().add(comment);
        comment.setBoard_user_id(board.getUser_id());
        comment.setBoard(board);
        commentRepository.save(comment);

        return "개시판 ::"+board.getContents() +"의\n "+comment.getContents() + "라는 댓글이 입력되었습니다.";
    }


    //댓글 수정
    @Transactional
    public String update_comment(Long boardId,Long commentId, CommentRequestDto commentRequestDto) {
        Board board = getBoard(boardId);
        Comment comment = getComment(commentId);
        String contents = comment.getContents();
        comment.update(commentRequestDto);
        return "변경 전 :"+contents +"\n변경 후 :"+commentRequestDto.getContents();
    }


    //댓글 삭제
    public String delete (Long id){
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return "댓글 삭제 완료";
    }


    // id로 Board 가져오기
    private Board getBoard(Long id) {
        return  boardRepository
                .findById(id).orElseThrow(
                        ()->new IllegalArgumentException("해당 개시판이 없습니다."));

    }

    // id로 Comment 가져오기
    private Comment getComment(Long commentId) {
        return commentRepository
                .findById(commentId).orElseThrow(
                        ()-> new IllegalArgumentException("해당 개시판이 없습니다."));
    }


}