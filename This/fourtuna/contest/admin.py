# admin.py
from django.contrib import admin
from .models import Contest_info, my_contest

admin.site.register(Contest_info)
admin.site.register(my_contest)
