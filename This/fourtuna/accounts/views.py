import json
from django.shortcuts import render
# from django.http import HttpResponse
from django.views import View
from django.http import JsonResponse
from .models import accounts
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

# from .models import user
# Create your views here.

@method_decorator(csrf_exempt, name='dispatch')
class SignUpView(View):
    def post(self, request):
        data = json.loads(request.body)
        accounts(
            email    = data['email'],
            password = data['password']
        ).save()						# 받아온 데이터를 DB에 저장시켜줌

        return JsonResponse({'message':'회원가입 완료'},status=200)
      
@method_decorator(csrf_exempt, name='dispatch')
class SignInView(View):
    def post(self, request):
        data = json.loads(request.body)

        if accounts.objects.filter(email = data['email']).exists() :
            user = accounts.objects.get(email = data['email'])
            if user.password == data['password'] :
                return JsonResponse({'message':f'{user.email}님 로그인 성공!'}, status=200)
            else :
                return JsonResponse({'message':'비밀번호가 틀렸어요'},status = 200)

        return JsonResponse({'message':'등록되지 않은 이메일 입니다.'},status=200)
 

def login_view(request):
  return HttpResponse("로그인 페이지")

def mypage_view(request):
  return HttpResponse("마이페이지")

def register_view(request):
  return HttpResponse("회원가입 페이지")