from .models import Contest
from rest_framework import serializers


class Contest_Serializer(serializers.ModelSerializer):
    class Meta:
        model = Contest
        fields = "__all__"
