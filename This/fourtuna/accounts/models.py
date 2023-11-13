from django.db import models

# Create your models here.
class user(models.Model):
    user_id = models.CharField(max_length=20)
    email  = models.CharField(max_length=20)
    password = models.CharField(max_length=20)
    # nickname = models.CharField(max_length=20)
    # Profile_Image
    # Gender
    # Interest_Field
    # Introduce
    # Birthday