from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import Contest_Serializer
from .models import Contest

# Create your views here.
