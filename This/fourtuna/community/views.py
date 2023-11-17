from django.shortcuts import render, redirect
from django.http import HttpResponse
from .models import Article
from accounts.models import Users
from .forms import BoardForm
# Create your views here.


# 게시글 작성
def board_write(request):
    if request.method == 'POST':
        form = BoardForm(request.POST)
        if form.is_valid():
            user_id = request.session.get('user') # user_id를 가져옴
            user = Users.objects.get(pk=user_id) # 현재 로그인한 사용자 id를 user에 저장

            NewArticle = Article() # 모델 클래스 변수 생성
            NewArticle.title = form.cleaned_data['title'] # form의 제목을 가져옴
            NewArticle.contents = form.cleaned_data['contents']
            NewArticle.writer = user # 현재 로그인한 사용자의 id
            NewArticle.save()

            return redirect('community:list') # 작성 후 글목록으로 이동
    else:
        form = BoardForm()
    return render(request, 'community/board_write.html', {'form' : form})


# 게시글 리스트
def board_list(request):
    boards = Article.objects.all().order_by('-id') # Board모델의 모든 필드를 가져와 id의 역순으로 가져옴(최신글을 맨위로 올림)

    return render(request, 'community/board_list.html', {'boards' : boards})


# 게시글 상세보기
def board_detail(request, pk): # 몇번째 글인지 확인하기 위해 주소로부터 pk를 받음
    board = Article.objects.get(pk=pk) # pk에 해당하는 글을 가져옴(입력받은 id값에(몇번째글인지) 맞는 글을 가져옴)
    return render(request, 'community/board_detail.html', {'board' : board}) #템플릿에 전달