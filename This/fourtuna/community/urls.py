from community import views
from django.contrib import admin
from django.urls import path, include
from django.conf.urls.static import static
from django.conf import settings

app_name = 'community'

urlpatterns = [
    
    path('board/list/', views.board_list, name='list'),
    path('board/write/', views.board_write),
    path('board/detail/<int:pk>', views.board_detail) # int형 pk라는 변수명으로 받음 ex detail/1 (= 첫번째 글)            
  
]+ static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
