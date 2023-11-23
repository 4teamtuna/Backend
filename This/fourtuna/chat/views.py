from django.shortcuts import render


def index(request):
    return render(request, "chat/templates/index.html")

def room(request, room_name):
    return render(request, "chat/templates/room.html", {"room_name": room_name})