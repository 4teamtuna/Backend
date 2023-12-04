from django.contrib.auth import authenticate, login, logout
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated
from .serializers import UserSerializer, UserProfileSerializer


class HomeView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        user_serializer = UserSerializer(request.user)
        return Response(user_serializer.data, status=status.HTTP_200_OK)


class MyPageView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        user_serializer = UserSerializer(request.user)
        return Response(user_serializer.data, status=status.HTTP_200_OK)


class RegisterView(APIView):
    def post(self, request):
        user_serializer = UserSerializer(data=request.data)
        profile_serializer = UserProfileSerializer(data=request.data)

        if user_serializer.is_valid() and profile_serializer.is_valid():
            user = user_serializer.save()
            user.set_password(user.password)
            user.save()

            profile = profile_serializer.save(commit=False)
            profile.user = user
            profile.save()

            login(request, user)
            return Response(user_serializer.data, status=status.HTTP_201_CREATED)
        
        return Response({'errors': user_serializer.errors | profile_serializer.errors}, status=status.HTTP_400_BAD_REQUEST)

class LoginView(APIView):
    def post(self, request):
        username = request.data.get('username')
        password = request.data.get('password')

        user = authenticate(request, username=username, password=password)

        if user is not None:
            login(request, user)
            return Response({'detail': '로그인 성공'}, status=status.HTTP_200_OK)
        else:
            return Response({'detail': '로그인 실패'}, status=status.HTTP_400_BAD_REQUEST)



class LogoutView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        logout(request)
        return Response({"detail": "로그아웃 성공"}, status=status.HTTP_200_OK)
