from django.shortcuts import render
from .models import Post, Comment
from django.shortcuts import render, get_object_or_404
from django.utils import timezone
from django.shortcuts import redirect
from .forms import PostForm, CommentForm
from django.http import HttpResponseNotAllowed
from django.core.paginator import Paginator
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from rest_framework import generics
from .serializers import PostSerializer, CommentSerializer
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework import status



# Create your views here.
@api_view(['GET'])
def index(request):
  post_list = Post.objects.order_by('-create_date')
  serializer = PostSerializer(post_list, many=True)
  context = {'post_list': post_list}
  # return render(request, 'templates/community/post_list.html', context)
  return Response(serializer.data)

@api_view(['GET'])
def detail(request, post_id):
  post = get_object_or_404(Post, pk=post_id)
  serializer = PostSerializer(post)
  context = {'post':post}
  # return render(request, 'templates/community/post_detail.html', context)
  return Response(serializer.data)

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def comment_create(request, pk):
    post = get_object_or_404(Post, pk=pk)
    serializer = CommentSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save(author=request.user, post=post)
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def post_create(request):
    serializer = PostSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save(author=request.user)
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
  
@api_view(['PUT'])
@permission_classes([IsAuthenticated])
def post_modify(request, pk):
    post = get_object_or_404(Post, pk=pk)
    if request.user != post.author:
        return Response({'detail': '수정 권한이 없습니다'}, status=status.HTTP_403_FORBIDDEN)
    serializer = PostSerializer(post, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['DELETE'])
@permission_classes([IsAuthenticated])
def post_delete(request, pk):
    post = get_object_or_404(Post, pk=pk)
    if request.user != post.author:
        return Response({'detail': '삭제 권한이 없습니다'}, status=status.HTTP_403_FORBIDDEN)
    post.delete()
    return Response(status=status.HTTP_204_NO_CONTENT)



