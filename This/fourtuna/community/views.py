from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def community_main(request):
    return HttpResponse('커뮤니티 메인 페이지')