package com.example.gsmoa.service;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.repository.BoardNotFoundException;
import com.example.gsmoa.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardEntity createBoard(BoardEntity board) {
        return boardRepository.save(board);
    }

    public List<BoardEntity> getAllBoards() {
        return boardRepository.findAll();
    }

    public BoardEntity getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }

    public BoardEntity updateBoard(Long id, BoardEntity newBoard) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setPostId(id);
                    return boardRepository.save(newBoard);
                });
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}