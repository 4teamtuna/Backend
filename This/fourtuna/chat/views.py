from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.shortcuts import get_object_or_404
from .models import ChatRoom
from .serializers import ChatRoomSerializer, MessageSerializer
from django.contrib.auth import get_user_model

User = get_user_model()

@api_view(['GET'])
def index(request):
    chat_rooms = request.user.chat_rooms.all()
    serializer = ChatRoomSerializer(chat_rooms, many=True)
    return Response(serializer.data)

@api_view(['GET', 'POST'])
def room(request, room_id):
    if request.method == 'GET':
        room = get_object_or_404(ChatRoom, id=room_id)
        serializer = ChatRoomSerializer(room)
        return Response(serializer.data)
    elif request.method == 'POST':
        serializer = MessageSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(author=request.user, chat_room_id=room_id)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def create_room(request):
    new_room = ChatRoom.objects.create()
    new_room.participants.add(request.user)
    new_room.save()
    serializer = ChatRoomSerializer(new_room)
    return Response(serializer.data, status=status.HTTP_201_CREATED)
