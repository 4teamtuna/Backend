from rest_framework import serializers
from django.contrib.auth.models import User
from .models import UserProfile

<<<<<<< HEAD
class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ['introduction', 'interest_field', 'birth_date', 'profile_image']

class UserSerializer(serializers.ModelSerializer):
    profile = UserProfileSerializer(read_only=True)
    class Meta:
        model = User
        fields = ['username', 'email', 'password', 'profile']
=======
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username', 'email', 'password', 'last_name']

class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ['introduction', 'interest_field', 'birth_date', 'profile_image', 'nickname']
>>>>>>> 6c79ddd56d29146e592a27eac616360e5bfc2086
