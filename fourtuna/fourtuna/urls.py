"""
URL configuration for fourtuna project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from gsmoa import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('gsmoa/', views.main, name='main'),
    path('gsmoa/login/', views.login, name='login'),
    path('gsmoa/community/', views.community, name='community'),
    path('gsmoa/community_posting/<int:posting_id>', views.community_posting, name='posting_id'),
    path('gsmoa/contest/', views.contest, name='contest'),
    path('gsmoa/contest_detail/<int:contest_id>', views.contest_detail, name='contest_id'),
    path('gsmoa/chatting', views.chatting, name='chatting'),
    path('gsmoa/chatting_room/<int:chatting_id', views.chatting_room, name='chatting_id'),
    path('gsmoa/teaming/', views.teaming, name='teaming'),
    path('gsmoa/mypage/', views.mypage, name='login'),
]
