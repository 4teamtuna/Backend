from django.contrib.auth import get_user_model
from django.db import models

User = get_user_model()


class Team(models.Model):
    participants = models.ManyToManyField(User, related_name='team')
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.id}"
