from django.urls import path
from . import views

urlpatterns = [
  path('mypage/', views.mypage_view),
  path('login/', views.login_view),
  path('register/', views.register_view),
]