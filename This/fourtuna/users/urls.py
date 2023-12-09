from django.urls import path
from .views import RegisterView, HomeView, MyPageView, LogoutView, UserAuthToken


urlpatterns = [
    path('', HomeView.as_view(), name='home'),
    path('register/', RegisterView.as_view(), name='register'),
    path('login/', UserAuthToken.as_view(), name='login'),
    path('mypage/', MyPageView.as_view(), name='home'),
    path('logout/', LogoutView.as_view(), name='logout'),
]