from django.shortcuts import render
from django.contrib.auth.hashers import make_password, check_password
from api.models import Users
from .serializers import Account_Serializer
from rest_framework.response import Response
from rest_framework.decorators import api_view
from django.shortcuts import redirect
from django.http import HttpResponse


@api_view(["GET"])
def users_list(request):
    users = Users.objects.all()
    serializer = Account_Serializer(users, many=True)
    return Response(serializer.data)


def login(request):
    if request.method == "GET":
        return render(request, "login.html")

    elif request.method == "POST":
        userid = request.POST.get("userid", None)
        password = request.POST.get("password", None)
        errMsg = {}

        if not (userid and password):
            errMsg["error"] = "모든 값을 입력하세요"
        else:
            try:
                user = Users.objects.get(userid=userid)
                if check_password(password, user.password):
                    request.session["user"] = user.id
                    return redirect("users/main/")
                else:
                    errMsg["error"] = "비밀번호를 다시 입력하세요"
            except Users.DoesNotExist:
                errMsg["error"] = "아이디가 존재하지 않습니다"
        return render(request, "login.html", errMsg)


def register(request):
    if request.method == "GET":
        return render(request, "register.html")

    elif request.method == "POST":
        username = request.POST.get("username", None)
        userid = request.POST.get("userid", None)
        password = request.POST.get("password", None)
        repassword = request.POST.get("re-password", None)
        useremail = request.POST.get("useremail", None)
        introduction = request.POST.get("introduction", None)
        errorMsg = {}

        if not (userid and useremail and password and repassword):
            errorMsg["error"] = "모든 값을 입력해야 합니다."
        elif password != repassword:
            errorMsg["error"] = "비밀번호가 다릅니다."
        else:
            user = Users(
                username=username,
                userid=userid,
                password=make_password(password),
                useremail=useremail,
                introduction=introduction,
            )
            user.save()

        return redirect("/")


def logout(request):
    if request.session.get("user"):
        del request.session["user"]
    return redirect("/")


def home(request):
    user_id = request.session.get("user")

    if user_id:
        member = Users.objects.get(pk=user_id)
        return render(request, "index.html")
    return render(request, "index.html")
