from rest_framework import serializers
from api.models import Users


class Account_Serializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = "__all__"
