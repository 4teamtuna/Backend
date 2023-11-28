from .models import Article
from rest_framework import serializers


class Community_Serializer(serializers.ModelSerializer):
    class Meta:
        model = Article
        fields = "__all__"
