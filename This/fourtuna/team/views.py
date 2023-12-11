from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from .models import Team
from .serializers import TeamSerializer
from django.contrib.auth import get_user_model
from rest_framework.permissions import IsAuthenticated
from django.shortcuts import get_object_or_404

User = get_user_model()


# 팀 리스트
@api_view(['GET'])
def teams_list(request):
    teams = Team.objects.all()
    serializer = TeamSerializer(teams, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)


# 팀 생성
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def create_team(request):
    serializer = TeamSerializer(data=request.data)
    if serializer.is_valid():
        team = serializer.save()
        team.participants.add(request.user) # 요청을 보낸 User를 그 팀의 참가자로 추가
        team.save()
        return Response(TeamSerializer(team).data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# 팀 참가
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def join_team(request, team_id): # team_id로 팀 정보 가져오기
    team = get_object_or_404(Team, id=team_id) # 
    team.participants.add(request.user) # 요청을 보낸 User를 그 팀의 참가자로 추가
    team.save()
    return Response({"message": f"User {request.user.username} has joined the team {team.id}"}, status=status.HTTP_200_OK)


# 팀 나가기
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def leave_team(request, team_id): # team_id로 팀 정보 가져오기
    team = get_object_or_404(Team, id=team_id)
    if request.user in team.participants.all():
        team.participants.remove(request.user) # 요청을 보낸 User를 그 팀에서 제거
        team.save()
        return Response({"message": f"User {request.user.username} has left the team {team.id}"}, status=status.HTTP_200_OK)
    else:
        return Response({"error": "User is not in the team"}, status=status.HTTP_400_BAD_REQUEST)
    

# 팀 삭제 (participants의 auto_increment id가 1인 사람 즉, 팀장인 사람만 삭제 가능)
@api_view(['DELETE'])
@permission_classes([IsAuthenticated])
def delete_team(request, team_id):
    if request.user.id != 1: # 팀장 권한 검사
        return Response({"error": "Permission denied"}, status=status.HTTP_403_FORBIDDEN)
    
    team = get_object_or_404(Team, id=team_id)
    team.delete()
    return Response({"message": f"Team {team_id} has been deleted"}, status=status.HTTP_200_OK)