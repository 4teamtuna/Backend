from .models import Post, Comment
from users.models import User, UserProfile
from rest_framework import serializers


class UserSerializer(serializers.ModelSerializer):
    profile_image = serializers.ImageField(read_only = True, source='userprofile.profile_image')
    class Meta:
        model = User
        fields = ('username', 'profile_image', 'email')



class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('profile_image','nickname')

class PostSerializer(serializers.ModelSerializer):
    author = UserSerializer(read_only = True)
    Profile = UserProfileSerializer(read_only = True)
    class Meta:
        model = Post
        fields = ('author','Profile','title','content','create_date','modify_date')
        
class CommentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = '__all__'