from .models import Post, Comment
from users.models import User
from rest_framework import serializers


class UserSerializer(serializers.ModelSerializer):
    profile_image = serializers.ImageField(read_only = True, source='userprofile.profile_image')
    class Meta:
        model = User
        fields = ('username', 'profile_image', 'email')




class PostSerializer(serializers.ModelSerializer):
    author = UserSerializer(read_only = True)
    class Meta:
        model = Post
        fields = ('author','title','content','create_date','modify_date')
        
class CommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = '__all__'