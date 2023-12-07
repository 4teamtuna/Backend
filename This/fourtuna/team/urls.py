from django.urls import path
from .views import teams

app_name = 'team'

urlpatterns = [
    path('', teams, name='teams'),
]
