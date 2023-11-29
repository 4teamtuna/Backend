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

    path('login/', auth_views.LoginView.as_view(template_name='templates/accounts/login.html'), name='login'),
    path('logout/', auth_views.LogoutView.as_view(), name='logout'),
    path('signup/', views.signup, name='signup'),
  
]+ static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
 
  # path('mypage/', views.mypage_view),
  # path('login/', views.login_view),
  # path('register/', views.register_view),
  # path('sign', include('account.urls')),