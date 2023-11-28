from django.urls import path
from .views import ContentsListCreateView

urlpatterns = [
    path(
        "api/contents/", ContentsListCreateView.as_view(), name="contents-list-create"
    ),
]
