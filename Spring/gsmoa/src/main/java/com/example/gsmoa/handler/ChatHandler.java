// 1. 소켓통신은 서버와 클라이언트가 1:N 관계를 맺음
// 2. 페이로드는 전송되는 데이터를 의미함 ( 데이터 전송 간 다양한 요소들을 함께 보내서 전송효율, 안정성을 높임)
// 3. 리스트에 웹소켓 세션들을 저장해 메세지를 각 세션에다 뿌려주는 handling 을 구현

package com.example.gsmoa.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);
        //페이로드란 전송되는 데이터를 의미한다.
        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }
    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }
    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }
}