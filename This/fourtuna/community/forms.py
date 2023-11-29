from django import forms
from community.models import Post, Comment

class PostForm(forms.ModelForm):
  class Meta:
    model = Post
    fields = ['title', 'content']


class CommentForm(forms.ModelForm):
  class Meta:
    model = Comment
    fields = ['content']
    labels = {
      'content': '댓글 내용',
    }