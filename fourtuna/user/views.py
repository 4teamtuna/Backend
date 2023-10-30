from django.shortcuts import render, HttpResponse

# Create your views here.
def mypage(request):
  return HttpResponse('마이페이지')