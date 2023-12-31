from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class Post(models.Model):
  author = models.ForeignKey(User, on_delete=models.CASCADE)
  title = models.CharField(max_length=200)
  content = models.TextField()
  create_date = models.DateTimeField()
  modify_date = models.DateTimeField(null=True, blank=True)

  def __str__(self):
    return self.title


class Comment(models.Model):
  author = models.ForeignKey(User, on_delete=models.CASCADE)
  post = models.ForeignKey(Post, on_delete=models.CASCADE)
  content = models.TextField()
  create_date = models.DateTimeField()
  modify_date = models.DateTimeField(null=True, blank=True)