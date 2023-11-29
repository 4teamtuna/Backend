# from django.shortcuts import render
# from django.contrib.auth.hashers import make_password, check_password
# from django.shortcuts import redirect 
# from django.http import HttpResponse
# from .models import UserProfile
# from django.contrib.auth.models import User
# from django.contrib.auth import authenticate, login
from django.contrib.auth import authenticate, login
from django.shortcuts import render, redirect
from accounts.forms import UserForm


    

def signup(request):
    if request.method == 'POST':
        form = UserForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            raw_password = form.cleaned_data.get('password1')
            user = authenticate(username=username, password=raw_password)
            login(request, user)
            return redirect('index')
    else:
        form = UserForm()
    return render(request, 'accounts/templates/common/signup.html', {'form': form})
