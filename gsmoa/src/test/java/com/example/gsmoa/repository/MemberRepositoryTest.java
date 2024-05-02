package com.example.gsmoa.repository;

import com.example.gsmoa.entity.Member;
import com.example.gsmoa.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository repository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Member member = new Member(
                null, "aaa", "0000", "김에이", "에이", "aaa@aaa.com", UserRole.USER
        );

        repository.save(member);

        Member findMember = repository.findByUsername(member.getUsername());

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getNickname(), member.getNickname());
        assertEquals(findMember.getRole(), member.getRole());
        assertEquals(findMember, member);
    }
}