from django.urls import path, re_path
from .views import index, room, ChatRoomView, ChatRoomListView

app_name = "chat"

urlpatterns = [
    path('chatroom/', ChatRoomView.as_view(), name='chatroom'),
    path('chatroomlist/', ChatRoomListView.as_view(), name='chatroomlist'),
    path('', index, name='index'),
    re_path(r'^(?P<room_name>[^/]+)/$', room, name='room'),
]
