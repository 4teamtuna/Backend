from django.urls import path
from .views import teams_list, create_team, join_team, leave_team, delete_team

app_name = 'team'

urlpatterns = [
    path('', teams_list),
    path('create/', create_team),
    path('join/<int:team_id>/', join_team),
    path('leave/<int:team_id>/', leave_team),
    path('delete/<int:team_id>/', delete_team),
]

