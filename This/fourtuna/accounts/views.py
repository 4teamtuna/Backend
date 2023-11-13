from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.

def login_view(request):
  return HttpResponse("로그인 페이지")

def mypage_view(request):
  return HttpResponse("마이페이지")

def register_view(request):
  return HttpResponse("회원가입 페이지")