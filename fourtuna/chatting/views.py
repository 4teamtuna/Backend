from django.shortcuts import render, HttpResponse

# Create your views here.
def chatting_main(request):
  return HttpResponse('채팅 메인 페이지')