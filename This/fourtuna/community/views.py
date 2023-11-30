from django.shortcuts import render
from .models import Post
from django.shortcuts import render, get_object_or_404
from django.utils import timezone
from django.shortcuts import redirect
from .forms import PostForm, CommentForm
from django.http import HttpResponseNotAllowed
from django.core.paginator import Paginator
from django.contrib.auth.decorators import login_required
from django.contrib import messages

# Create your views here.
def index(request):
  post_list = Post.objects.order_by('-create_date')
  context = {'post_list': post_list}
  return render(request, 'templates/community/post_list.html', context)

def detail(request, post_id):
  post = get_object_or_404(Post, pk=post_id)
  context = {'post':post}
  return render(request, 'templates/community/post_detail.html', context)

@login_required(login_url='accounts:login')
def comment_create(request, post_id):
  post = get_object_or_404(Post, pk=post_id)
  if request.method == "POST":
    form = CommentForm(request.POST)
    if form.is_valid():
      comment = form.save(commit=False)
      comment.author = request.user
      comment.create_date = timezone.now()
      comment.post = post
      comment.save()
      return redirect('community:detail', post_id=post.id)
  else:
    form = CommentForm()
  context = {'post': post, 'form': form}
  return render(request, 'templates/community/post_detail.html', context)

@login_required(login_url='accounts:login')
def post_create(request):
  if request.method == "POST":
    form = PostForm(request.POST)
    if form.is_valid():
      post = form.save(commit=False)
      post.author = request.user
      post.create_date = timezone.now()
      post.save()
      return redirect('community:index')
  else:
    form = PostForm()
  context = {'form': form}
  return render(request, 'templates/community/post_form.html', context)
  
@login_required(login_url='accounts:login')
def post_modify(request, post_id):
  post = get_object_or_404(Post, pk=post_id)
  if request.user != post.author:
    messages.error(request, '수정 권한이 없습니다')
    return redirect('community:detail', post_id=post.id)
  if request.method == "POST":
    form = PostForm(request.POST, instance=post)
    if form.is_valid():
      post = form.save(commit=False)
      post.modify_date = timezone.now()
      post.save()
      return redirect('community:detail', post_id=post.id)
  else:
    form = PostForm(instance=post)
  context = {'form': form}
  return render(request, 'templates/community/post_form.html', context)

@login_required(login_url='accounts:login')
def post_delete(request, post_id):
  post = get_object_or_404(Post, pk=post_id)
  if request.user != post.author:
    messages.error(request, "삭제 권한이 없습니다")
    return redirect('community:detail', post_id=post.id)
  post.delete()
  return redirect('community:index')
