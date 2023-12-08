# 기본 user 모델 확장
from django.contrib.auth.models import User
from django.db import models

class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    nickname = models.CharField(max_length=30, default= 'User')
    introduction = models.CharField(max_length=100)
    interest_field = models.CharField(max_length=100)
    birth_date = models.DateField(null=True, blank=True)
    profile_image = models.ImageField(upload_to='avatars/', null=True, blank=True)

