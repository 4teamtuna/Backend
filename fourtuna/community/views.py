from django.shortcuts import render, HttpResponse

# Create your views here.
def community_main(request):
  return HttpResponse('커뮤니티 메인 페이지')