from .models import Chat
from rest_framework import serializers


class Chat_Serializer(serializers.ModelSerializer):
    class Meta:
        model = Chat
        fields = "__all__"
