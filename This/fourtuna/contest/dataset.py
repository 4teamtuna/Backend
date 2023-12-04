'''
from django.db import models
from django.contrib.auth.models import User
import csv

# Create your models here.
    
class my_contest(models.Model):
    contest_id = models.AutoField(primary_key=True)
    login_id = models.ForeignKey(User,unique=True, on_delete=models.SET_NULL, null=True)
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
    deadline = models.DateTimeField()
    view = models.IntegerField()

    def __str__(self):
        return self.host
'''

import pandas as pd
from datetime import datetime
from contest.models import Contest_info

df = pd.read_excel('/Users/KimDongHyeon/Desktop/공모전.xlsx')

for index, row in df.iterrows():
    instance = Contest_info(title=row['공모전명'], 
                            link=row['홈페이지URL'],
                            image= row['사진URL'],
                            keyword = row['분야'],
                            host = row['주최사'],
                            period = row['기한'],
                            date=datetime.now(),  # 현재 시간을 적용합니다.
                            )
    instance.save()

