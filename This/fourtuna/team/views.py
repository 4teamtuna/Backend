from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Team
from .serializers import TeamSerializer
from django.contrib.auth import get_user_model

User = get_user_model()

@api_view(['GET', 'POST'])
def teams(request):
    if request.method == 'GET':
        teams = Team.objects.all()
        serializer = TeamSerializer(teams, many=True)
        return Response(serializer.data)
    elif request.method == 'POST':
        serializer = TeamSerializer(data=request.data)
        if serializer.is_valid():
            team = serializer.save()
            return Response(TeamSerializer(team).data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
