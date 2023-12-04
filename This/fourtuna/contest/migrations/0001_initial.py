# Generated by Django 4.2.6 on 2023-12-01 02:50

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):
    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name="my_contest",
            fields=[
                ("contest_id", models.AutoField(primary_key=True, serialize=False)),
                ("title", models.CharField(max_length=50)),
                (
                    "login_id",
                    models.ForeignKey(
                        null=True,
                        on_delete=django.db.models.deletion.SET_NULL,
                        to=settings.AUTH_USER_MODEL,
                        unique=True,
                    ),
                ),
            ],
        ),
        migrations.CreateModel(
            name="Contest_info",
            fields=[
                ("image", models.ImageField(upload_to="", verbose_name="이미지")),
                (
                    "contest_info_id",
                    models.AutoField(primary_key=True, serialize=False),
                ),
                ("contents", models.TextField()),
                ("host", models.CharField(max_length=30)),
                ("period", models.CharField(max_length=30)),
                ("date", models.DateField()),
                ("deadline", models.DateTimeField()),
                ("view", models.IntegerField()),
                (
                    "contest",
                    models.ForeignKey(
                        null=True,
                        on_delete=django.db.models.deletion.SET_NULL,
                        to="contest.my_contest",
                    ),
                ),
            ],
        ),
    ]
