# Generated by Django 4.2.6 on 2023-12-05 01:14

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ("chat", "0001_initial"),
    ]

    operations = [
        migrations.AddField(
            model_name="chatroom",
            name="name",
            field=models.CharField(default="", max_length=255),
        ),
    ]
