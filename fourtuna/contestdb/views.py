from django.shortcuts import render, HttpResponse

# Create your views here.


def contest_list(request):
  return HttpResponse("공모전 리스트 메인 페이지")