from django.urls import path
from contestdb import views

urlpatterns = [
    path('', views.contest_list),
]