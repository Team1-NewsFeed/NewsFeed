package com.sparta.newsfeed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.dto.BoardRequestDto;
import com.sparta.newsfeed.dto.BoardResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Multimedia;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.MultimediaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MultimediaRepository multimediaRepository;
    private final ObjectMapper objectMapper;

    // 개시판 생성
    // HttpServletRequest 는 유저 정보 받아오는거
    public String create_board(
            HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        Board board = new Board(servletRequest,boardRequestDto);
        boardRepository.save(board);
        return board.getContents() +" 생성 완료";
    }

    // 개시판 만들때 파일도 같이 넣음
    public String create_m_board(
            HttpServletRequest servletRequest, MultipartFile image, MultipartFile movie, String board) {

        try {
            Board new_board = new Board(servletRequest, getBoard(servletRequest,board));
            boardRepository.save(new_board);

            Multimedia multimedia = new Multimedia();
            multimedia.setBoard(new_board);
            if (image != null && !image.isEmpty() && image.getContentType() != null && image.getContentType().toLowerCase().contains("image")) {
                multimedia.setImage(image.getBytes());
            }

            if (movie != null && !movie.isEmpty() && movie.getContentType() != null && (movie.getContentType().toLowerCase().contains("mp4") || movie.getContentType().toLowerCase().contains("avi"))) {
                multimedia.setMovie(movie.getBytes());
            }
            multimediaRepository.save(multimedia);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "생성 완료";
    }

    // 개시판 전채 조회
    // 1인 이유는 아직 유저값을 못받아서 그런것
/*    public List<BoardResponseDto> get_all_board(HttpServletRequest servletRequest) {
        List<Board> boards = boardRepository.findByUser_id(1L);
        if(boards.isEmpty()) throw new IllegalArgumentException("사용자의 개시물이 없습니다.");


        return boardRepository.findAll().stream()
                .filter(B -> B.getUser_id().equals(1L))
                .map( BoardResponseDto :: new)
                .toList();
    }*/

    // 개시판 특정 조회
    public BoardResponseDto get_board(
            HttpServletRequest servletRequest,BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        return new BoardResponseDto(board);
    }

    // 개시판 삭제
    @Transactional
    public String delete_board(
            HttpServletRequest servletRequest,BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        boardRepository.delete(board);
        return "삭제 완료";
    }

    // 개시판 수정
    @Transactional
    public String update_board(HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        board.update(boardRequestDto);
        return "수정완료";
    }

    // 개시판 + 파일 업데이트
    @Transactional
    public String update_m_board(
            HttpServletRequest servletRequest, MultipartFile image, MultipartFile movie, String board) {

        try {
            Board new_board = getBoard(getBoard(servletRequest,board));
            new_board.update(getBoard(servletRequest,board));
            Optional<Multimedia> multimedia = multimediaRepository.findById(new_board.getId());



            multimedia.get().setBoard(new_board);
            if (image != null && !image.isEmpty() && image.getContentType() != null && image.getContentType().toLowerCase().contains("image")) {
                multimedia.get().setImage(image.getBytes());
            }

            if (movie != null && !movie.isEmpty() && movie.getContentType() != null && (movie.getContentType().toLowerCase().contains("mp4") || movie.getContentType().toLowerCase().contains("avi"))) {
                multimedia.get().setMovie(movie.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "수정 완료";
    }

    /* 도구 상자 */

    // 문자열 Board로 변환
    private BoardRequestDto getBoard(HttpServletRequest servletRequest, String board) throws JsonProcessingException {
        return objectMapper.readValue(board, BoardRequestDto.class);
    }

    // 개시판 id로 찾아서 가셔오기
    private Board getBoard(BoardRequestDto boardRequestDto) {
        Optional<Board> boards = boardRepository.findById(boardRequestDto.getId());
        if(boards.isEmpty()) throw new NullPointerException("사용자의 개시물이 없습니다.");
        Board board = boards.get();
        return board;
    }
}
