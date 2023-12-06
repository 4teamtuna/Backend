from django.contrib.auth.decorators import login_required
from django.shortcuts import render
from django.utils.safestring import mark_safe
from django.http import JsonResponse
from django.views import View
from django.contrib.auth.mixins import LoginRequiredMixin
from .models import ChatRoom, User

class ChatRoomView(LoginRequiredMixin, View):
    def post(self, request, *args, **kwargs):
        # 1. 채팅방 이름 가져오기
        name = request.POST.get('name')  # 요청에서 채팅방 이름 가져오기

        # 2. 채팅방 생성
        chat_room = ChatRoom()
        chat_room.name = name
        chat_room.save()

        # 3. 현재 로그인한 사용자 가져오기
        user = request.user

        # 4. 참가자로 사용자 추가
        chat_room.participants.add(user)

        return JsonResponse({"message": "Chat room created and user added."})

    
class ChatRoomListView(LoginRequiredMixin, View):
    def get(self, request, *args, **kwargs):
        # 로그인한 사용자가 참가한 채팅방 목록 가져오기
        chat_rooms = request.user.chatrooms.all()

        # 채팅방 목록을 JSON 형태로 변환
        chat_room_list = [{"id": chat_room.id, "name": chat_room.name} for chat_room in chat_rooms]

        return JsonResponse({"chat_rooms": chat_room_list})


import json

def index(request):
    return render(request, 'chat/templates/index.html', {})

@login_required
def room(request, room_name):
    return render(request, 'chat/templates/room.html', {
        'room_name_json': mark_safe(json.dumps(room_name)),
        'username': mark_safe(json.dumps(request.user.username)),
    })