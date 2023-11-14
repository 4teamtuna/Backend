from django.contrib import admin
from .models import Users

class accountsAdmin(admin.ModelAdmin):
    list_display = ('username', 'useremail')

admin.site.register(Users, accountsAdmin)