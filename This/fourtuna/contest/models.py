from django.db import models
from django.contrib.auth.models import User
from django.core.files import File
import os

# Create your models here.
    
class my_contest(models.Model):
    contest_id = models.AutoField(primary_key=True)
    login = models.ForeignKey(User,unique=True, on_delete=models.SET_NULL, null=True)
    title = models.CharField(max_length=50)

    def __str__(self):
        return self.title
    
class Contest_info(models.Model):
    title = models.CharField(max_length=100, null =True)
    link = models.CharField(max_length= 300, null =True)
    image = models.ImageField(upload_to='This/fourtuna/static/contest_img')
    keyword = models.CharField(max_length= 100, null = True)
    contest_info_id = models.AutoField(primary_key=True)
    contest = models.ForeignKey(my_contest, on_delete=models.SET_NULL, null=True)
    content = models.TextField(null=True)
    host = models.CharField(max_length=100)
    period = models.CharField(max_length=30)
    date = models.DateField()
    view = models.IntegerField(default=0)

    def __str__(self):
        return self.host
    