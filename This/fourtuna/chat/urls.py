from django.urls import path
from .views import index, room, create_room

app_name = 'chat'

urlpatterns = [
    path('', index, name='index'),
    path('create/', create_room, name='create_room'),
    path('<int:room_id>/', room, name='room'),
]