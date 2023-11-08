from django.db import models

# Create your models here.
class user(models.Model):
  user_id = models.CharField(max_length=10)
  user_name = models.CharField(max_length=10)
  user_nick_name = models.CharField(max_length=12)
  user_pw = models.CharField(max_length=12)
  
  def __str__(self):
    return self.user_id

