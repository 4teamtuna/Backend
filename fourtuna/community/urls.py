from django.urls import path
from community import views

urlpatterns = [
    path('', views.community_main),
]