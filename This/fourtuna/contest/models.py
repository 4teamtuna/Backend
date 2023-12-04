from django.db import models
from django.contrib.auth.models import User
import csv

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
    image = models.ImageField(verbose_name = '이미지')
    keyword = models.CharField(max_length= 100, null = True)
    contest_info_id = models.AutoField(primary_key=True)
    contest = models.ForeignKey(my_contest, on_delete=models.SET_NULL, null=True)
    contents = models.TextField()
    host = models.CharField(max_length=30)
    period = models.CharField(max_length=30)
    date = models.DateField()
    view = models.IntegerField()

    def __str__(self):
        return self.host