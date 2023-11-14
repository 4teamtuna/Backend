from . import views
from django.contrib import admin
from django.urls import path, include
from django.conf.urls.static import static
from django.conf import settings



urlpatterns = [
    
    path('', views.community_main),
  
]+ static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
 
  # path('mypage/', views.mypage_view),
  # path('login/', views.login_view),
  # path('register/', views.register_view),
  # path('sign', include('account.urls')),