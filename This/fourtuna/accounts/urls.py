from django.urls import path
from .views import SignUpView, SignInView


urlpatterns = [
  path('up', SignUpView.as_view()),
  path('in', SignInView.as_view()),
]
 
  # path('mypage/', views.mypage_view),
  # path('login/', views.login_view),
  # path('register/', views.register_view),
  # path('sign', include('account.urls')),