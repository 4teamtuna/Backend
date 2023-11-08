from django.shortcuts import render,HttpResponse

# Create your views here.
def main(request):
  return HttpResponse('메인 페이지')

def login(request):
  return HttpResponse('로그인 페이지')

def register(request):
  return HttpResponse('회원가입 페이지')
