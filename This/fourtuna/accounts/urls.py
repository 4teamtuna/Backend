from . import views
from django.contrib import admin
from django.urls import path, include
from django.conf.urls.static import static
from django.conf import settings
from django.contrib.auth import views as auth_views

app_name = 'accounts'



urlpatterns = [
    # path('users/register/', views.register, name='register'),
    # path('users/main/', views.home),
    # path('users/logout/',views.logout),
    # path('', views.login),

    path('', views.login, name='login'),
    path('logout/', views.logout, name='logout'),
    path('signup/', views.signup, name='signup'),
  
]
 
  # path('mypage/', views.mypage_view),44
  # path('login/', views.login_view),
  # path('register/', views.register_view),
  # path('sign', include('account.urls')),