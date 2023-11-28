from django.contrib import admin
from api.models import Users

class accountsAdmin(admin.ModelAdmin):
    list_display = ('username', 'useremail', 'userid')

admin.site.register(Users, accountsAdmin)