# Generated by Django 4.2.6 on 2023-12-09 05:24

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ("users", "0001_initial"),
    ]

    operations = [
        migrations.AddField(
            model_name="userprofile",
            name="nickname",
            field=models.CharField(default="User", max_length=30),
        ),
    ]
