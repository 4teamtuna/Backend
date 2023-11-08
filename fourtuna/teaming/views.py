from django.shortcuts import render, HttpResponse

# Create your views here.
def teaming_main(request):
  return HttpResponse('팀 매칭 메인 페이지')