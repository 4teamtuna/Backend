package com.example.teamchatting.controller;

import com.example.teamchatting.Contest.dto.ContestDto;
import com.example.teamchatting.Contest.entity.Contest;
import com.example.teamchatting.Service.ContestClientService;
import com.example.teamchatting.config.WebSocketConfig;
import com.example.teamchatting.dto.ChatTeamResponse;
import com.example.teamchatting.dto.ContestDtoResponse;
import com.example.teamchatting.dto.TeamRequestDto;
import com.example.teamchatting.dto.TeamResponseDto;
import com.example.teamchatting.model.ChatMessage;
import com.example.teamchatting.model.Team;
import com.example.teamchatting.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TeamRepository teamRepository;

    private final ContestClientService contestClientService;
    private final WebSocketConfig webSocketConfig;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, TeamRepository teamRepository,  ContestClientService contestClientService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.teamRepository = teamRepository;
        this.contestClientService = contestClientService;
        this.webSocketConfig = new WebSocketConfig();
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // broadcast message to all subscribers of the room
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
        return contest;
    }

    public TeamResponseDto convertToDto(Team team) {
        TeamResponseDto dto = new TeamResponseDto();
        dto.setId(team.getId());
        dto.setTeamName(team.getTeamName());
        dto.setLeader(team.getLeader());
        dto.setContent(team.getContent());

        Contest contest = team.getContest();
        if (contest != null) {
            dto.setContestId(contest.getId());
            dto.setContestTitle(contest.getTitle());
            dto.setContestImage(contest.getImage());
            // Add more contest fields to the DTO as needed
        }

        return dto;
    }



    @PostMapping("/teams/{contestId}")
    public ChatTeamResponse createRoom(@PathVariable("contestId") Integer contestId, @RequestBody TeamRequestDto teamRequestDto) {
        ContestDtoResponse contestDto = contestClientService.getContestById(contestId);
        Team team = new Team();
        team.setTeamName(teamRequestDto.getTeamName());
        team.setLeader(teamRequestDto.getLeader());
        team.setContent(teamRequestDto.getContent());
        team.setMaxMember(teamRequestDto.getMaxMember());
        team.setContestName(contestDto.getTitle()); // Use the title from the ContestDto as the contest name
        Team savedTeam = teamRepository.save(team);

        ChatTeamResponse response = new ChatTeamResponse();
        response.setId(savedTeam.getId());
        response.setTeamName(savedTeam.getTeamName());
        response.setLeader(savedTeam.getLeader());
        response.setMaxMember(savedTeam.getMaxMember());
        response.setContent(savedTeam.getContent());

        return response; // Return the newly created team as a ChatTeamResponse
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
    // Add more methods as needed


}