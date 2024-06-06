package com.example.gsmoa.TeamChatting.controller;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.service.ContestService;
import com.example.gsmoa.TeamChatting.config.WebSocketConfig;
import com.example.gsmoa.TeamChatting.dto.TeamRequestDto;
import com.example.gsmoa.TeamChatting.dto.TeamResponseDto;
import com.example.gsmoa.TeamChatting.model.ChatMessage;
import com.example.gsmoa.TeamChatting.model.Team;
import com.example.gsmoa.TeamChatting.model.TeamJoin;
import com.example.gsmoa.TeamChatting.repository.TeamJoinRepository;
import com.example.gsmoa.TeamChatting.repository.TeamRepository;
import com.example.gsmoa.User.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TeamRepository teamRepository;
    private final ContestService contestService;
    private final WebSocketConfig webSocketConfig;
    private final TeamJoinRepository teamJoinRepository;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, TeamRepository teamRepository, ContestService contestService, TeamJoinRepository teamJoinRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.teamRepository = teamRepository;
        this.contestService = contestService;
        this.webSocketConfig = new WebSocketConfig();
        this.teamJoinRepository = teamJoinRepository;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }


    public Contest dtoToEntity(ContestDto contestDto) {
        Contest contest = new Contest();
        contest.setId(contestDto.getId());
        contest.setTitle(contestDto.getTitle());
        contest.setHostName(contestDto.getHostName());
        contest.setPeriod(contestDto.getPeriod());
        contest.setPostedDate(contestDto.getPostedDate());
        contest.setTag(contestDto.getTag());
        contest.setViewCount(contestDto.getViewCount());
        contest.setDetails(contestDto.getDetails());
        return contest;
    }

    public TeamResponseDto convertToDto(Team team) {
        TeamResponseDto dto = new TeamResponseDto();
        dto.setId(team.getId());
        dto.setTeamName(team.getTeamName());
        dto.setLeader(team.getLeader());
        dto.setContent(team.getContent());
        dto.setCurrentMember(team.getCurrentMember());

        Contest contest = team.getContest();
        if (contest != null) {
            dto.setContestId(contest.getId());
            dto.setContestTitle(contest.getTitle());
            dto.setContestImage(contest.getImage());
        }

        return dto;
    }



    @PostMapping("/teams/{contestId}")
    public Long createRoom(@PathVariable("contestId") Integer contestId, @RequestBody TeamRequestDto teamRequestDto) {
        ContestDto contestDto = contestService.getContest(contestId);
        Contest contest = dtoToEntity(contestDto);
        TeamJoin teamJoin = new TeamJoin();
        Team team = new Team();
        team.setTeamName(teamRequestDto.getTeamName());
        team.setLeader(teamRequestDto.getLeader());
        team.setContent(teamRequestDto.getContent());
        team.setMaxMember(teamRequestDto.getMaxMember());
        team.setContest(contest);
        team.setCurrentMember(1L);
        team.setUserId(teamRequestDto.getUserId());

        Team savedTeam = teamRepository.save(team);
        teamJoin.setTeamId(savedTeam.getId());
        teamJoin.setUserId(teamRequestDto.getUserId());
        teamJoinRepository.save(teamJoin);
        return savedTeam.getId();
    }


    @GetMapping("/teams")
    public List<TeamResponseDto> getRooms() {
        List<Team> teams = teamRepository.findAll();
        List<TeamResponseDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            TeamResponseDto dto = convertToDto(team);
            dto.setSessionCount(webSocketConfig.getTeamSessionCount().getOrDefault(dto.getId().toString(), 0));
            teamDtos.add(dto);
        }
        return teamDtos;
    }

    @GetMapping("/teams/{roomId}")
    public TeamResponseDto getRoom(@PathVariable("roomId") Long roomId) {
        Team team = teamRepository.findById(roomId).orElse(null);
        if (team != null) {
            return convertToDto(team);
        }
        return null;
    }

    @PostMapping("/teams/{roomId}/join")
    public ResponseEntity<Void> joinTeam(@PathVariable("roomId") Long roomId, @RequestBody TeamJoin teamJoin) {
        Team team = teamRepository.findById(roomId).orElse(null);
        if (team != null) {
            team.increaseCurrentMember();
            teamRepository.save(team);
            teamJoinRepository.save(teamJoin);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/teams/{roomId}/join")
    public ResponseEntity<Void> leaveTeam(@PathVariable("roomId") Long roomId, @RequestBody TeamJoin teamJoin) {
        Team team = teamRepository.findById(roomId).orElse(null);
        TeamJoin existingTeamJoin = teamJoinRepository.findByUserIdAndTeamId(teamJoin.getUserId(), roomId);
        if (team != null && existingTeamJoin != null) {
            team.decreaseCurrentMember();
            teamRepository.save(team);
            teamJoinRepository.delete(existingTeamJoin);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/teams/joined/{userId}")
    public List<TeamResponseDto> getJoinedTeams(@PathVariable("userId") Long userId) {
        List<TeamJoin> teamJoins = teamJoinRepository.findByUserId(userId);
        List<TeamResponseDto> teamDtos = new ArrayList<>();
        for (TeamJoin teamJoin : teamJoins) {
            Team team = teamRepository.findById(teamJoin.getTeamId()).orElse(null);
            if (team != null) {
                teamDtos.add(convertToDto(team));
            }
        }
        return teamDtos;
    }

}