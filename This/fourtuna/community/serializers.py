from .models import Post, Comment
from users.models import User
from rest_framework import serializers


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'


class PostSerializer(serializers.ModelSerializer):
    author = UserSerializer(read_only = True)
    class Meta:
        model = Post
        fields = ('author','title','content','create_date','modify_date')

        
class CommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = '__all__'