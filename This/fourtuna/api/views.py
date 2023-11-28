from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import Users, Article, Chat, Contest
from .serializers import (
    Chat_Serializer,
    Contest_Serializer,
)

# Create your views here.


@api_view(["GET"])
def contest_list(request):
    contests = Contest.objects.all()
    serializer = Contest_Serializer(contests, many=True)
    return Response(serializer.data)
