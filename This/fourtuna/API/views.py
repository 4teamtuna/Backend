from django.shortcuts import get_object_or_404, get_list_or_404
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from django.conf import settings
import requests, json
from contest.serializers import My_Contest_serializer, Contest_Info_serializer

API_KEY = settings.API_KEY

@api_view(['GET'])
def Contests(request):
    url = 'http://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json'
    params = {
        'auth': API_KEY,
        'topFinGrpNo': '020000',
        'pageNo': '1'
    }
    response = requests.get(url, params=params)
    Contests_data = response.json()['result']['baseList']
    product_serializer = Contest_Info_serializer(Contests_data, many=True)

    return Response(product_serializer.data)