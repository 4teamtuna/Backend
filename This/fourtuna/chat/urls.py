from django.urls import path, re_path
from .views import index, room, create_room

app_name = 'chat'

urlpatterns = [
    path('', index, name='index'),
    path('create/', create_room, name='create_room'),
    re_path(r'^(?P<room_id>[^/]+)/$', room, name='room'),

]
