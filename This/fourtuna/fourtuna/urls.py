from django.contrib import admin
from django.conf.urls import include
from django.urls import path
from django.views.generic import RedirectView
from django.conf import settings
from django.conf.urls.static import static
from rest_framework import routers
from accounts import views as accounts_views  # 'accounts' 앱의 뷰를 가져옵니다.
from community import views as community_views  # 'community' 앱의 뷰를 가져옵니다.
from contest.views import Contest_Info_API
from contest import models
from chat import views as chat_views  # 'chat' 앱의 뷰를 가져옵니다.

router = routers.DefaultRouter()
router.register(r'contests', Contest_Info_API, basename='contest_info')

urlpatterns = [
    path('api/',include(router.urls)),
    path('admin/', admin.site.urls),
    path('accounts/', include('accounts.urls')),
    path('chat/', include('chat.urls', namespace='chat')),
    path('community/', include('community.urls')),
    path('', accounts_views.index, name='index'),  # 'accounts' 앱의 'index' 뷰를 사용합니다.
]
