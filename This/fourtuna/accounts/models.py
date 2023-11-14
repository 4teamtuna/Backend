from django.db import models

# Create your models here.
class Users(models.Model):
    username = models.CharField(max_length=32, verbose_name = "사용자 이름")
    useremail = models.CharField(max_length = 128, verbose_name = "사용자 이메일")
    userid = models.CharField(max_length=32, verbose_name = "아이디")
    password = models.CharField(max_length=300, verbose_name = "비밀번호")
    introduction = models.CharField(max_length=300, verbose_name= "자기소개")
    registered_date = models.DateTimeField(auto_now_add = True, verbose_name = "등록시간")

    def __str__(self):
        return self.userid

    class Meta:
        db_table = "users"
        verbose_name = "사용자"
        verbose_name_plural = "사용자"