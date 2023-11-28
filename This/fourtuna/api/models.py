'''
from django.db import models
from django.contrib.auth.models import AbstractUser

from django.db import models
from django.utils import timezone
from django.utils.translation import gettext_lazy as _
from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin, BaseUserManager


class CustomAccountManager(BaseUserManager):

    def create_superuser(self, user_name, password, **other_fields):

        other_fields.setdefault('is_staff', True)
        other_fields.setdefault('is_superuser', True)
        other_fields.setdefault('is_active', True)

        if other_fields.get('is_staff') is not True:
            raise ValueError(
                'Superuser must be assigned to is_staff=True.')
        if other_fields.get('is_superuser') is not True:
            raise ValueError(
                'Superuser must be assigned to is_superuser=True.')

        return self.create_user(user_name, password, **other_fields)

    def create_user(self, user_name, password, **other_fields):

        user = self.model(user_name=user_name, **other_fields)
        user.set_password(password)
        user.save()
        return user


class User(AbstractBaseUser, PermissionsMixin):
    ROLE_CHOICES = (
        ('helper', 'helper'),
        ('helped', 'helped'),
    )

    # 필요한 추가 컬럼
    user_name = models.CharField(max_length=150, unique=True)
    password = models.CharField(max_length=150)
    nick_name = models.CharField(max_length=10)
    phone_number = models.CharField(max_length=100)

    img = models.ImageField(upload_to='user_img', null=True, blank=True)
    role = models.CharField(max_length=10, choices=ROLE_CHOICES, default='helper')

    # 건들면 안되는거
    start_date = models.DateTimeField(default=timezone.now)
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    objects = CustomAccountManager()

    # 유동적으로
    USERNAME_FIELD = 'user_name'
    REQUIRED_FIELDS = ['role']

    def __str__(self):
        return self.user_name


class UserReview(models.Model):
    user = models.ForeignKey('User', on_delete=models.CASCADE)
    score = models.IntegerField()
    review = models.TextField()

    def __str__(self):
        return self.user.user_name


class Service(models.Model):
    STATUS_CHOICES = (
        ('wait', 'wait'),
        ('proceed', 'proceed'),
        ('success', 'success'),
    )

    CATEGORY_CHOICES = (
        ('public_service', 'public_service'),
        ('phone', 'phone'),
        ('computer', 'computer'),
        ('print', 'print'),
    )

    category = models.CharField(max_length=20, choices=CATEGORY_CHOICES)
    user = models.ForeignKey('User', on_delete=models.CASCADE)

    title = models.CharField(max_length=100)
    voice_file = models.FileField(upload_to='voice_file', null=True, blank=True)
    context = models.TextField()

    helper_phone_number = models.CharField(max_length=15, null=True, blank=True)
    helped_phone_number = models.CharField(max_length=15, null=True, blank=True)

    status = models.CharField(max_length=10, choices=STATUS_CHOICES, default='wait')
    create_at = models.DateTimeField(auto_now_add=True)
    update_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.title
'''

from django.db import models

# Create your models here.
class Login(models.Model):
    pk = models.IntegerField(max_length=10, primary_key=True)
    Login_ID = models.IntegerField(max_length=20)
    user_PWD = models.CharField(max_length=40)

class Register(models.Model):
    pk = models.IntegerField(max_length=10, primary_key=True)
    login_ID = models.IntegerField(max_length=20)
    user_ID = models.CharField(max_length=40)
    user_PWD = models.CharField(max_length=40)
    NickName = models.CharField(max_length=40)
    Gender = models.CharField(max_length=10)
    E_Mail = models.CharField(max_length=40)
    Birthday = models.CharField(max_length=40)
    
class Users(models.Model):
    Login_ID = models.IntegerField(max_length=10, primary_key=True)
    username = models.CharField(max_length=30)
    password = models.CharField(max_length=40)
    nickname = models.CharField(max_length=40)
    profile_image = models.ImageField()
    gender = models.CharField(max_length=10)
    interest_field = models.TextField()
    introduction = models.TextField()
    birthday = models.CharField(max_length=40)

class my_contest(models.Model):
    pk = models.IntegerField(max_length=10, primary_key=True)
    Login_ID = models.ForeignKey(Login, on_delete=models.SET_NULL, null = True)
    title = models.CharField(max_length=50)
    
class Contest_info(models.Model):
    pk = models.IntegerField(max_length = 20)
    contest_id = models.ForeignKey(my_contest,on_delete=models.SET_NULL, null = True)
    contents = models.TextField()
    host = models.CharField(max_length= 30)
    period = models.CharField(max_length=30)
    date = models.DateField()
    deadline = models.DateTimeField()
    view = models.IntegerField(max_length=20)

class Message(models.Model):
    Chat_room_id = models.BigIntegerField(primary_key=True)
    Message_ID = models.BigIntegerField(primary_key=True)
    user_id = models.ForeignKey(Users,to_field='username', on_delete=models.SET_NULL, null = True)
    Message = models.CharField(max_length=200)
    is_readed = models.BooleanField()
    SendTime = models.DateTimeField()
    
class Chat_In(models.Model):
    chat_room_id = models.ForeignKey(Message, to_field='Chat_room_id',on_delete=models.SET_NULL, null = True)
    user_id = models.ForeignKey(Message, to_field='user_id',on_delete=models.SET_NULL, null = True)
    
class Chatting_Room(models.Model):
    pk = models.IntegerField(max_length=20, primary_key=True)
    chatting_id = models.ForeignKey(Users,on_delete=models.SET_NULL, null = True)
    contest_id = models.ForeignKey(Contest_info,on_delete=models.SET_NULL, null =True)
    title = models.CharField(max_length=20,null=False)
    Checked = models.BooleanField()
    profile_image = models.ImageField()
    Least_Message = models.CharField(max_length=20)
    Least_Time = models.DateField()
    State = models.CharField(max_length=20, null = False)
    
class Post(models.Model):
    Category_ID = models.IntegerField(primary_key=True)
    Category_Name = models.CharField(max_length=20, null =False)
    Category_Code = models.IntegerField()

class Article(models.Model):
    Post_ID = models.IntegerField(max_length=30, primary_key=True)
    Title = models.CharField(max_length=30, null = False)
    Created_By = models.ForeignKey(Users,on_delete=models.SET_NULL, null = True)
    Created_at = models.DateTimeField()
    Like = models.IntegerField()
    Category_ID = models.ForeignKey(Post,on_delete=models.SET_NULL, null = True)

    def __str__(self):
        return self.title
    
    class Meta:
        db_table = "Community_board"
        verbose_name = "커뮤니티 게시글"
        verbose_name_plural = "커뮤니티 게시글"
        
class Recommandation(models.Model):
    pk = models.IntegerField(primary_key=True)
    Login_ID = models.ForeignKey(Users,on_delete=models.SET_NULL, null = True)
    Code = models.IntegerField()
    Category_ID = models.IntegerField()
    Post_ID = models.ForeignKey(Article,on_delete=models.SET_NULL, null =True)
    
class comment(models.Model):
    Category_ID = models.ForeignKey(Article,to_field="Category_ID",on_delete=models.SET_NULL, null = True)
    Reply_ID = models.ForeignKey(Users,to_field="user_id",on_delete=models.SET_NULL, null =True)
    Post_ID = models.ForeignKey(Article,to_field="Post_ID", on_delete=models.SET_NULL, null =True)
    Contents = models.CharField(max_length=200, null =False)
    Created_at = models.DateTimeField()
    
    
    
    
    