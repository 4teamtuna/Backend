from django.urls import path
from gsmoa import views

urlpatterns = [
    path('', views.main),
    path('login/', views.login),
    path('register/', views.register)
]