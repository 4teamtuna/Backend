from django.urls import path
from teaming import views

urlpatterns = [
    path('', views.teaming_main),
]