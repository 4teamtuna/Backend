# Generated by Django 4.2.6 on 2023-12-09 00:54

from django.db import migrations


class Migration(migrations.Migration):
    dependencies = [
        ("contest", "0005_rename_login_id_my_contest_login_and_more"),
    ]

    operations = [
        migrations.RemoveField(
            model_name="contest_info",
            name="contents",
        ),
    ]
