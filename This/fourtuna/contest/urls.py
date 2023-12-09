from django.urls import path
from .views import Contest_Detail,Contest_list, My_Contest_API

app_name = 'contest'

urlpatterns = [
    path('', Contest_list, name='index'),
    path('contest_details',Contest_Detail, name = 'contest_Detail'),
    path('mycontest',My_Contest_API.as_view(), name = 'mycontest')
]
