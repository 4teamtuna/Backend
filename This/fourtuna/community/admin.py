from django.contrib import admin
from api.models import Article

# Register your models here.

class CommunityAdmin(admin.ModelAdmin):
    list_display = ('title', )
admin.site.register(Article, CommunityAdmin) # 등록
