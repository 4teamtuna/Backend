from django.shortcuts import render
from rest_framework.response import Response
from .models import Contest_info, my_contest
from rest_framework.views import APIView
from .serializers import Contest_Info_serializer, My_Contest_serializer
from rest_framework.viewsets import ModelViewSet

# Create your views here.
class Contest_Info_API(ModelViewSet):
    def get(self, request):
        queryset = Contest_info.objects.all()
        print(queryset)
        serializer = Contest_Info_serializer(queryset, many = True)
        return Response(serializer.data)

class My_Contest_API(APIView):
    def get(self, request):
        queryset = my_contest.objects.all()
        print(queryset)
        serializer = My_Contest_serializer(queryset, many = True)
        return Response(serializer.data)