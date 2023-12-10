from rest_framework import serializers
from .models import my_contest,Contest_info

class My_Contest_serializer(serializers.ModelSerializer):
    class Meta:
        model = my_contest
        fields = '__all__'

class Contest_Detail_serializer(serializers.ModelSerializer):
    class Meta:
        model = Contest_info
        fields = ('title','link','content')
        
class ListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contest_info
        field = 'image'
        
class Contest_Info_serializer(serializers.ModelSerializer):
    class Meta:
        model = Contest_info
        fields = '__all__'
